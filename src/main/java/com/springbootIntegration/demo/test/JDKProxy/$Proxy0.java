package com.springbootIntegration.demo.test.JDKProxy;
public class $Proxy0 {

}
/*
 // @author liukun
 // @description 这个只是java Proxy自动创建的代理类
 // 我单独读出来进行分析用
 // @date 2020/4/4

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.UndeclaredThrowableException;

// 注意，这里可知是继承了Proxy，由于是但继承，因此java的代理必须是实现接口
// 注意，这里为什么是继承Proxy类，因为创建的时候是把我们原来实现了InvocationHandler接口的
// 类传进到Proxy中，作为h参数，
public final class $Proxy0 extends Proxy implements HelloWorldImpl {
    private static Method m1;
    private static Method m4;
    private static Method m9;
    private static Method m2;
    private static Method m7;
    private static Method m3;
    private static Method m6;
    private static Method m8;
    private static Method m10;
    private static Method m0;
    private static Method m5;

    public $Proxy0(InvocationHandler var1) throws {
        super(var1);
    }

    public final boolean equals(Object var1) throws {
        try {
            return (Boolean) super.h.invoke(this, m1, new Object[]{var1});
        } catch (RuntimeException | Error var3) {
            throw var3;
        } catch (Throwable var4) {
            throw new UndeclaredThrowableException(var4);
        }
    }

    // 这个方法java通过扫描通过newInstance方法传进去的所有接口的方法
    public final void sayHi() throws {
        try {
            // 这里的super.h对应的就是父类的InvocationHandler h，而这个参数是创建的时候实际实现该接口的类传进去的
            // 因此也是调用的我们实现该接口类的invoke方法，可知，接口在这起到了重要作用!
            // 这里的m4是反射过来的实现类（这个实现类是我们接口的实现类）的方法
            super.h.invoke(this, m4, (Object[]) null);
        } catch (RuntimeException | Error var2) {
            throw var2;
        } catch (Throwable var3) {
            throw new UndeclaredThrowableException(var3);
        }
    }

    public final void notify() throws {
        try {
            super.h.invoke(this, m9, (Object[]) null);
        } catch (RuntimeException | Error var2) {
            throw var2;
        } catch (Throwable var3) {
            throw new UndeclaredThrowableException(var3);
        }
    }

    public final String toString() throws {
        try {
            return (String) super.h.invoke(this, m2, (Object[]) null);
        } catch (RuntimeException | Error var2) {
            throw var2;
        } catch (Throwable var3) {
            throw new UndeclaredThrowableException(var3);
        }
    }

    public final void wait(long var1) throws InterruptedException {
        try {
            super.h.invoke(this, m7, new Object[]{var1});
        } catch (RuntimeException | InterruptedException | Error var4) {
            throw var4;
        } catch (Throwable var5) {
            throw new UndeclaredThrowableException(var5);
        }
    }

    public final void sayHelloWorld() throws {
        try {
            super.h.invoke(this, m3, (Object[]) null);
        } catch (RuntimeException | Error var2) {
            throw var2;
        } catch (Throwable var3) {
            throw new UndeclaredThrowableException(var3);
        }
    }

    public final void wait(long var1, int var3) throws InterruptedException {
        try {
            super.h.invoke(this, m6, new Object[]{var1, var3});
        } catch (RuntimeException | InterruptedException | Error var5) {
            throw var5;
        } catch (Throwable var6) {
            throw new UndeclaredThrowableException(var6);
        }
    }

    public final Class getClass() throws {
        try {
            return (Class) super.h.invoke(this, m8, (Object[]) null);
        } catch (RuntimeException | Error var2) {
            throw var2;
        } catch (Throwable var3) {
            throw new UndeclaredThrowableException(var3);
        }
    }

    public final void notifyAll() throws {
        try {
            super.h.invoke(this, m10, (Object[]) null);
        } catch (RuntimeException | Error var2) {
            throw var2;
        } catch (Throwable var3) {
            throw new UndeclaredThrowableException(var3);
        }
    }

    public final int hashCode() throws {
        try {
            return (Integer) super.h.invoke(this, m0, (Object[]) null);
        } catch (RuntimeException | Error var2) {
            throw var2;
        } catch (Throwable var3) {
            throw new UndeclaredThrowableException(var3);
        }
    }

    public final void wait() throws InterruptedException {
        try {
            super.h.invoke(this, m5, (Object[]) null);
        } catch (RuntimeException | InterruptedException | Error var2) {
            throw var2;
        } catch (Throwable var3) {
            throw new UndeclaredThrowableException(var3);
        }
    }

    // 静态代码块
    // 代理类通过反射技术来获取被代理类（也就是我们自己真实实现类）的详细信息，比如各种方法
    static {
        try {
            m1 = Class.forName("java.lang.Object").getMethod("equals", Class.forName("java.lang.Object"));
            m4 = Class.forName("com.springbootIntegration.demo.test.JDKProxy.HelloWorldImpl").getMethod("sayHi");
            m9 = Class.forName("com.springbootIntegration.demo.test.JDKProxy.HelloWorldImpl").getMethod("notify");
            m2 = Class.forName("java.lang.Object").getMethod("toString");
            m7 = Class.forName("com.springbootIntegration.demo.test.JDKProxy.HelloWorldImpl").getMethod("wait", Long.TYPE);
            m3 = Class.forName("com.springbootIntegration.demo.test.JDKProxy.HelloWorldImpl").getMethod("sayHelloWorld");
            m6 = Class.forName("com.springbootIntegration.demo.test.JDKProxy.HelloWorldImpl").getMethod("wait", Long.TYPE, Integer.TYPE);
            m8 = Class.forName("com.springbootIntegration.demo.test.JDKProxy.HelloWorldImpl").getMethod("getClass");
            m10 = Class.forName("com.springbootIntegration.demo.test.JDKProxy.HelloWorldImpl").getMethod("notifyAll");
            m0 = Class.forName("java.lang.Object").getMethod("hashCode");
            m5 = Class.forName("com.springbootIntegration.demo.test.JDKProxy.HelloWorldImpl").getMethod("wait");
        } catch (NoSuchMethodException var2) {
            throw new NoSuchMethodError(var2.getMessage());
        } catch (ClassNotFoundException var3) {
            throw new NoClassDefFoundError(var3.getMessage());
        }
    }
}
*/
