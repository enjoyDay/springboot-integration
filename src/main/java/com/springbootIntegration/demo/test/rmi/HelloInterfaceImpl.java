package com.springbootIntegration.demo.test.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * @author liukun
 * @description
 * @date 2019/11/25
 */
public class HelloInterfaceImpl extends UnicastRemoteObject implements HelloInterface {
    protected HelloInterfaceImpl() throws RemoteException {
        super();
    }
    @Override
    public String hello(String name) throws RemoteException {
        return "你好：" + name;
    }
}
