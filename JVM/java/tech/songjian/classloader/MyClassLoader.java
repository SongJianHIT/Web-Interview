/**
 * @projectName Web-Interview
 * @package tech.songjian.classloader
 * @className tech.songjian.classloader.MyClassLoader
 */
package tech.songjian.classloader;

import java.io.*;

/**
 * MyClassLoader
 * @description 自定义类加载器
 * @author SongJian
 * @date 2023/6/5 17:54
 * @version
 */
public class MyClassLoader extends ClassLoader{

    private String classpath;

    public MyClassLoader(String classpath) {
        this.classpath = classpath;
    }

    /**
     *
     * @param name 类全限定名，定位 class 文件
     *
     * @return
     * @throws ClassNotFoundException
     */
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            byte[] classData = getData(name);
            if(classData != null) {
                // 使用 defineClass 方法将 字节数组数据 转化为 字节码对象
                return defineClass(name, classData, 0, classData.length);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return super.findClass(name);
    }

    /**
     * 加载类的字节数据
     * @param className
     * @return
     * @throws IOException
     */
    private byte[] getData(String className) throws IOException {
        // 根据 className 生成 class 对应的路径
        String path = classpath + File.separatorChar +
                className.replace('.', File.separatorChar) + ".class";
        try (InputStream in = new FileInputStream(path);
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[2048];
            int len = 0;
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            return out.toByteArray();
        }
    }
}

