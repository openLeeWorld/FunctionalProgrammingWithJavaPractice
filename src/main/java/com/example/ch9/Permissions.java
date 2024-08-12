package com.example.ch9;

import java.util.List;
import java.util.Optional;

public record Permissions(List<String> permissions, Group group) {

    public boolean isEmpty() {
        return permissions.isEmpty();
    }

    public record Group(Optional<User> admin) {
    }

    public record User(boolean isActive) {
    }
}
