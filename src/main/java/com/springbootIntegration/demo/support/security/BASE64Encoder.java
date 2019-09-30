
package com.springbootIntegration.demo.support.security;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PushbackInputStream;

public class BASE64Encoder {
    private static final char[] PEM_ARRAY = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '+', '/'};
    private static final byte[] pem_convert_array = new byte[256];
    private byte[] decodeBuffer = new byte[4];

    public BASE64Encoder() {
    }

    public String encode(byte[] bt) {
        int totalBits = bt.length * 8;
        int nn = totalBits % 6;
        int curPos = 0;

        StringBuilder toReturn;
        for(toReturn = new StringBuilder(32); curPos < totalBits; curPos += 6) {
            int bytePos = curPos / 8;
            int pos;
            switch(curPos % 8) {
            case 0:
                toReturn.append(PEM_ARRAY[(bt[bytePos] & 252) >> 2]);
            case 1:
            case 3:
            case 5:
            default:
                break;
            case 2:
                toReturn.append(PEM_ARRAY[bt[bytePos] & 63]);
                break;
            case 4:
                if (bytePos == bt.length - 1) {
                    toReturn.append(PEM_ARRAY[(bt[bytePos] & 15) << 2 & 63]);
                } else {
                    pos = ((bt[bytePos] & 15) << 2 | (bt[bytePos + 1] & 192) >> 6) & 63;
                    toReturn.append(PEM_ARRAY[pos]);
                }
                break;
            case 6:
                if (bytePos == bt.length - 1) {
                    toReturn.append(PEM_ARRAY[(bt[bytePos] & 3) << 4 & 63]);
                } else {
                    pos = ((bt[bytePos] & 3) << 4 | (bt[bytePos + 1] & 240) >> 4) & 63;
                    toReturn.append(PEM_ARRAY[pos]);
                }
            }
        }

        if (nn == 2) {
            toReturn.append("==");
        } else if (nn == 4) {
            toReturn.append("=");
        }

        return toReturn.toString();
    }

    public byte[] decode(String str) throws IOException {
        byte[] arrayOfByte = str.getBytes();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(arrayOfByte);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        this.decodeBuffer(inputStream, outputStream);
        return outputStream.toByteArray();
    }

    private void decodeBuffer(InputStream paramInputStream, OutputStream paramOutputStream) throws IOException {
        PushbackInputStream localPushbackInputStream = new PushbackInputStream(paramInputStream);
        int j = 0;

        while(true) {
            while(true) {
                try {
                    int k = this.bytesPerLine();
                    int i = 0;
                    if (i + this.bytesPerAtom() < k) {
                        this.decodeAtom(localPushbackInputStream, paramOutputStream, this.bytesPerAtom());
                        j += this.bytesPerAtom();
                        int var10000 = i + this.bytesPerAtom();
                    } else if (i + this.bytesPerAtom() == k) {
                        this.decodeAtom(localPushbackInputStream, paramOutputStream, this.bytesPerAtom());
                        j += this.bytesPerAtom();
                    } else {
                        this.decodeAtom(localPushbackInputStream, paramOutputStream, k - i);
                        j += k - i;
                    }
                } catch (RuntimeException var7) {
                    String.valueOf(j);
                    return;
                }
            }
        }
    }

    private int bytesPerAtom() {
        return 4;
    }

    private int bytesPerLine() {
        return 72;
    }

    private void decodeAtom(PushbackInputStream paramPushbackInputStream, OutputStream paramOutputStream, int paramInt) throws IOException {
        int j = -1;
        int k = -1;
        int m = -1;
        int n = -1;
        if (paramInt < 2) {
            throw new ArrayStoreException("BASE64Decoder: Not enough bytes for an atom.");
        } else {
            int i;
            do {
                i = paramPushbackInputStream.read();
                if (i == -1) {
                    throw new RuntimeException();
                }
            } while(i == 10 || i == 13);

            this.decodeBuffer[0] = (byte)i;
            i = this.readFully(paramPushbackInputStream, this.decodeBuffer, 1, paramInt - 1);
            if (i == -1) {
                throw new RuntimeException();
            } else {
                if (paramInt > 3 && this.decodeBuffer[3] == 61) {
                    paramInt = 3;
                }

                if (paramInt > 2 && this.decodeBuffer[2] == 61) {
                    paramInt = 2;
                }

                switch(paramInt) {
                case 4:
                    n = pem_convert_array[this.decodeBuffer[3] & 255];
                case 3:
                    m = pem_convert_array[this.decodeBuffer[2] & 255];
                case 2:
                    k = pem_convert_array[this.decodeBuffer[1] & 255];
                    j = pem_convert_array[this.decodeBuffer[0] & 255];
                default:
                    switch(paramInt) {
                    case 2:
                        paramOutputStream.write((byte)(j << 2 & 252 | k >>> 4 & 3));
                        break;
                    case 3:
                        paramOutputStream.write((byte)(j << 2 & 252 | k >>> 4 & 3));
                        paramOutputStream.write((byte)(k << 4 & 240 | m >>> 2 & 15));
                        break;
                    case 4:
                        paramOutputStream.write((byte)(j << 2 & 252 | k >>> 4 & 3));
                        paramOutputStream.write((byte)(k << 4 & 240 | m >>> 2 & 15));
                        paramOutputStream.write((byte)(m << 6 & 192 | n & 63));
                    }

                }
            }
        }
    }

    private int readFully(InputStream paramInputStream, byte[] paramArrayOfByte, int paramInt1, int paramInt2) throws IOException {
        for(int i = 0; i < paramInt2; ++i) {
            int j = paramInputStream.read();
            if (j == -1) {
                return i == 0 ? -1 : i;
            }

            paramArrayOfByte[i + paramInt1] = (byte)j;
        }

        return paramInt2;
    }

    static {
        int i;
        for(i = 0; i < 255; ++i) {
            pem_convert_array[i] = -1;
        }

        for(i = 0; i < PEM_ARRAY.length; ++i) {
            pem_convert_array[PEM_ARRAY[i]] = (byte)i;
        }

    }
}
