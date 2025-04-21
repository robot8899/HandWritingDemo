package com.robin.proxyDemo;


import java.io.IOException;

/**
 * @description: 类的描述信息
 * @author: 罗彬
 * @time: 2025/4/21 7:23
 */
public class MainDemo {
    public static void main(String[] args) throws Exception {
        System.out.println("--------------------------");
        MyInterFace myInterFace1 = MyInterFaceFactory.create(methodName ->
                "        System.out.println(\"" + methodName + "\");");
        myInterFace1.fun1();
        myInterFace1.fun2();
        myInterFace1.fun3();
        System.out.println("--------------------------");

        myInterFace1 = MyInterFaceFactory.create(methodName ->
        {
            StringBuilder builder = new StringBuilder();
            builder.append("        System.out.println(\"1111\");\n")
                    .append("        System.out.println(\"" + methodName + "\");\n");
            return builder.toString();
        });
        myInterFace1.fun1();
        myInterFace1.fun2();
        myInterFace1.fun3();
        System.out.println("--------------------------");

        myInterFace1 = MyInterFaceFactory.create(new MyHandlerImpl(myInterFace1));
        myInterFace1.fun1();
        myInterFace1.fun2();
        myInterFace1.fun3();

    }


}
