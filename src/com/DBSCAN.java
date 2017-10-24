package com;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac on 17/7/5.
 */
public class DBSCAN {

    private String filePath = "/Users/mac/Desktop/data.txt";
    private String resultPath = "/Users/mac/Desktop/result.txt";
    private double e = 200; // 半径
    private int minp = 5;// 密度阈值
    private List<List<Point>> resultList = new ArrayList<List<Point>>();



    public DBSCAN(String filePath, String resultPath, double e, int minp){
        this.filePath = filePath;
        this.resultPath = resultPath;
        this.e = e;
        this.minp = minp;
    }

    public List<Point> readFile() throws Exception {
        BufferedReader file = new BufferedReader(new FileReader(filePath));
        List<Point> points = new ArrayList<Point>();
        String line = null;
        //测试的时候先读一行，到时候删掉
        line = file.readLine();
        int i = 0;
        while ((line = file.readLine()) != null) {
            String[] str = mySplit(line, '$');
            String id = str[0];
            double money = Double.parseDouble(str[1]);
            double freq = Double.parseDouble(str[2])/100;
//            String p[] = line.split("[;|,|\\s]");
//            List<Double> location = new ArrayList<Double>();
//            for (int j = 0; j < p.length; j++) {
//                location.add(Double.parseDouble(p[j]));
//            }
//            location.add(money);
            points.add(new Point(money,freq,id));
        }
        file.close();
        return points;
    }

    public void saveResult() throws Exception {
        BufferedWriter bw = new BufferedWriter(new FileWriter(resultPath));
        int i = 1;
        for (List<Point> mic : resultList) {
            bw.write("a new class:");
            bw.newLine();
            for (Point p : mic) {
                StringBuffer sb = new StringBuffer();
                sb.append(p.getId()).append(";");
                if(p.isKey()){
                    sb.append("k;x:").append(p.getX()).append(" ");
                    sb.append("k;y:").append(p.getY());
                }else{
                    sb.append("n;x:").append(p.getX()).append(" ");
                    sb.append("n;y:").append(p.getY());
                }
                bw.write(sb.append(i).toString());
                bw.newLine();
            }
            i++;
        }
        bw.flush();
        bw.close();
    }

    public  double getDistance(Point p, Point q) {

        double dx = p.getX() - q.getX();

        double dy = p.getY() - q.getY();

        double distance = Math.sqrt(dx * dx + dy * dy);

        return distance;

    }

    public  List<Point> isKeyPoint(List<Point> lst, Point p) {
        int count = 0;
        List<Point> tmpLst = new ArrayList<Point>();
        for (Point q : lst) {
            if (getDistance(p, q) <= e) {
                ++count;
                if (!tmpLst.contains(q)) {
                    tmpLst.add(q);
                }
            }
        }
        if (count >= minp) {
            p.setKey(true);
            return tmpLst;
        }
        return null;
    }

    public boolean mergeList(List<Point> a, List<Point> b) {
        boolean merge = false;
        for (int index = 0; index < b.size(); ++index) {
            if (a.contains(b.get(index))) {
                merge = true;
                break;
            }
        }
        if (merge) {
            for (int index = 0; index < b.size(); ++index) {
                if (!a.contains(b.get(index))) {
                    a.add(b.get(index));
                }
            }
        }
        return merge;
    }

    public void setListClassed(List<Point> lst) {
        for (Point p : lst) {
            if (!p.isClassed()) {
                p.setClassed(true);
            }
        }
    }

    private void applyDbscan(List<Point> pointsList) {
        for (Point p : pointsList) {
            if (!p.isClassed()) {
                List<Point> tmpLst = new ArrayList<Point>();
                if ((tmpLst = isKeyPoint(pointsList, p)) != null) {
                    setListClassed(tmpLst);
                    resultList.add(tmpLst);
                }
            }
        }
    }

    private List<List<Point>> getResult(List<Point> lst) {
        applyDbscan(lst);//找到所有直达的聚类
        int length = resultList.size();
        for (int i = 0; i < length; ++i) {
            for (int j = i + 1; j < length; ++j) {
                if (mergeList(resultList.get(i), resultList.get(j))) {
                    resultList.get(j).clear();
                }
            }
        }
        return resultList;
    }

    private String[] mySplit(String str, char token) {
        List<String> list = new ArrayList<String>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char currentchar = str.charAt(i);
            if (currentchar == token) {
                list.add(sb.toString());
                sb.setLength(0);
            } else {
                sb.append(currentchar);
            }
        }

        list.add(sb.toString());
        return list.toArray(new String[list.size()]);
    }

    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        DBSCAN dbscan = new DBSCAN(args[0],args[1],Double.parseDouble(args[2]),Integer.parseInt(args[3]));
        List<Point> points = dbscan.readFile();
        dbscan.getResult(points);
        dbscan.saveResult();
        System.out.println("use time:" + (System.currentTimeMillis() - start));
    }
}
