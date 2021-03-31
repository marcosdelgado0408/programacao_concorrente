package br.ufrn.segUnidade;

import br.ufrn.Point;

import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ExecutorClassify {

    private static final int coreCount = Runtime.getRuntime().availableProcessors(); // coreCount = 4
    private static final Executor exec = Executors.newFixedThreadPool(coreCount);


    public double classifyPoint(Point[] arr, int dataPointsNumber, int k, Point point){ // metodo para classificação de um ponto desconhecido p


        System.out.println("Core Count: " + Runtime.getRuntime().availableProcessors());

        Runnable eucDistTask = () -> {

            for(int i=0;i<dataPointsNumber;i++){
                arr[i].distance = Math.sqrt((arr[i].x - point.x) * (arr[i].x - point.x) + (arr[i].y - point.y) * (arr[i].y - point.y)); // calculo distância euclidiana
            }

        };


        Runnable sortTask = ()-> {

            Arrays.sort(arr, new Comparator<Point>() {   // ordenar o array por distância -> crescente
                @Override
                public int compare(Point o1, Point o2) {
                    return Double.compare(o1.distance, o2.distance);
                }
            });

        };



        /*
         * irá pesquisar os valores até uma distância determinada k do ponto desconhecido p
         * */

        Runnable mediaTask = ()->{
            double media = 0;
            for(int i=0;i<k;i++){
                media += arr[i].val; // media dos precos
            }

            media = media / k;
            System.out.println(media);
        };


        exec.execute(eucDistTask);
        exec.execute(sortTask);
        exec.execute(mediaTask);



        return 0;
    }


}
