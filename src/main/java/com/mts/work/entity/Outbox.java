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
@Table(name = "outbox", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
public class Outbox {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "outboxSeqGen")
  @SequenceGenerator(name = "outboxSeqGen", sequenceName = "outbox_id_seq",  allocationSize = 1)
  @JsonIgnore
  @Hidden
  private Integer id;
  @NotNull(message = "Data can not be NULL")
  private String data;

  public Outbox(String data) {
    this.data = data;
  }
}
