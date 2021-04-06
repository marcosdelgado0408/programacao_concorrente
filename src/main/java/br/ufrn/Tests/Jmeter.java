//package br.ufrn.Tests;
//
//import br.ufrn.Point;
//import br.ufrn.SerialClassify;
//import org.apache.jmeter.config.Arguments;
//import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
//import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
//import org.apache.jmeter.samplers.SampleResult;
//
//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.io.Serializable;
//import java.util.ArrayList;
//import java.util.Random;
//
//
///*
// * point -> p.x = 2 | p.y = 7.45
// * k -> 3
// *  List de Point
// *  resposta = 25.666666666666668
// *
// */
//
//public class Jmeter extends AbstractJavaSamplerClient implements Serializable{
//
//    @Override
//    public Arguments getDefaultParameters() {
//        Arguments defaultParameters = new Arguments();
//        defaultParameters.addArgument("k","3"); // vai adicionar a "var1" -> valor 1
//        defaultParameters.addArgument("p.x", "2");
//        defaultParameters.addArgument("p.y", "7.45");
//
//        return defaultParameters;
//    }
//
//    @Override
//    public SampleResult runTest(JavaSamplerContext javaSamplerContext) {
//
//        ArrayList<Point> points = new ArrayList<>(); // adding dataset
//
//        try {
//            String currentLine;
//            BufferedReader bufferedReader = new BufferedReader(new FileReader("/home/marcos/Documentos/prog_concorrente/programacao_concorrente/yellow_tripdata_2016-01.csv"));
//
//            bufferedReader.readLine(); // pulando primeira linha
//
//            while( (currentLine = bufferedReader.readLine() ) != null){
//                String[] splitedLine = currentLine.split(",");
//                points.add(new Point( Double.parseDouble(splitedLine[3]), Double.parseDouble(splitedLine[4]), Double.parseDouble(splitedLine[12]) )); // x = passanger_count / y = trip_distance / val = fare_amount
//            }
//
//        }
//        catch (Exception e){
//            e.printStackTrace();
//        }
//
//        Point[] arr = points.toArray(new Point[0]);
//
//
//
//
//
//        String k = javaSamplerContext.getParameter("k");
//        String px = javaSamplerContext.getParameter("p.x");
//        String py = javaSamplerContext.getParameter("p.y");
//
//        Point p = new Point(Double.parseDouble(px), Double.parseDouble(py));
//
//        SampleResult result = new SampleResult();
//        result.sampleStart();
//        result.setSampleLabel("Test Sample");
//
//
//        SerialClassify serialClassify = new SerialClassify();
//        double val = serialClassify.classifyPoint(arr, arr.length, Integer.parseInt(k), p);
//
//
//        if(val == 25.666666666666668) {
//            result.sampleEnd();
//            result.setResponseCode("200");
//            result.setResponseMessage("OK - val: " + val);
//            result.setSuccessful(true);
//        }
//        else {
//            result.sampleEnd();
//            result.setResponseCode("500");
//            result.setResponseMessage("NOK - val: " + val);
//            result.setSuccessful(false);
//        }
//
//
//        return result;
//    }
//
//
//
//
//
//}
