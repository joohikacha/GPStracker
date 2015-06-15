package com.joohi.gpstracker;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by joohi_k on 11-06-2015.
 */
public class coordinates implements Serializable {
    double latitude;
    double longitude;
    coordinates(double lat, double lon)
    {
        this.latitude=lat;
        this.longitude=lon;
    }

   /* public byte[] getByteArrayObject(coordinates segment[]){

        byte[] byteArrayObject = null;
        try {

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(segment);

            oos.close();
            bos.close();
            byteArrayObject = bos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return byteArrayObject;
        }
        return byteArrayObject;
    }
    //converting byte[] to SimpleExample
    public coordinates getJavaObject(byte[] convertObject){
        coordinates segment = null;

        ByteArrayInputStream bais;
        ObjectInputStream ins;
        try {

            bais = new ByteArrayInputStream(convertObject);

            ins = new ObjectInputStream(bais);
            segment  =(coordinates)ins.readObject();

            ins.close();

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return segment ;
    }
    */
}
