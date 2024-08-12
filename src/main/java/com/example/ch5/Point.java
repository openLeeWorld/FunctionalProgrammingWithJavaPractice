package com.example.ch5;

import java.io.Serializable;

public record Point(int x, int y) implements Serializable {

    // wither 메서드로 변형된 복사본 생성
    public Point withX(int newX) {
        return new Point(newX, y());
    }

    public Point withY(int newY) {
        return new Point(x(), newY);
    }
    
    // 중첩 레코드로 원래 레코드에서 변경 로직을 깔끔하게 분리
    public With with() {
        return new With(this);
    }

    public record With(Point source) {

        public Point x(int x) {
            return new Point(x, source.y());
        }

        public Point y(int y) {
            return new Point(source.x(), y);
        }
    }
    
    // 빌더 패턴으로 복제 셍성자를 도입하여 변경 관리
    public static final class Builder {
        private int x;
        private int y;

        public Builder(Point point) {
            this.x = point.x();
            this.y = point.y();
        }

        public Builder x(int x) {
            this.x = x;
            return this;
        }

        public Builder y(int y) {
            this.y = y;
            return this;
        }

        public Point build() {
            return new Point(this.x, this.y);
        }
    }
}
