package com.honestwalker.android.rectroid;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by lanzhe on 16-11-24.
 */
public class ProxyEvent implements InvocationHandler {

    private Method callMethod;
    private String methodName;
    private Object handler;

    public Object newInstance(Object handler, Method callMethod, String methodName ,Class[] interfaces) {
        this.callMethod = callMethod;
        this.handler = handler;
        this.methodName = methodName;
        return Proxy.newProxyInstance(ProxyEvent.class.getClassLoader(),
                interfaces, this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if(method.getName().equals(methodName)) {
            return callMethod.invoke(handler);
        }
        return null;
    }

}
