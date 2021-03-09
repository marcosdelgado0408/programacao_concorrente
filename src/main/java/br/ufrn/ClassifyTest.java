package br.ufrn;

import org.openjdk.jcstress.annotations.Actor;
import org.openjdk.jcstress.annotations.Expect;
import org.openjdk.jcstress.annotations.JCStressTest;
import org.openjdk.jcstress.annotations.Outcome;
import org.openjdk.jcstress.annotations.State;
import org.openjdk.jcstress.infra.results.III_Result;

@JCStressTest
@Outcome(id = "0, 0, 1", expect = Expect.ACCEPTABLE, desc = "get back 0-0-1")
@Outcome(id = "0, 1, 0", expect = Expect.ACCEPTABLE, desc = "get back 0-1-0")
@Outcome(id = "0, 0, 1", expect = Expect.ACCEPTABLE, desc = "get back 0-0-1")


@State
public class ClassifyTest extends Counter{

    Point p;
    Point[] arr;
    int dataPointsNumber;
    int k;

    public void setP(Point p) { this.p = p; }
    public void setArr(Point[] arr) { this.arr = arr; }
    public void setDataPointsNumber(int dataPointsNumber) { this.dataPointsNumber = dataPointsNumber; }
    public void setK(int k) { this.k = k; }

    SerialClassify serialClassify = new SerialClassify();


    @Actor
    void actor1(III_Result r){
        r.r1 = this.getNext();
        serialClassify.getDistance(p, arr, dataPointsNumber);
    }

    @Actor
    void actor2(III_Result r){
        r.r2 = this.getNext();
        serialClassify.sortPointArray(arr);
    }

    @Actor
    void actor3(III_Result r){
        r.r3 = this.getNext();
        serialClassify.classifyAverage(k, arr);
    }



}

