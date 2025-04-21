package com.robin.proxyDemo;



import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.ToolProvider;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @description: 类的描述信息
 * @author: 罗彬
 * @time: 2025/4/21 7:24
 */
public class MyInterFaceFactory {
    private final static AtomicInteger count =new AtomicInteger();


    public static  MyInterFace create(MyHandler myHandler) throws Exception {
        String className =getClassName();
        //编译
        createAndCompilerFile(className,myHandler);
        //加载这个class文件，并创建一个代理类对象
        MyInterFace myInterFace = newMyInterface(className,myHandler);

        return myInterFace;

    };
    private static void createAndCompilerFile(String className,MyHandler myHandler) throws Exception {
        Class<MyInterFace> myInterFaceClass = MyInterFace.class;
        Method[] methods = myInterFaceClass.getMethods();
        for (Method method : methods) {
            System.out.println(method.getName());

        }
        String fun1Body = myHandler.FunctionBody("fun1");
        String fun2Body = myHandler.FunctionBody("fun2");
        String fun3Body = myHandler.FunctionBody("fun3");
        String context = "package com.robin.proxyDemo;\n" +
                "\n" +
                "public class "+className+" implements MyInterFace{\n" +
                "    private MyInterFace myInterFace;\n"+
                "    @Override\n" +
                "    public void fun1() {\n" +
                fun1Body +
                "    }\n" +
                "\n" +
                "    @Override\n" +
                "    public void fun2() {\n" +
                fun2Body +
                "\n" +
                "    }\n" +
                "\n" +
                "    @Override\n" +
                "    public void fun3() {\n" +
                fun3Body +
                "\n" +
                "    }\n" +
                "}\n";

        //类名
//        File file = new File(className+".java");
//        Files.writeString(file.toPath(),context);
//        SimpleJavaCompiler.compile(file);

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        JavaFileObject source = new JavaSourceFromString("com.robin.proxyDemo." + className, context);
        JavaCompiler.CompilationTask task = compiler.getTask(
                null,                             // 默认输出
                null,                             // 使用默认文件管理器
                null,                             // 无诊断监听
                List.of("-d", "./target/classes"), // 指定输出目录
                null,                             // 类名集合
                List.of(source)                   // 编译源文件
        );
        System.out.println(task.call()?"编译成功":"编译失败");
    }

    /**
     *         //编译成功后
     *         //将这个类加载到JVM
     *         //接收一个全类名
     * @param className
     * @return
     * @throws Exception
     */
    private static MyInterFace newMyInterface(String className,MyHandler myHandler) throws Exception {
        //加载目录下的class文件
        Class<?> aClass = MyInterFaceFactory.class.getClassLoader().loadClass("com.robin.proxyDemo."+ className);
        Constructor<?> constructor = aClass.getConstructor();
        //创建对象
        MyInterFace proxy = (MyInterFace)constructor.newInstance();

        //对MyInterFaceImpl中的MyInterFace字段赋值
        myHandler.setProxy(proxy);
        return proxy;
    }

    private static String getClassName(){
        return "MyInterFace$Proxy"+count.incrementAndGet();
    }
}
