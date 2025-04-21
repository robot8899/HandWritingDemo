package com.robin.proxyDemo;

/**
 * @description: 类的描述信息
 * @author: 罗彬
 * @time: 2025/4/21 7:43
 */
public interface MyHandler  {
    default void setProxy(MyInterFace myInterFace){

    };
    String FunctionBody(String MethodName);
}
