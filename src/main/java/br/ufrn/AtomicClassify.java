package br.ufrn;

import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.atomic.AtomicReferenceArray;

public class AtomicClassify {

    public double classifyPoint(Point[] arr, int dataPointsNumber, int k, Point point){ // metodo para classificação de um ponto desconhecido p



        AtomicReferenceArray<Point> atomicArr = new AtomicReferenceArray<Point>(arr);
        System.out.println(atomicArr.get(0).distance);



        Thread threadDistEucl = new Thread( ()-> {



            for(int i=0;i<dataPointsNumber;i++){
                Point point1 = new Point();
                point1.x = atomicArr.get(i).x;
                point1.y = atomicArr.get(i).y;
                point1.val = atomicArr.get(i).val;
                point1.distance = Math.sqrt((atomicArr.get(i).x - point.x) * (atomicArr.get(i).x - point.x) + (atomicArr.get(i).y - point.y) * (atomicArr.get(i).y - point.y));

                atomicArr.set(i, point1);

            }

        });



        Thread comparator = new Thread( () -> {

            //Não possuo controle do algoritmo de ordenção
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
