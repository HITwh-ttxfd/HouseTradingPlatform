需配置Tomcat
同时在类src.main.java.codeBase64.ImageStore
中，

需要配置本地的绝对存储路径，且不能在Tomcat的webapps中的项目

此外在Tomcat的deployments中，要手动添加自己选择的存储图片的目录
我使用的是houseImg，selectUrl中也是这个关键字，建议创建houseImg来存储图片

设置端口8091

路径修改完（修改好本地的）

再启动

先启动Tomcat后启动Springboot!