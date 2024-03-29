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
import br.ufrn.Tests.JcStressTest;
import org.openjdk.jcstress.infra.results.III_Result_jcstress;

public class JcStressTest_jcstress extends Runner<III_Result_jcstress> {

    volatile StateHolder<JcStressTest, III_Result_jcstress> version;

    public JcStressTest_jcstress(TestConfig config, TestResultCollector collector, ExecutorService pool) {
        super(config, collector, pool, "br.ufrn.Tests.JcStressTest");
    }

    @Override
    public Counter<III_Result_jcstress> sanityCheck() throws Throwable {
        Counter<III_Result_jcstress> counter = new Counter<>();
        sanityCheck_API(counter);
        sanityCheck_Footprints(counter);
        return counter;
    }

    private void sanityCheck_API(Counter<III_Result_jcstress> counter) throws Throwable {
        final JcStressTest s = new JcStressTest();
        final III_Result_jcstress r = new III_Result_jcstress();
        Collection<Future<?>> res = new ArrayList<>();
        res.add(pool.submit(() -> s.actor1(r)));
        res.add(pool.submit(() -> s.actor2(r)));
        res.add(pool.submit(() -> s.actor3(r)));
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
            version = new StateHolder<>(new JcStressTest[size], new III_Result_jcstress[size], 3, config.spinLoopStyle);
            for (int c = 0; c < size; c++) {
                III_Result_jcstress r = new III_Result_jcstress();
                JcStressTest s = new JcStressTest();
                version.rs[c] = r;
                version.ss[c] = s;
                s.actor1(r);
                s.actor2(r);
                s.actor3(r);
                counter.record(r);
            }
        });
    }

    @Override
    public Counter<III_Result_jcstress> internalRun() {
        version = new StateHolder<>(new JcStressTest[0], new III_Result_jcstress[0], 3, config.spinLoopStyle);

        control.isStopped = false;

        List<Callable<Counter<III_Result_jcstress>>> tasks = new ArrayList<>();
        tasks.add(this::actor1);
        tasks.add(this::actor2);
        tasks.add(this::actor3);
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

    public final void jcstress_consume(StateHolder<JcStressTest, III_Result_jcstress> holder, Counter<III_Result_jcstress> cnt, int a, int actors) {
        JcStressTest[] ss = holder.ss;
        III_Result_jcstress[] rs = holder.rs;
        int len = ss.length;
        int left = a * len / actors;
        int right = (a + 1) * len / actors;
        for (int c = left; c < right; c++) {
            III_Result_jcstress r = rs[c];
            JcStressTest s = ss[c];
            ss[c] = new JcStressTest();
            cnt.record(r);
            r.r1 = 0;
            r.r2 = 0;
            r.r3 = 0;
        }
    }

    public final void jcstress_updateHolder(StateHolder<JcStressTest, III_Result_jcstress> holder) {
        if (!holder.tryStartUpdate()) return;
        JcStressTest[] ss = holder.ss;
        III_Result_jcstress[] rs = holder.rs;
        int len = ss.length;

        int newLen = holder.updateStride ? Math.max(config.minStride, Math.min(len * 2, config.maxStride)) : len;

        JcStressTest[] newS = ss;
        III_Result_jcstress[] newR = rs;
        if (newLen > len) {
            newS = Arrays.copyOf(ss, newLen);
            newR = Arrays.copyOf(rs, newLen);
            for (int c = len; c < newLen; c++) {
                newR[c] = new III_Result_jcstress();
                newS[c] = new JcStressTest();
            }
         }

        version = new StateHolder<>(control.isStopped, newS, newR, 3, config.spinLoopStyle);
        holder.finishUpdate();
   }

    public final Counter<III_Result_jcstress> actor1() {

        Counter<III_Result_jcstress> counter = new Counter<>();
        while (true) {
            StateHolder<JcStressTest,III_Result_jcstress> holder = version;
            if (holder.stopped) {
                return counter;
            }

            JcStressTest[] ss = holder.ss;
            III_Result_jcstress[] rs = holder.rs;
            int size = ss.length;

            holder.preRun();

            for (int c = 0; c < size; c++) {
                JcStressTest s = ss[c];
                III_Result_jcstress r = rs[c];
                r.trap = 0;
                s.actor1(r);
            }

            holder.postRun();

            jcstress_consume(holder, counter, 0, 3);
            jcstress_updateHolder(holder);

            holder.postUpdate();
        }
    }

    public final Counter<III_Result_jcstress> actor2() {

        Counter<III_Result_jcstress> counter = new Counter<>();
        while (true) {
            StateHolder<JcStressTest,III_Result_jcstress> holder = version;
            if (holder.stopped) {
                return counter;
            }

            JcStressTest[] ss = holder.ss;
            III_Result_jcstress[] rs = holder.rs;
            int size = ss.length;

            holder.preRun();

            for (int c = 0; c < size; c++) {
                JcStressTest s = ss[c];
                III_Result_jcstress r = rs[c];
                r.trap = 0;
                s.actor2(r);
            }

            holder.postRun();

            jcstress_consume(holder, counter, 1, 3);
            jcstress_updateHolder(holder);

            holder.postUpdate();
        }
    }

    public final Counter<III_Result_jcstress> actor3() {

        Counter<III_Result_jcstress> counter = new Counter<>();
        while (true) {
            StateHolder<JcStressTest,III_Result_jcstress> holder = version;
            if (holder.stopped) {
                return counter;
            }

            JcStressTest[] ss = holder.ss;
            III_Result_jcstress[] rs = holder.rs;
            int size = ss.length;

            holder.preRun();

            for (int c = 0; c < size; c++) {
                JcStressTest s = ss[c];
                III_Result_jcstress r = rs[c];
                r.trap = 0;
                s.actor3(r);
            }

            holder.postRun();

            jcstress_consume(holder, counter, 2, 3);
            jcstress_updateHolder(holder);

            holder.postUpdate();
        }
    }

}
