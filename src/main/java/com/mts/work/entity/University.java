package com.mts.work.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class University {
    private UniversityId id;
    private String name;
    private UserId userId;
}
