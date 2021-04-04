package br.ufrn.segUnidade.ForkJoin;

import br.ufrn.Point;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;


public class RecursiveDist extends RecursiveTask<List<Point>> {
    private final List<Point> data;
    private final int SEQUENTIAL_THRESHOLD;
    private final Point point;

    public RecursiveDist(List<Point> arr, Point point, int SEQUENTIAL_THRESHOLD) {
        this.data = arr;
        this.point = point;
        this.SEQUENTIAL_THRESHOLD = SEQUENTIAL_THRESHOLD;
    }

    private List<Point> computeDistDirectly(){
        List<Point> points = this.data;

        for(Point p : points){
            p.distance = Math.sqrt((p.x - point.x) * (p.x - point.x) + (p.y - point.y) * (p.y - point.y));
        }

        return points;
    }


    @Override
    protected List<Point> compute() {

        System.out.println(SEQUENTIAL_THRESHOLD);

        if(data.size() <= SEQUENTIAL_THRESHOLD){ // caso base
            List<Point> points = computeDistDirectly();
//            System.out.println(points);
            return points;
        }
        else {
            int mid = data.size()/2;

            RecursiveDist firstSubTask = new RecursiveDist(data.subList(0, mid), point, SEQUENTIAL_THRESHOLD);
            RecursiveDist secondSubTask = new RecursiveDist(data.subList(mid, data.size()), point, SEQUENTIAL_THRESHOLD);

            firstSubTask.fork();

            // como o addAll retorna valores booleanos, tive que criar uma nova List<Point>
            List<Point> retorno = new ArrayList<>();
            retorno.addAll(secondSubTask.compute());
            retorno.addAll(firstSubTask.join());


            return retorno;

        }


    }





}
