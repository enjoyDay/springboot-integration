package com.springbootIntegration.demo.test.rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * @author liukun
 * @description naming客户端
 * @date 2019/11/26
 */
public class NamingClient {
    public static void main(String[] args) {
        try {
            String remoteAddr="rmi://localhost:1100/HelloNaming";
            HelloInterface hello = (HelloInterface) Naming.lookup(remoteAddr);
            String response = hello.hello("naming客户端");
            System.out.println("=======> " + response + " <=======");
        } catch (NotBoundException | RemoteException | MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
