package com.etong.pt.utility;


import java.io.*;

/**
 * Created by Administrator on 2015/11/5.
 */
public class SerializeHelper {

    public static byte[] objectToByte(Object obj) {
        if (obj == null) {
            return null;
        }
        byte[] bytes = null;
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(obj);
            bytes = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    public static <T> T byteToObject(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        T t = null;
        try {
            ByteArrayInputStream bi = new ByteArrayInputStream(bytes);
            ObjectInputStream oi = new ObjectInputStream(new BufferedInputStream(bi));
            t = (T) oi.readObject();
            bi.close();
            oi.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return t;
    }
}

