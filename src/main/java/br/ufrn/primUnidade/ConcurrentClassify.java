package br.ufrn.primUnidade;

import br.ufrn.Point;

import java.util.Arrays;
import java.util.Comparator;

public class ConcurrentClassify {

    public double classifyPoint(Point[] arr, int dataPointsNumber, int k, Point point){ // metodo para classificação de um ponto desconhecido p


        Thread threadDistEucl = new Thread( ()-> {

            for(int i=0;i<dataPointsNumber;i++){
                arr[i].distance = Math.sqrt((arr[i].x - point.x) * (arr[i].x - point.x) + (arr[i].y - point.y) * (arr[i].y - point.y)); // calculo distância euclidiana
            }

        });



        Thread comparator = new Thread( () -> {

            Arrays.sort(arr, new Comparator<Point>() { // ordenar o array por distância -> crescente
                @Override
                public int compare(Point o1, Point o2) {
                    return Double.compare(o1.distance, o2.distance);
                }
            });

        });



        /*
         * irá pesquisar os valores até uma distância determinada k do ponto desconhecido p
         * */



        Thread fazerMedia = new Thread( ()->{

            double media = 0;
            for(int i=0;i<k;i++){
                media += arr[i].val; // media dos precos
            }

            media = media / k;
            System.out.println(media);


        });



        threadDistEucl.start();
        comparator.start();
        fazerMedia.start();




        return 0;

    }

}
