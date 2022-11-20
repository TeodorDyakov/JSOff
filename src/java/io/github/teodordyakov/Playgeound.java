package io.github.teodordyakov;

import java.util.stream.IntStream;

public class Playgeound {
    public static void main(String[] args) {
        IntStream.rangeClosed(2, 100)
                .filter(x -> IntStream.range(2, x)
                        .noneMatch(a -> x% a == 0))
                .forEach(i -> System.out.println(i));

    }

    //js quadtree
    // gravity simulator - press and hold to create planet
}
