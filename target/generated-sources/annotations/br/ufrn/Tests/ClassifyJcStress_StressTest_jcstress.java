package br.ufrn.Tests;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.openjdk.jcstress.infra.runners.TestConfig;
import org.openjdk.jcstress.infra.collectors.TestResultCollector;
import org.openjdk.jcstress.infra.runners.Runner;
import org.openjdk.jcstress.infra.runners.StateHolder;
import org.openjdk.jcstress.util.Counter;
import org.openjdk.jcstress.vm.WhiteBoxSupport;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Callable;
import java.util.Collections;
import java.util.List;
import br.ufrn.Tests.ClassifyJcStress.StressTest;
import org.openjdk.jcstress.infra.results.III_Result_jcstress;
import br.ufrn.Tests.ClassifyJcStress_MyState_jcstress;

public class ClassifyJcStress_StressTest_jcstress extends Runner<III_Result_jcstress> {

    StressTest test;
    volatile StateHolder<ClassifyJcStress_MyState_jcstress, III_Result_jcstress> version;

    public ClassifyJcStress_StressTest_jcstress(TestConfig config, TestResultCollector collector, ExecutorService pool) {
        super(config, collector, pool, "br.ufrn.Tests.ClassifyJcStress.StressTest");
    }

    @Override
    public Counter<III_Result_jcstress> sanityCheck() throws Throwable {
        Counter<III_Result_jcstress> counter = new Counter<>();
        sanityCheck_API(counter);
        sanityCheck_Footprints(counter);
        return counter;
    }

    private void sanityCheck_API(Counter<III_Result_jcstress> counter) throws Throwable {
        final ClassifyJcStress_MyState_jcstress s = new ClassifyJcStress_MyState_jcstress();
        final III_Result_jcstress r = new III_Result_jcstress();
        final StressTest t = new StressTest();
        Collection<Future<?>> res = new ArrayList<>();
        res.add(pool.submit(() -> t.setConfig(s, r)));
        for (Future<?> f : res) {
            try {
                f.get();
            } catch (ExecutionException e) {
                throw e.getCause();
            }
        }
        counter.record(r);
    }

    private void sanityCheck_Footprints(Counter<III_Result_jcstress> counter) throws Throwable {
        config.adjustStrides(size -> {
            version = new StateHolder<>(new ClassifyJcStress_MyState_jcstress[size], new III_Result_jcstress[size], 1, config.spinLoopStyle);
            final StressTest t = new StressTest();
            for (int c = 0; c < size; c++) {
                III_Result_jcstress r = new III_Result_jcstress();
                ClassifyJcStress_MyState_jcstress s = new ClassifyJcStress_MyState_jcstress();
                version.rs[c] = r;
                version.ss[c] = s;
                t.setConfig(s, r);
                counter.record(r);
            }
        });
    }

    @Override
    public Counter<III_Result_jcstress> internalRun() {
        test = new StressTest();
        version = new StateHolder<>(new ClassifyJcStress_MyState_jcstress[0], new III_Result_jcstress[0], 1, config.spinLoopStyle);

        control.isStopped = false;

        List<Callable<Counter<III_Result_jcstress>>> tasks = new ArrayList<>();
        tasks.add(this::setConfig);
        Collections.shuffle(tasks);

        Collection<Future<Counter<III_Result_jcstress>>> results = new ArrayList<>();
        for (Callable<Counter<III_Result_jcstress>> task : tasks) {
            results.add(pool.submit(task));
        }

        try {
            TimeUnit.MILLISECONDS.sleep(config.time);
        } catch (InterruptedException e) {
        }

        control.isStopped = true;

        waitFor(results);

        Counter<III_Result_jcstress> counter = new Counter<>();
        for (Future<Counter<III_Result_jcstress>> f : results) {
            try {
                counter.merge(f.get());
            } catch (Throwable e) {
                throw new IllegalStateException(e);
            }
        }
        return counter;
    }

    public final void jcstress_consume(StateHolder<ClassifyJcStress_MyState_jcstress, III_Result_jcstress> holder, Counter<III_Result_jcstress> cnt, int a, int actors) {
        ClassifyJcStress_MyState_jcstress[] ss = holder.ss;
        III_Result_jcstress[] rs = holder.rs;
        int len = ss.length;
        int left = a * len / actors;
        int right = (a + 1) * len / actors;
        for (int c = left; c < right; c++) {
            III_Result_jcstress r = rs[c];
            ClassifyJcStress_MyState_jcstress s = ss[c];
            ss[c] = new ClassifyJcStress_MyState_jcstress();
            cnt.record(r);
            r.r1 = 0;
            r.r2 = 0;
            r.r3 = 0;
        }
    }

    public final void jcstress_updateHolder(StateHolder<ClassifyJcStress_MyState_jcstress, III_Result_jcstress> holder) {
        if (!holder.tryStartUpdate()) return;
        ClassifyJcStress_MyState_jcstress[] ss = holder.ss;
        III_Result_jcstress[] rs = holder.rs;
        int len = ss.length;

        int newLen = holder.updateStride ? Math.max(config.minStride, Math.min(len * 2, config.maxStride)) : len;

        ClassifyJcStress_MyState_jcstress[] newS = ss;
        III_Result_jcstress[] newR = rs;
        if (newLen > len) {
            newS = Arrays.copyOf(ss, newLen);
            newR = Arrays.copyOf(rs, newLen);
            for (int c = len; c < newLen; c++) {
                newR[c] = new III_Result_jcstress();
                newS[c] = new ClassifyJcStress_MyState_jcstress();
            }
         }

        version = new StateHolder<>(control.isStopped, newS, newR, 1, config.spinLoopStyle);
        holder.finishUpdate();
   }

    public final Counter<III_Result_jcstress> setConfig() {

        StressTest lt = test;
        Counter<III_Result_jcstress> counter = new Counter<>();
        while (true) {
            StateHolder<ClassifyJcStress_MyState_jcstress,III_Result_jcstress> holder = version;
            if (holder.stopped) {
                return counter;
            }

            ClassifyJcStress_MyState_jcstress[] ss = holder.ss;
            III_Result_jcstress[] rs = holder.rs;
            int size = ss.length;

            holder.preRun();

            for (int c = 0; c < size; c++) {
                ClassifyJcStress_MyState_jcstress s = ss[c];
                III_Result_jcstress r = rs[c];
                r.trap = 0;
                s.trap = 0;
                lt.setConfig(s, r);
            }

            holder.postRun();

            jcstress_consume(holder, counter, 0, 1);
            jcstress_updateHolder(holder);

            holder.postUpdate();
        }
    }

}
