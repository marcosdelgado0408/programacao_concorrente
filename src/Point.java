/*
* Nesse algoritmo, vamos medir a distância euclidiana de um ponto desconhecido P(ponto que será classificado pela sua distância de um grupo) a cada
* ponto de um training data sample
* */

import java.util.*;



public class Point extends Thread{
    public double val;
    public double x, y; // coordenada do ponto
    public double distance;

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


    public double classifyPoint(Point[] arr, int dataPointsNumber, int k, Point point){ // metodo para classificação de um ponto desconhecido p


        for(int i=0;i<dataPointsNumber;i++){

            int finalI = i; // o metodo so aceita que ele utilize uma variavel incializada
            Thread thread = new Thread(){
                @Override
                public void run() {
                    arr[finalI].distance = Math.sqrt((arr[finalI].x - point.x) * (arr[finalI].x - point.x) + (arr[finalI].y - point.y) * (arr[finalI].y - point.y)); // calculo distância euclidiana
                }
            };


        }

        Arrays.sort(arr, new Comparator<Point>() { // ordenar o array por distância -> crescente
            @Override
            public int compare(Point o1, Point o2) {
                return Double.compare(o1.distance, o2.distance);
            }
        });


        /*
        * irá pesquisar os valores até uma distância determinada k do ponto desconhecido p
        * */

        double media = 0;
        for(int i=0;i<k;i++){
            media += arr[i].val; // media dos precos
        }

        media = media / k;

        return media;

    }



}
