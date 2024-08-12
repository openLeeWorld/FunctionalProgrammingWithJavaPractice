package com.example.ch7.DownStreamCollector;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record User(UUID id, String group, LocalDateTime lastLogin,
                   List<String> logEntries) {
}
