package com.robin.proxyDemo;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.util.Arrays;

/**
 * 简单的编译类
 */
public final class SimpleJavaCompiler {
    /**
     * 编译指定的 Java 文件，并将 .class 文件输出到 ./target/classes
     * @param javaFile Java 源码文件（例如：src/test/Hello.java）
     * @return true 表示编译成功，false 表示失败
     */
    public static void compile(File javaFile) {
        String outputDir = "./target/classes"; // 输出目录
        try {
            // 获取系统编译器
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            if (compiler == null) {
                System.err.println("找不到系统 Java 编译器，请确认使用的是 JDK。");
                return ;
            }

            // 获取标准文件管理器
            StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);

            // 设置编译选项
            Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjects(javaFile);
            Iterable<String> options = Arrays.asList("-d", outputDir);

            // 编译任务
            JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, null, options, null, compilationUnits);
            task.call();
            System.out.println("编译成功！！");
            fileManager.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}