package org.swat.reactivex;


import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Create some tasks which can run in parallel threads. Then join to the main thread.
 */
public class ParallelJobs {
    public static void main(String[] args) {
        List<Disposable> disposables = new ArrayList<>();
        AtomicInteger failures = new AtomicInteger(0);
        for (int x = 0; x < 10; x++) {
            Disposable disposable = Observable.fromArray(x)
                    .observeOn(Schedulers.newThread())
                    .doOnError(s -> {
                        failures.incrementAndGet();
                    })
                    .subscribe(s -> {
                        try {
                            System.out.println("Started thread #" + s);
                            Thread.sleep(1000);
                            System.out.println("Completed thread #" + s);
                        } catch (InterruptedException e) {
                        }
                    });
            disposables.add(disposable);
        }

        try {
            while (true) {
                Thread.sleep(10);
                boolean disposed = true;
                for (Disposable disposable : disposables) {
                    disposed = disposed && disposable.isDisposed();
                }
                if (disposed) {
                    break;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("All completed with " + failures + " failures");
    }
}
