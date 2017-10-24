package com;

/**
 * Created by mac on 17/7/5.
 */
public class Point {

    private double x;
    private double y;
    private boolean isKey;
    private boolean isClassed;
    private String id ;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isKey() {
        return isKey;
    }

    public void setKey(boolean isKey) {
        this.isKey = isKey;
        this.isClassed = true;

    }

    public boolean isClassed() {
        return isClassed;
    }

    public void setClassed(boolean isClassed) {
        this.isClassed = isClassed;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public Point(double x, double y, String id) {
        this.x = x;
        this.y = y;
        this.id = id;
    }

    public Point(String str) {
        String[] p = str.split(",");
        this.x = Integer.parseInt(p[0]);
        this.y = Integer.parseInt(p[1]);
    }


    public  static  double getDistance(Point p, Point q) {

        double dx = p.getX() - q.getX();

        double dy = p.getY() - q.getY();

        double distance = Math.sqrt(dx * dx + dy * dy);

        return distance;

    }

    public static void main(String[] args){
        Point p = new Point(73.0,0.063751,"1");
        Point p1 = new Point(78.0,71.30724,"1");
        Point p3= new Point(80,1.335,"2");
        System.out.println(getDistance(p,p3));
        System.out.println(getDistance(p1,p3));
    }
}
