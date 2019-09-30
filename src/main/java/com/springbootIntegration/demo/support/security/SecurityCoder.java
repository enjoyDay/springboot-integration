//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.springbootIntegration.demo.support.security;

import java.security.MessageDigest;
import java.security.Security;

public abstract class SecurityCoder {
    private static Byte ADDFLAG = 0;

    public SecurityCoder() {
    }

    public static byte[] digest(String algorithm, String data) throws Exception {
        MessageDigest md = MessageDigest.getInstance(algorithm);
        return md.digest(data.getBytes("UTF-8"));
    }

    static {
        if (ADDFLAG == 0) {
            Security.addProvider(new BouncyCastleProvider());
            ADDFLAG = 1;
        }

    }
}
