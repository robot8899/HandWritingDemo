package com.robin.proxyDemo;

import java.lang.reflect.Field;

public class MyHandlerImpl implements MyHandler {
    private MyInterFace myInterFace;

    public MyHandlerImpl(MyInterFace myInterFace) {
        this.myInterFace = myInterFace;
    }

    @Override
    public void setProxy(MyInterFace proxy) {
        Field myInterFace1 =null;
        try {
            /**
             * 对MyInterFaceImpl中的MyInterFace进行赋值
             */
            Class<? extends MyInterFace> aClass = proxy.getClass();
            myInterFace1 = aClass.getDeclaredField("myInterFace");
            myInterFace1.setAccessible(true);
            
            myInterFace1.set(proxy,myInterFace);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public String FunctionBody(String MethodName) {
        StringBuilder builder = new StringBuilder();
        builder.append("        System.out.println(\"before\");\n")
                .append("        myInterFace." + MethodName + "();\n")
                .append("        System.out.println(\"after \");\n");
        return builder.toString();
    }
}