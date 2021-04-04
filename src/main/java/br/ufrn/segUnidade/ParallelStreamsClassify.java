package br.ufrn.segUnidade;

import br.ufrn.Point;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ParallelStreamsClassify {


    public double classifyPoint(List<Point> arr, int dataPointsNumber, int k, Point point){ // metodo para classificação de um ponto desconhecido p

        // Distância euclidiana
        arr.parallelStream()
        .forEach(p -> {
           p.setDistance(Math.sqrt((p.x - point.x) * (p.x - point.x) + (p.y - point.y) * (p.y - point.y)));
        });

        // Ordenação
        double media = arr.parallelStream()
            .sorted(new Comparator<Point>() {
                @Override
                public int compare(Point o1, Point o2) {
                    return Double.compare(o1.distance, o2.distance);
                }
            })
            .collect(Collectors.toList())

            //Média
            .subList(0, k)
            .stream().mapToDouble(p -> p.val)
            .average()
            .getAsDouble();

        return media;

    }

}
