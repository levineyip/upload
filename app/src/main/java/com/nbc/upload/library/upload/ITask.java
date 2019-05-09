package com.nbc.upload.library.upload;

public interface ITask {

    void run();

    interface OnUploadCallback {
        void uploadSingleImageCompleted(int position, String imageUrl);
    }
}
