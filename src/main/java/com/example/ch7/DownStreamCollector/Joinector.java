package com.example.ch7.DownStreamCollector;

import java.util.Collections;
import java.util.Set;
import java.util.StringJoiner;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

// 사용자 정의 컬렉터
// Collectors.joining(CharSequence delimiter)를 재구현
public class Joinector implements Collector<CharSequence, // T
                                            StringJoiner, // A
                                            String> { // R
    private final CharSequence delimiter;

    public Joinector(CharSequence delimiter) {
        this.delimiter = delimiter;
    }

    @Override
    public Supplier<StringJoiner> supplier() {
        return () -> new StringJoiner(this.delimiter);
    } // StringJoiner 타입은 공개 API와 구분자로 인해 가변 결과 컨테이너

    @Override
    public BiConsumer<StringJoiner, CharSequence> accumulator() {
        return StringJoiner::add;
    } // 컨테이너에 새 요소 추가

    @Override
    public BinaryOperator<StringJoiner> combiner() {
        return StringJoiner::merge;
    } // 여러 컨테이너를 병합하기 위한 로직은 메서드 참조로

    @Override
    public Function<StringJoiner, String> finisher() {
        return StringJoiner::toString;
    } // 결과 컨테이너를 실제 결과값으로 변환

    @Override
    public Set<Characteristics> characteristics() {
        return Collections.emptySet();
    } // 사용 가능한 컬렉터 특성 중 어떠한 것도 포함 안함
}

/*
// 위는 다음과 동일
Collector<CharSequence, StringJoiner, String> joinector =
        Collector.of(() -> new StringJoiner(delimiter),
                StringJoiner::add,
                StringJoiner::merge,
                StringJoiner::toString);
*/