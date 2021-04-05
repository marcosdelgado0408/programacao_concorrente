package br.ufrn.segUnidade.ForkJoin;

import br.ufrn.Point;

import java.util.*;
import java.util.concurrent.ForkJoinPool;




public class ForkJoinClassify {

    // Acho melhor chamar um recursive action pra cada parte
    /* vou ter que provavelmente separar em 3 arquivos pras 3 partes do algorimo, e assim cada parte vai ser
    * uma classe com o extends, pq assim eu nao consigo chamar denovo a mesma funcao
    * */
    public Double classifyPoint(List<Point> arr, int dataPointsNumber ,int k, Point point){ // metodo para classificação de um ponto desconhecido p

        ForkJoinPool pool = new ForkJoinPool();
        RecursiveDist task1 = new RecursiveDist(arr, point, dataPointsNumber);

        arr = pool.invoke(task1);

        arr.sort(new  Comparator<>() { // ordenar o array por "distance" -> crescente
            @Override
            public int compare(Point o1, Point o2) {
                return Double.compare(o1.distance, o2.distance);
            }
        });


//        RecursiveMedia task2 = new RecursiveMedia(arr, k, 0);
//        pool.invoke(task2);



        // tambem da pra fazer dividir pra conquistar

        // transformando para um array de tamanho limitado
        List<Point> arrSizeOfK = new ArrayList<>();
        for(int i=0;i<k;i++){
            arrSizeOfK.add(arr.get(i));
        }

        RecursiveMedia task2 = new RecursiveMedia(arrSizeOfK, k);


        return pool.invoke(task2);

    }


}
