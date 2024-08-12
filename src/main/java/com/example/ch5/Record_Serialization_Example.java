package com.example.ch5;

import java.io.*;

public class Record_Serialization_Example {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        var point = new Point(23, 42);

        try (var out = new ObjectOutputStream(new FileOutputStream("point.data"))) {
            out.writeObject(point);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        var in = new ObjectInputStream(new FileInputStream("point.data"));

        var point_result = in.readObject();

        System.out.println(point_result);
    }
}
