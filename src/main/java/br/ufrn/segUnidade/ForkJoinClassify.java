package br.ufrn.segUnidade;

import br.ufrn.Point;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.RecursiveAction;

public class ForkJoinClassify {

    private Point[] arr;
    private int dataPointsNumber;
    private int k;
    private Point point;



    public ForkJoinClassify(Point[] arr, int dataPointsNumber, int k, Point point) {
        this.arr = arr;
        this.dataPointsNumber = dataPointsNumber;
        this.k = k;
        this.point = point;
    }


    private Point[] computeDistDirectly(){
        Point[] poins = this.arr;

        for(Point p : poins){
            p.distance = Math.sqrt((p.x - point.x) * (p.x - point.x) + (p.y - point.y) * (p.y - point.y));
        }

        return poins;
    }


    // Acho melhor chamar um recursive action pra cada parte
    /* vou ter que provavelmente separar em 3 arquivos pras 3 partes do algorimo, e assim cada parte vai ser
    * uma classe com o extends, pq assim eu nao consigo chamar denovo a mesma funcao
    * */
    public double classifyPoint(Point[] arr, int dataPointsNumber, int k, Point point){ // metodo para classificação de um ponto desconhecido p

        RecursiveAction recursiveActionDist = new RecursiveAction() {

            final ArrayList<Point> newDistances = new ArrayList<>();

            @Override
            protected void compute() {
               int SEQUENTIAL_THRESHOLD = arr.length;

               if(newDistances.size() == SEQUENTIAL_THRESHOLD){ // caso base
                    Point[] points = computeDistDirectly();
               }
               else {
                   int mid = arr.length/2;

                   RecursiveAction firstSubTask = new RecursiveAction();



            }
        };







        // e possivel utilizar o dividir pra conquistar aqui
        for(int i=0;i<dataPointsNumber;i++){
            arr[i].distance = Math.sqrt((arr[i].x - point.x) * (arr[i].x - point.x) + (arr[i].y - point.y) * (arr[i].y - point.y)); // calculo distância euclidiana
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

        // tambem da pra fazer dividir pra conquistar
        double media = 0;
        for(int i=0;i<k;i++){
            media += arr[i].val; // media dos precos
        }

        media = media / k;
        System.out.println(media);

        return media;

    }


}
