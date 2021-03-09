package br.ufrn;

public class Point {

    public double val;
    public double x, y; // coordenada do ponto
    public double distance;


    public double getVal() { return val; }
    public void setVal(double val) { this.val = val; }

    public double getX() { return x; }
    public void setX(double x) { this.x = x; }

    public double getY() { return y; }
    public void setY(double y) { this.y = y; }

    public double getDistance() { return distance; }
    public void setDistance(double distance) { this.distance = distance; }




    public Point(double x, double y, double val) {
        this.x = x;
        this.y = y;
        this.val = val;
    }

    public Point() {
        this.x = 0;
        this.y = 0;
        this.val = 0;
    }



}
