package com.mts.work.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserId {
    private final long value;

    public UserId(int value) {
        this.value = value;
    }
}
