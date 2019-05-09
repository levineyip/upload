package com.nbc.upload.library.upload;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.nbc.upload.library.entity.AliCloudOSS;
import com.nbc.upload.library.util.PreferenceUtil;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class UploadTask implements ITask {

    private String filePath;
    private Context context;
    private int position;
    private OnUploadCallback callback;

    public UploadTask(Context context, int position, String filePath, OnUploadCallback callback) {
        this.context = context;
        this.position = position;
        this.filePath = filePath;
        this.callback = callback;
    }

    @Override
    public void run() {
        Log.d("UploadTask", "filePath  -->  " + filePath);
        if (!TextUtils.isEmpty(filePath)) {
            formUpload(filePath);
        }
    }

    private void formUpload(String localFile) {
        HttpURLConnection conn = null;
        String boundary = "9431149156168";
        try {
            AliCloudOSS.OSSPolicy ossPolicy = PreferenceUtil.getAliCloudOSSPolicy(context);
            if (ossPolicy == null) {
                if (callback != null) {
                    callback.uploadSingleImageCompleted(position, null);
                    return;
                }
            }
            String fileName = System.currentTimeMillis() + "_" + (localFile.substring(localFile.lastIndexOf("/") + 1));
            HashMap<String, String> parameters = new HashMap<>();
            // key
            parameters.put("key", ossPolicy.getDir() + fileName);
            parameters.put("success_action_status", "200");
            // Content-Disposition
            parameters.put("Content-Disposition", "attachment;filename="
                    + localFile);
            // OSSAccessKeyId
            parameters.put("OSSAccessKeyId", ossPolicy.getAccessKeyId());
            // policy
            parameters.put("policy", ossPolicy.getPolicy());
            // Signature
            parameters.put("Signature", ossPolicy.getSignature());

            URL url = new URL(ossPolicy.getHost());
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("connection", "keep-alive");
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
            OutputStream out = new DataOutputStream(conn.getOutputStream());

            // text
            StringBuilder strBuf = new StringBuilder();
            Iterator<Map.Entry<String, String>> iter = parameters.entrySet().iterator();
            int i = 0;

            while (iter.hasNext()) {
                Map.Entry<String, String> entry = iter.next();
                String inputName = entry.getKey();
                String inputValue = entry.getValue();
                if (inputValue == null) {
                    continue;
                }

                if (i == 0) {
                    strBuf.append("--").append(boundary).append("\r\n");
                    strBuf.append("Content-Disposition: form-data; name=\""
                            + inputName + "\"\r\n\r\n");
                    strBuf.append(inputValue);
                } else {
                    strBuf.append("\r\n").append("--").append(boundary).append("\r\n");
                    strBuf.append("Content-Disposition: form-data; name=\""
                            + inputName + "\"\r\n\r\n");
                    strBuf.append(inputValue);
                }
                i++;
            }
            out.write(strBuf.toString().getBytes());

            String contentType = "application/octet-stream";

            strBuf = new StringBuilder();
            strBuf.append("\r\n").append("--").append(boundary)
                    .append("\r\n");
            strBuf.append("Content-Disposition: form-data; name=\"file\"; "
                    + "filename=\"" + fileName + "\"\r\n");
            strBuf.append("Content-Type: " + contentType + "\r\n\r\n");

            out.write(strBuf.toString().getBytes());

            DataInputStream in = new DataInputStream(new FileInputStream(new File(localFile)));
            int bytes;
            byte[] bufferOut = new byte[1024];
            while ((bytes = in.read(bufferOut)) != -1) {
                out.write(bufferOut, 0, bytes);
            }
            in.close();

            byte[] endData = ("\r\n--" + boundary + "--\r\n").getBytes();
            out.write(endData);
            out.flush();
            out.close();

            Log.d("UploadTask", localFile + "  -->  " + conn.getResponseCode());
            if (conn.getResponseCode() == 200) {
                if (callback != null) {
                    String imageUrl = ossPolicy.getHost() + File.separator + ossPolicy.getDir() + fileName;
                    callback.uploadSingleImageCompleted(position, imageUrl);
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        if (callback != null) {
            callback.uploadSingleImageCompleted(position, null);
        }
    }
}
