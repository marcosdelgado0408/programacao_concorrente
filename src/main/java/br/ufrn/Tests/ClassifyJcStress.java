package br.ufrn.Tests;

import br.ufrn.Counter;
import br.ufrn.Point;
import org.openjdk.jcstress.annotations.Actor;
import org.openjdk.jcstress.annotations.Expect;
import org.openjdk.jcstress.annotations.JCStressTest;
import org.openjdk.jcstress.annotations.Outcome;
import org.openjdk.jcstress.annotations.State;
import org.openjdk.jcstress.infra.results.III_Result;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;




public class ClassifyJcStress {


    @State
    public static class MyState extends Counter{}


    @JCStressTest
    @Outcome(id = "1, 0, 0", expect = Expect.ACCEPTABLE, desc = "get back 0-0-1")
    @Outcome(id = "0, 1, 0", expect = Expect.ACCEPTABLE, desc = "get back 0-1-0")
    @Outcome(id = "0, 0, 1", expect = Expect.ACCEPTABLE, desc = "get back 0-0-1")

    public static class StressTest{
        @Actor
        public void setConfig(MyState myState, III_Result r){

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

            r.r1 = myState.getNext();
            //Distancia Euclidiana
            for(int i=0;i<dataPointsNumber;i++){
                arr[i].distance = Math.sqrt((arr[i].x - p.x) * (arr[i].x - p.x) + (arr[i].y - p.y) * (arr[i].y - p.y)); // calculo distância euclidiana
            }


            r.r2 = myState.getNext();
            //Ordenacao
            Arrays.sort(arr);


            r.r3 = myState.getNext();
            //Media
            double media = 0;
            for(int i=0;i<k;i++){
                media += arr[i].val; // media dos precos da fare
            }

            media = media / k;
            System.out.println(media);


        }




    }


}

