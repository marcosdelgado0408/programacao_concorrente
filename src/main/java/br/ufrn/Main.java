package br.ufrn;

import br.ufrn.primUnidade.AtomicClassify;
import br.ufrn.primUnidade.ConcurrentClassify;
import br.ufrn.primUnidade.SynchronizedClassify;
import br.ufrn.segUnidade.CallableFutureClassify;
import br.ufrn.segUnidade.ExecutorClassify;
import br.ufrn.segUnidade.ForkJoin.ForkJoinClassify;
import br.ufrn.segUnidade.ParallelStreamsClassify;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class Main {

    public static void main(String[] args) throws ExecutionException, InterruptedException {


        ArrayList<Point> points = new ArrayList<>(); // adding dataset

        try {
            String currentLine;
            BufferedReader bufferedReader = new BufferedReader(new FileReader("yellow_tripdata_2016-01.csv"));

            bufferedReader.readLine(); // pulando primeira linha

            while( (currentLine = bufferedReader.readLine() ) != null){
                String[] splitedLine = currentLine.split(",");
                points.add(new Point( Double.parseDouble(splitedLine[3]), Double.parseDouble(splitedLine[4]), Double.parseDouble(splitedLine[12]) )); // x = passanger_count / y = trip_distance / val = fare_amount
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }


        Point[] arr = points.toArray(Point[]::new);


        /*Testing Point*/
        Point p = new Point(); // o point neste caso sera o ponto desconhecido a ser determinado seu grupo a partir do Knn
        p.x = 2;
        p.y = 7.45;

        // Parameter to decide group of the testing point
        int k = 3;

        SerialClassify serialClassify = new SerialClassify();
        ConcurrentClassify concurrentClassify = new ConcurrentClassify();
        SynchronizedClassify synchronizedClassify = new SynchronizedClassify();
        AtomicClassify atomicClassify = new AtomicClassify();
        ExecutorClassify executorClassify = new ExecutorClassify();
        CallableFutureClassify callableFutureClassify = new CallableFutureClassify();

        // vamos utilizar o Points no forkJoin e no parallelStreamsClassify
        ForkJoinClassify forkJoinClassify = new ForkJoinClassify();
        ParallelStreamsClassify parallelStreamsClassify = new ParallelStreamsClassify();

        System.out.println("The value classified to unknown point is " + serialClassify.classifyPoint(arr, arr.length, k, p));

//        System.out.println("The value classified to unknown point is " + parallelStreamsClassify.classifyPoint(points, points.size(), k, p));

    }

}
