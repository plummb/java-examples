package org.swat.reactivex;

import io.reactivex.Flowable;
import io.reactivex.Observable;

/**
 * Created by swat
 * on 13/6/17.
 */
public class Example {
    public static void main(String[] args) {
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
                .subscribe(System.out::println);
    }
}
