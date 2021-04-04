package br.ufrn.segUnidade.ForkJoin;

import br.ufrn.Point;

import java.util.List;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

public class RecursiveMedia extends RecursiveTask<Double> {
    private final List<Point> data;
    private final int k;

    public RecursiveMedia(List<Point> data, int k) {
        this.data = data;
        this.k = k;
    }

    private double computeSumDirectly() {
        double sum = 0;
        for (Point p: data) {
            sum += p.val;
        }
        return sum;
    }


    //so fazer igual ao do professor e divide no final so

    @Override
    protected Double compute() {

        System.out.println(data);

        if(data.size() <= k){  // caso base
            double media = computeSumDirectly();
            media = media/k;

            System.out.println("Media: " + media);
            return media;
        }
        else {
            int mid = k/2;
            RecursiveMedia firstSubtask = new RecursiveMedia(data.subList(0, mid), k);
            RecursiveMedia secondSubtask = new RecursiveMedia(data.subList(mid, data.size()) , k);
            firstSubtask.fork(); // queue the first task

            return secondSubtask.compute() + firstSubtask.join();
        }

    }



}
