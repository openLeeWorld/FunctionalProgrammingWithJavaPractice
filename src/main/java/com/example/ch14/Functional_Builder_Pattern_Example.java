package com.example.ch14;

import com.example.ch14.User.User;
public class Functional_Builder_Pattern_Example {
    public static void main(String[] args) {
        // 빌더 패턴으로 유저 객체 등록
        var builder = User.builder()
                .email("ben@example.com")
                .name("Ben Weidig");

        var user = builder.addPermission("create")
                            .addPermission("edit")
                            .build();
        System.out.println(user);

        var user2 = User.builder()
                .with(builder2 -> {
                    builder2.email = "ben@example.com";
                    builder2.name = "Ben Weidig";
                })
                .withPermissions(permissions -> {
                    permissions.add("create");
                    permissions.add("view");
                })
                .build();
        System.out.println(user2);

    }
}
