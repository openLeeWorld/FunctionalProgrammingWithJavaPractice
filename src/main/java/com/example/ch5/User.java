package com.example.ch5;

import java.time.LocalDateTime;
import java.util.Objects;

// record는 불변값 생성을 위해 property가 final 선언되있고, setter는 없음
// 불변 객체는 복사본으로 새 값을 적용 가능(메모리는 더 먹겠지만)
public record User(String username, boolean active, LocalDateTime lastlogin) {
    /* // 표준 생성자
 public User(String username, boolean active, LocalDateTime lastlogin) {
     Objects.requireNonNull(username);
     Objects.requireNonNull(lastlogin);

     this.username = username;
     this.active = active;
     this.lastlogin = lastlogin;
 }
   */
 // 간결 생성자
 public User { // 괄호를 포함하여 모든 인수를 생략

     Objects.requireNonNull(username);
     Objects.requireNonNull(lastlogin);

     username = username.toLowerCase(); // 사용자 정의나 정규화 가능
     // 각 컴포넌트는 자동으로 해당 필드에 매핑됨
 }

 // 빌더 패턴을 레코드에서 사용 가능 by static builder class
 public static final class UserBuilder {
     private final String username;
     private boolean active;
     private LocalDateTime lastLogin;

     public UserBuilder(String username) {
         this.username = username;
         this.active = true;
     }

     // 생성 중에 변경될 수 있는 필드는 setter와 유사한 방식의 메서드 필요
     public UserBuilder active(boolean isActive) {
         if (!this.active) {
             throw new IllegalArgumentException();
         }

         this.active = isActive;
         return this;
     }

     public UserBuilder lastLogin(LocalDateTime lastLogin) {
         this.lastLogin = lastLogin;
         return this;
     }

     public User build() {
         return new User(this.username, this.active, this.lastLogin);
     }
 }
}
