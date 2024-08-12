package com.example.ch6;

import java.util.List;
import java.util.stream.Stream;

public class Stream_Example {
    public static void main(String[] args) {
        Book book1 = new Book("Harry Poter", 1986, Genre.MOVIE);
        Book book2 = new Book("Space odissey", 1969, Genre.SCIENCE_FICTION);
        Book book3 = new Book("Where we are", 1999, Genre.NOVEL);
        Book book4 = new Book("As you wish", 2011, Genre.POEM);
        Book book5 = new Book("Founded", 2011, Genre.LITERATURE);
        List<Book> books = List.of(book1, book2, book3, book4, book5
        );

        List<String> result =
                books.stream()
                        .filter(book -> book.year() > 1970)
                        .filter(book -> book.genre() != Genre.LITERATURE)
                        .map(Book::title)
                        .sorted()
                        .limit(3L)
                        .toList(); // .collect(Collectors.toList());

        System.out.println(result);

        // Shape stream
        List<Shape> filter_shape =
            Stream.of(Shape.square(), Shape.triangle(), Shape.circle())
                    .map(Shape::twice)
                    .flatMap(List::stream)
                    //.peek(shape -> System.out.println("current: " + shape))
                    .filter(shape -> shape.corners() < 4)
                    .toList();
        
        // reduce 메서드 비교 (초기값, accumulator, combiner)
        var reduceOnly = Stream.of("apple", "orange", "banana")
                .reduce(0,
                        (acc, str) -> acc + str.length(),

                        Integer::sum);
        System.out.println(reduceOnly); // 17

        var mapReduce = Stream.of("apple", "orange", "banana")
                .mapToInt(String::length)
                .reduce(0, (acc, length) -> acc + length);
        System.out.println(mapReduce);
    }
}
