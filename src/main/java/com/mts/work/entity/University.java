package com.mts.work.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "universities", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
public class University {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "universitySeqGen")
    @SequenceGenerator(name = "universitySeqGen", sequenceName = "universities_id_seq",  allocationSize = 1)
    @JsonIgnore
    @Hidden
    private Integer id;
    @NotNull(message = "Name can not be NULL")
    private String name;
    private int userId;
}
