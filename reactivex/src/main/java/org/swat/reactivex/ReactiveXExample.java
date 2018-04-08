package org.swat.reactivex;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by swat
 * on 13/6/17.
 */
public class ReactiveXExample {
    public static void main(String[] args) throws InterruptedException {
        Flowable.just("Hello world").subscribe(System.out::println);
        String[] words = {
                "the",
                "quick",
                "brown",
                "fox",
                "jumped",
                "over",
                "the",
                "lazy",
                "dog"
        };
        Observable.fromArray(words)
                .subscribe(System.out::println);

        Observable.fromArray(words)
                .zipWith(Observable.range(1, Integer.MAX_VALUE),
                        (string, count) -> String.format("%2d. %s", count, string))
                .observeOn(Schedulers.newThread())
                .subscribe(s -> {
                    Thread.sleep(1);
                    System.out.println(s);
                });
        System.out.println("Hello");
        Thread.sleep(1000);
    }
}
