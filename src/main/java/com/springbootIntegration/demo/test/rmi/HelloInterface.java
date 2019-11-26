package com.springbootIntegration.demo.test.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author liukun
 * @description 接口，用于远程调用，接口需要继承Remote
 * @date 2019/11/25
 */
public interface HelloInterface  extends Remote {
    String hello(String name)throws RemoteException;
}
