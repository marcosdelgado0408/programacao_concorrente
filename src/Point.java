/*
* Nesse algoritmo, vamos medir a distância euclidiana de um ponto desconhecido P(ponto que será classificado pela sua distância de um grupo) a cada
* ponto de um training data sample
* */

import java.util.*;



public class Point {
    public int val;
    public double x, y; // coordenada do ponto
    public double distance;


    public int getVal() { return val; }
    public double getX() { return x; }
    public double getY() { return y; }
    public double getDistance() { return distance; }

    public void setVal(int val) {this.val = val; }
    public void setX(double x) { this.x = x; }
    public void setY(double y) { this.y = y; }
    public void setDistance(double distance) { this.distance = distance; }



//    public boolean comparison(Point a, Point b){
//        return a.distance < b.distance;
//    }


    public int classifyPoint(Point[] arr, int dataPointsNumber, int k, Point point){ // metodo para classificação de um ponto desconhecido p
        for(int i=0;i<dataPointsNumber;i++){
            arr[i].distance = Math.sqrt((arr[i].x - point.x) * (arr[i].x - point.x) + (arr[i].y - point.y) * (arr[i].y - point.y)); // calculo distância euclidiana
        }

        Arrays.sort(arr, new Comparator<Point>() { // ordenar o array por distância -> crescente
            @Override
            public int compare(Point o1, Point o2) {
                return Double.compare(o1.distance, o2.distance);
            }
        });


        for(int i=0;i<arr.length;i++){
            System.out.println(arr[i].distance);
        }


        /*
        * irá pesquisar os valores até uma distância determinada k do ponto desconhecido p
        * */
        int freq1 = 0;
        int freq2 = 0;
        for(int i=0;i<k;i++){
            if(arr[i].val == 0){
                freq1++;
            }
            else if(arr[i].val == 1){
                freq2++;
            }
        }
        return (freq1 > freq2 ? 0 : 1);

    }








}
