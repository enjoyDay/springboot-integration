package com.springbootIntegration.demo.test.rmi;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * @author liukun
 * @description rmi的服务端
 * @date 2019/11/25
 */
public class RegistryServer {
    public static void main(String[] args) {
        try {
            // 实例化远程对象注册表，端口1099
            Registry registry = LocateRegistry.createRegistry(1099);
            // 创建一个远程对象
            HelloInterface helloInterface = new HelloInterfaceImpl();
            // 把远程对象注册到RMI注册服务器上，在注册表中命名为helloInterface
            registry.rebind("helloInterface",helloInterface);
            System.out.println("======= 启动RMI服务成功! =======");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
