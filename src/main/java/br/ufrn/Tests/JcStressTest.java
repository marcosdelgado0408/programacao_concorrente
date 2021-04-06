package br.ufrn.Tests;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

import br.ufrn.Point;
import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.III_Result;




@JCStressTest
@State
@Outcome(id = "1, 0, 0", expect = Expect.ACCEPTABLE, desc = "get back 1-0-0")
@Outcome(id = "0, 1, 0", expect = Expect.ACCEPTABLE, desc = "get back 0-1-0")
@Outcome(id = "0, 0, 1", expect = Expect.ACCEPTABLE, desc = "get back 0-0-1")
public class JcStressTest {

    private final Point[] pointsArr;
    private final Point p = (new Point(2, 7.45));
    private int k = 3;


    public JcStressTest(){
        List<Point> points = new ArrayList<>(); // adding dataset

//        try {
//            String currentLine;
//            BufferedReader bufferedReader = new BufferedReader(new FileReader("yellow_tripdata_2016-01.csv"));
//
//            bufferedReader.readLine(); // pulando primeira linha
//
//            while( (currentLine = bufferedReader.readLine() ) != null){
//                String[] splitedLine = currentLine.split(",");
//                points.add(new Point( Double.parseDouble(splitedLine[3]), Double.parseDouble(splitedLine[4]), Double.parseDouble(splitedLine[12]) )); // x = passanger_count / y = trip_distance / val = fare_amount
//            }
//
//
//        }
//        catch (Exception e){
//            e.printStackTrace();
//        }

        Random random = new Random();

        for(int i=0;i<5;i++){
            points.add(new Point(random.nextDouble(), random.nextDouble(), random.nextDouble()));
        }

        pointsArr = points.toArray(new Point[0]);

    }





    public void getDistance(){

        for(int i=0;i<this.pointsArr.length;i++){
            pointsArr[i].distance = Math.sqrt((pointsArr[i].x - p.x) * (pointsArr[i].x - p.x) + (pointsArr[i].y - p.y) * (pointsArr[i].y - p.y)); // calculo distância euclidiana
        }

    }

    public void sortPointArray(){

        Arrays.sort(pointsArr, (o1, o2) -> Double.compare(o1.distance, o2.distance));

    }

    public void classifyAverage(){

        double media = 0;
        for(int i=0;i<k;i++){
            media += pointsArr[i].val; // media dos precos da fare
        }

        media = media / k;
        System.out.println(media);

    }




    @Actor
    public void actor1(III_Result r){
        r.r1 = 1;
        r.r2 = 0;
        r.r3 = 0;
        this.getDistance();

    }

    @Actor
    public void actor2(III_Result r){
        r.r1 = 0;
        r.r2 = 1;
        r.r3 = 0;
        this.sortPointArray();

    }


    @Actor
    public void actor3(III_Result r){
        r.r1 = 0;
        r.r2 = 0;
        r.r3 = 1;
        this.classifyAverage();
    }









}