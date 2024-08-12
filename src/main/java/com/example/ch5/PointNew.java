package com.example.ch5;

import io.soabase.recordbuilder.core.RecordBuilder;

@RecordBuilder
public record PointNew(int x, int y) implements PointNewBuilder.With{
    // 바디 생략
}
// @RecordBuilder가 자동으로 PointNewBuilder 생성
// PointNewBuilder.With을 구현함으로써 wither 메서드도 사용 가능
// annotation preocessor가 빌드 도구에 결합됨으로써 종속성 강해지므로 주의
// intellij에서 annotation processor enable하고, build.gradle에서 gradle
// 새로고침 후에 프로젝트를 다시 빌드해야 인식됨

