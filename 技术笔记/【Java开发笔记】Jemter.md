# 【Java开发笔记】Jemter

Jmeter依赖于JDK，所以必须确保当前计算机上已经安装了JDK，并且配置了环境变量。

## 1 安装

### 1.1 下载

可以 Apache Jmeter 官网下载，地址：http://jmeter.apache.org/download_jmeter.cgi

![image-20210715193149837](./【Java开发笔记】Jemter.assets/image-20210715193149837.png)

### 1.2 解压

我的解压路径：`/Users/mac/Desktop/Softs/apache-jmeter-5.4.1`

![image-20230227111116667](./【Java开发笔记】Jemter.assets/image-20230227111116667.png)

### 1.3 运行

进入 `/bin` 目录，执行 `sh jmeter.sh`

![image-20230227111227849](./【Java开发笔记】Jemter.assets/image-20230227111227849.png)

## 2 基本操作

在测试计划上点鼠标右键，选择添加 > 线程（用户） > 线程组：

![image-20230227111403092](./【Java开发笔记】Jemter.assets/image-20230227111403092.png)

在新增的线程组中，填写线程信息：

![image-20230227111601594](./【Java开发笔记】Jemter.assets/image-20230227111601594.png)

给线程组点鼠标右键，添加http取样器：

![image-20230227111629441](./【Java开发笔记】Jemter.assets/image-20230227111629441.png)

编写取样器内容：

![image-20230227111751749](./【Java开发笔记】Jemter.assets/image-20230227111751749.png)

添加监听报告：

![image-20230227111817923](./【Java开发笔记】Jemter.assets/image-20230227111817923.png)

添加监听结果树：

![image-20230227111851790](./【Java开发笔记】Jemter.assets/image-20230227111851790.png)

汇总报告结果：

![image-20210715200243194](./【Java开发笔记】Jemter.assets/image-20210715200243194.png)

结果树：

![image-20210715200336526](./【Java开发笔记】Jemter.assets/45ab4u.png)



























