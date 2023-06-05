/**
 * @projectName Web-Interview
 * @package tech.songjian.classloader
 * @className tech.songjian.classloader.TestMyClassLoader
 */
package tech.songjian.classloader;

import java.lang.reflect.Method;

/**
 * TestMyClassLoader
 * @description
 * @author SongJian
 * @date 2023/6/5 18:04
 * @version
 */
public class TestMyClassLoader {
    public static void main(String[] args) throws Exception {
        // 自定义类加载器的加载路径
        MyClassLoader myClassLoader = new MyClassLoader("lib");
        Class c = myClassLoader.loadClass("tech.songjian.classloader.Test");
        if (c != null) {
            Object obj = c.newInstance();
            Method method = c.getMethod("say", null);
            method.invoke(obj, null);
            System.out.println(c.getClassLoader().toString());
        }
    }
}

