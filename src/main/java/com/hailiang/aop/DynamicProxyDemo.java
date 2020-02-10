package com.hailiang.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class DynamicProxyDemo {

    public static void main(String[] args) {
        Hello hello = new HelloImpl();
        // 普通方法调用
        hello.sayHello();
        HelloImpl helloImpl = new HelloImpl();
        ClassLoader classLoader = helloImpl.getClass().getClassLoader();
        Class<?>[] interfaces = helloImpl.getClass().getInterfaces();
        // jdk的动态代理是以接口为中心，如果要对没有实现接口的类进行动态代理可以使用cglib
        Hello proxyHello =  (Hello) Proxy.newProxyInstance(classLoader, interfaces, new MyInvocationHandler(helloImpl));
        // com.hailiang.aop.$Proxy0 cannot be cast to com.hailiang.aop.DynamicProxyDemo$HelloImpl
//        HelloImpl proxyHello =  (HelloImpl) Proxy.newProxyInstance(classLoader, interfaces, new MyInvocationHandler(helloImpl));

        // 添加动态代理方法调用
        proxyHello.sayHello();
    }

    interface Hello {
        void sayHello();
    }

    static class HelloImpl implements Hello{
        @Override
        public void sayHello() {
            System.out.println("Hello world");
        }
    }

    static class MyInvocationHandler implements InvocationHandler {

        private Object target;

        public MyInvocationHandler(Object target) {
            this.target = target;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("invoking sayHello");
            Object result = method.invoke(target, args);
            return result;
        }
    }
}
