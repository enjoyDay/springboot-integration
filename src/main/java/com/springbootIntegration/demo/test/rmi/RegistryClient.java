package com.springbootIntegration.demo.test.rmi;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * @author liukun
 * @description 远程调用的客户端
 * @date 2019/11/25
 */
public class RegistryClient {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry(1099);
            HelloInterface helloInterface = (HelloInterface) registry.lookup("helloInterface");
            String response = helloInterface.hello("客户端");
            System.out.println("=======> " + response + " <=======");
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }
}
