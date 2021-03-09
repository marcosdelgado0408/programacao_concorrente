package br.ufrn;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) {

        ArrayList<Point> points = new ArrayList<>(); // adding dataset

        try {
            File file = new File("yellow_tripdata_2016-01.csv");
            Scanner scanner = new Scanner(file);

            scanner.nextLine(); // pulando primeira linha

            while(scanner.hasNextLine()){
                String[] line = scanner.nextLine().split(",");
                points.add(new Point( Double.parseDouble(line[3]), Double.parseDouble(line[4]), Double.parseDouble(line[12]) )); // x = passanger_count / y = trip_distance / val = fare_amount
            }
        }
        catch (FileNotFoundException e) {
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


        System.out.println("The value classified to unknown point is " + serialClassify.classifyPoint(arr, arr.length, k, p));


    }

}
