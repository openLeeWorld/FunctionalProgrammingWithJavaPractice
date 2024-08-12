package com.example.ch5;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class Record_Album_pers_year {

    public static void main(String[] args) {
        Map<Integer, List<String>> albums =
                Map.of(1990, List.of("Bossanova", "Listen Without Prejudice"),
                        1991, List.of("Nevermind", "Ten", "Blue Lines"),
                        1992, List.of("November", "Ten no", "Red Lines"));

        var filterd_albums = filterAlbums(albums, 1991);
        System.out.println(filterd_albums);
    }

    public static List<String> filterAlbums(Map<Integer, List<String>> albums,
                                            int minimumYear) {

        // 로컬 레코드로 메서드 내부에서 선언되어 스코프 외부 노출 x
        record AlbumsPerYear(int year, List<String> titles) {

            public AlbumsPerYear(Map.Entry<Integer, List<String>> entry) {
                this(entry.getKey(), entry.getValue());
            } // 추가 생성자로 새로운 인스턴스 생성을 위해 메서드 참조를 사용 가능

            public static Predicate<AlbumsPerYear> minimumYear(int year) {
                return albumsPerYear -> albumsPerYear.year() >= year;
            } // 특정 작업이 스코프 밖의 변수(year)에 의존하는 경우 정적 헬퍼로 정의

            public static Comparator<AlbumsPerYear> sortByYear() {
                return Comparator.comparing(AlbumsPerYear::year);
            } // 데이터 정렬을 위해 Comparator를 반환하는 정적 헬퍼 메서드를 생성
        }
        
        return albums.entrySet() // Map 요소에 대해 반복 처리
                .stream() // 스트림 라인 
                .map(AlbumsPerYear::new) // Map.Entry 인스턴스를 로컬 레코드 타입으로 변환
                .filter(AlbumsPerYear.minimumYear(minimumYear)) // 필터링
                .sorted(AlbumsPerYear.sortByYear()) // 낮은 연도 순서대로 sort
                .map(AlbumsPerYear::titles) // 각 List<String> 요소를 뽑아옴(record getTitles 비슷한 것)
                .flatMap(List::stream) // 모든 요소를 하나의 List로 만듬 
                .toList(); // 실제로 변환

    }
}

// Map을 반복처리하려면 entrySet 메서드를 사용

/* // 원래 필터 버전
public List<String> filterAlbums(Map<Integer, List<String>> albums, int minimunYear) {
    return albums.entrySet()
                .stream()
                .filter(entry -> entry.getKey() >= minimumYear)
                .sorted(Comparator.comparing(Map.Entry::getKey))
                .map(Map.Entry::getValue) // 항목을 실제 값으로 반환
                .flatMap(List::stream) // List<String> 요소 평탄화
                .toList();
}
 */
