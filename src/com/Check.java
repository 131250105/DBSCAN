package com;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac on 17/7/17.
 */
public class Check {


    public static void main(String[] args) throws Exception{
        BufferedReader file = new BufferedReader(new FileReader("/Users/mac/Desktop/result.txt"));
        List<Point> points = new ArrayList<Point>();
        String line = null;
        //测试的时候先读一行，到时候删掉
        line = file.readLine();
        int i = 0;
        while ((line = file.readLine()) != null) {
            String[] str = line.split(";");
            if(str.length>2){
                if(str[1].equals("k")) {
                    System.out.println(line);
                }
            }
//            String id = str[0];
//            double money = Double.parseDouble(str[1]);
//            double freq = Double.parseDouble(str[2]);
////
//            points.add(new Point(money,freq,id));
        }
        file.close();
    }


}
