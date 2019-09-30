package com.springbootIntegration.demo.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;

/**
 * @author liukun
 * @description 序列化工具类
 * @date 2019/9/14
 */
public final class SerializeUtil {
    private static final Logger logger = LogManager.getLogger();

    private SerializeUtil() {
    }

    public static final byte[] serialize(Object object) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;

        byte[] var3;
        try {
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            var3 = baos.toByteArray();
        } catch (IOException var15) {
            throw new RuntimeException(var15.getMessage(), var15);
        } finally {
            try {
                if (oos != null) {
                    oos.close();
                }
            } catch (Exception var14) {
                logger.error("", var14);
            }

            try {
                if (baos != null) {
                    baos.close();
                }
            } catch (Exception var13) {
                logger.error("", var13);
            }

        }

        return var3;
    }

    public static final Object deserialize(byte[] bytes) {
        return deserialize(bytes, Object.class);
    }

    public static final <K> K deserialize(byte[] bytes, Class<K> cls) {
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = null;

        K var4;
        try {
            ois = new ObjectInputStream(bais);
            var4 = (K)ois.readObject();
        } catch (IOException var17) {
            throw new RuntimeException(var17.getMessage(), var17);
        } catch (ClassNotFoundException var18) {
            throw new RuntimeException(var18.getMessage(), var18);
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
            } catch (Exception var16) {
                logger.error("", var16);
            }

            try {
                if (bais != null) {
                    bais.close();
                }
            } catch (Exception var15) {
                logger.error("", var15);
            }

        }

        return var4;
    }
}

