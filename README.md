# upload
实现文件上传


第一步：在github创建一个名为” upload”的repository；
第二步：git clone XXX将远程仓库拉到本地；
第三步：以upload为根目录创建一个Android工程，结构如下图；
P.S. 因为是library库，需将module对应的build.gradle稍微修改。①将apply plugin: 'com.android.application'改为apply plugin'com.android.library';
②注释applicationId；
 
第四步：通过下面的步骤push代码到github
git add .
git commit
git push –u origin branch-name(一般是master)
第五步：发布该库，在Github上打开该库，点击“release”
 
如果未发布过库，点击”Create a new release”
如果发布过库，点击”Draft a new release”
 
填写好发布信息，点击”Publish release”。
 
发布成功后，会在Releases中查看到所有发布成功的Library。
 
然后进入网站https://jitpack.io/，输入library的git名称搜索发布结果，例” levineyip/upload”。红圈Log部分若是绿色代表编译通过，点击”Get it”可以直接使用该library；若是红色代表编译不通过，需要查看Log是哪里编译不过。
在过程中引入你的library时，不要忘记加上红框中的语句，否则android studio无法下载该library。

 
