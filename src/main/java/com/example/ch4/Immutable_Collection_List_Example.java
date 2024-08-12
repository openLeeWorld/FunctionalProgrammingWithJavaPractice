package com.example.ch4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Immutable_Collection_List_Example {
    public static void main(String[] args) {
        List<String> original = new ArrayList<>();
        original.add("blue");
        original.add("red");

        List<String> unmodifiable = Collections.unmodifiableList(original);
        //unmodifiable.clear(); //UnsupportedOperationException 발생

        original.add("green");
        // 원본 참조를 통해 데이터를 여전히 수정할 수 있음
        // unmodifiable은 변경되지 않는 뷰에 불과할 뿐
        System.out.println(unmodifiable.size()); // 출력: 3
        
        // 복사본 생성
        List<String> copiedList = List.copyOf(original);
        
        // 원본 리스트에 대한 새 항목 추가
        original.add("green");
        
        // 내용 확인
        System.out.println(original);
        // [blue, red, green]
        System.out.println(copiedList);
        // [blue, red]
        // 복사된 컬렉션은 원본 리스트에 요소를 추가하거나 제거가 안되지만, 
        // 실제 요소 자체는 여전히 공유되며 변경 가능


    }
}
