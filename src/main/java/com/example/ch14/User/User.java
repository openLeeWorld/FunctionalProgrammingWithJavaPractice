package com.example.ch14.User;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public record User(String email, String name, List<String> permissions) {

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        public String email;
        public String name;
        private final List<String> permissions = new ArrayList<>();

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder addPermission(String permission) {
            this.permissions.add(permission);
            return this;
        }

        public User build() {
            return new User(this.email, this.name, this.permissions);
        }

        public Builder with(Consumer<Builder> builderFn) {
            builderFn.accept(this);
            return this;
        }

        public Builder withPermissions(Consumer<List<String>> permissionsFn) {
            permissionsFn.accept(this.permissions);
            return this;
        }
    }
}
