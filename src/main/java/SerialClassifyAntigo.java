import java.util.Arrays;
import java.util.Comparator;
import br.ufrn.Point;

public class SerialClassifyAntigo{


    public Point[] getDistance(Point point, Point[] arr, int dataPointsNumber){

        for(int i=0;i<dataPointsNumber;i++){
            arr[i].distance = Math.sqrt((arr[i].x - point.x) * (arr[i].x - point.x) + (arr[i].y - point.y) * (arr[i].y - point.y)); // calculo distância euclidiana
        }

        return arr;
    }



    public Point[] sortPointArray(Point[] arr){

        Arrays.sort(arr, new  Comparator<>() { // ordenar o array por "distance" -> crescente
            @Override
            public int compare(Point o1, Point o2) {
                return Double.compare(o1.distance, o2.distance);
            }
        });

        return arr;
    }

    public double classifyAverage(int k, Point[] arr){

        /*
         * irá pesquisar os valores até uma distância determinada k do ponto desconhecido p
         * */


        double media = 0;
        for(int i=0;i<k;i++){
            media += arr[i].val; // media dos precos da fare
        }

        media = media / k;
        System.out.println(media);

        return media;
    }


    public double classifyPoint(Point[] arr, int dataPointsNumber, int k, Point point){ // metodo para classificação de um ponto desconhecido p
        Point[] pointsArr;


        pointsArr = getDistance(point, arr, dataPointsNumber);
        pointsArr = sortPointArray(pointsArr);


        return classifyAverage(k, pointsArr);

    }

}