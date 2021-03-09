package br.ufrn.Tests;

import br.ufrn.Point;
import org.openjdk.jmh.annotations.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class JmhTest {

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @Fork(value = 1)
    @Warmup(iterations = 2)
    @Measurement(iterations = 1)

    public void init() {

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

        // Insercao na variavele e criacao de um Point
        Point[] arr = points.toArray(Point[]::new);

        Point p = new Point(); // o point neste caso sera o ponto desconhecido a ser determinado seu grupo a partir do Knn
        p.x = 2;
        p.y = 7.45;

        int k = 3;
        int dataPointsNumber = arr.length;


        Thread threadDistEucl = new Thread( ()-> {
            //Distancia Euclidiana
            synchronized (this) {
                for (int i = 0; i < dataPointsNumber; i++) {
                    arr[i].distance = Math.sqrt((arr[i].x - p.x) * (arr[i].x - p.x) + (arr[i].y - p.y) * (arr[i].y - p.y)); // calculo distância euclidiana
                }
            }

        });


        Thread comparator = new Thread( ()-> {
            //Ordenacao
            synchronized (this) {
                Arrays.sort(arr);
            }

        });



        Thread fazerMedia = new Thread( ()->{
            //Media
            synchronized (this) {
                double media = 0;
                for (int i = 0; i < k; i++) {
                    media += arr[i].val; // media dos precos da fare
                }

                media = media / k;
                System.out.println(media);
            }
        });

        threadDistEucl.start();
        comparator.start();
        fazerMedia.start();


    }



}