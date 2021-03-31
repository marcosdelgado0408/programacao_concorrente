package br.ufrn.segUnidade;

import br.ufrn.Point;

import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.*;

public class CallableFutureClassify {
    private static final int coreCount = Runtime.getRuntime().availableProcessors(); // coreCount = 4
    private static final ExecutorService service = Executors.newFixedThreadPool(coreCount);

    public double classifyPoint(Point[] arr, int dataPointsNumber, int k, Point point) throws ExecutionException, InterruptedException { // metodo para classificação de um ponto desconhecido p


        Callable distTask = new Callable() {
            @Override
            public Object call() throws Exception {

                for(int i=0;i<dataPointsNumber;i++){
                    arr[i].distance = Math.sqrt((arr[i].x - point.x) * (arr[i].x - point.x) + (arr[i].y - point.y) * (arr[i].y - point.y)); // calculo distância euclidiana
                }

                return null;
            }
        };


        Future euclDistFuture = service.submit(distTask); // performar a task
        /*
        * ira somemte pegar o retorno da funcao quando a task for finalizada
        * */
//        Integer result = euclDistFuture.get();


       Callable sortTask = new Callable() {
           @Override
           public Object call() throws Exception {
               Arrays.sort(arr, new Comparator<Point>() { // ordenar o array por distância -> crescente
                   @Override
                   public int compare(Point o1, Point o2) {
                       return Double.compare(o1.distance, o2.distance);
                   }
               });
               return null;
           }
       };

       Future sortFuture = service.submit(sortTask);



        /*
         * irá pesquisar os valores até uma distância determinada k do ponto desconhecido p
         * */


        Callable<Double> mediaTask = new Callable<Double>() {
            @Override
            public Double call() throws Exception {
                double media = 0;
                for(int i=0;i<k;i++){
                    media += arr[i].val; // media dos precos
                }

                media = media / k;
                System.out.println(media);

                return media;
            }
        };

        Future<Double> mediaFuture = service.submit(mediaTask);

        Double media = mediaFuture.get();



        return media;

    }
}
