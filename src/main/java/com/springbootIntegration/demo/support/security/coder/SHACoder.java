//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.springbootIntegration.demo.support.security.coder;


import com.springbootIntegration.demo.support.security.SecurityCoder;

public abstract class SHACoder extends SecurityCoder {
    public SHACoder() {
    }

    public static byte[] encodeSHA(String data) throws Exception {
        return digest("SHA", data);
    }

    public static byte[] encodeSHA1(String data) throws Exception {
        return digest("SHA-1", data);
    }

    public static byte[] encodeSHA256(String data) throws Exception {
        return digest("SHA-256", data);
    }

    public static byte[] encodeSHA384(String data) throws Exception {
        return digest("SHA-384", data);
    }

    public static byte[] encodeSHA512(String data) throws Exception {
        return digest("SHA-512", data);
    }

    public static byte[] encodeSHA224(String data) throws Exception {
        return digest("SHA-224", data);
    }
}
