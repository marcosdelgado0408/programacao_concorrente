/*
 * Nesse algoritmo, vamos medir a distância euclidiana de um ponto desconhecido P(ponto que será classificado pela sua distância de um grupo) a cada
 * ponto de um training data sample
 * */

import java.util.*;


public class Point {

    public double val;
    public double x, y; // coordenada do ponto
    public double distance;
    public double media;

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

        Thread threadDistEucl = new Thread(){
            @Override
            public void run(){
                for(int i=0;i<dataPointsNumber;i++){
                    arr[i].distance = Math.sqrt((arr[i].x - point.x) * (arr[i].x - point.x) + (arr[i].y - point.y) * (arr[i].y - point.y)); // calculo distância euclidiana
                }
            }
        };
        threadDistEucl.start();


        Thread comparator =  new Thread(){
            @Override
            public void run(){

                Arrays.sort(arr, new Comparator<Point>() { // ordenar o array por distância -> crescente
                    @Override
                    public int compare(Point o1, Point o2) {
                        return Double.compare(o1.distance, o2.distance);
                    }
                });

            }
        };
        comparator.start();



        /*
         * irá pesquisar os valores até uma distância determinada k do ponto desconhecido p
         * */


        Thread media1 =  new Thread(){
            @Override
            public void run(){

                double media = 0;
                for(int i=0;i<k;i++){
                    media += arr[i].val; // media dos precos
                }

                media = media / k;
                System.out.println(media);
            }
        };
        media1.start();

        return 0;

    }



}
