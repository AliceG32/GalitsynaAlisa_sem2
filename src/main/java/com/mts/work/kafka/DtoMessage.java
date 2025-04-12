package com.mts.work.kafka;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class DtoMessage {
  public enum Action {
    CREATE, UPDATE, DELETE
  }

  public UUID userId;
  public String date;
  public String eventType;
  public String eventDetails;

  public DtoMessage() {
  }

  public DtoMessage(UUID userId, String eventType, String eventDetails) {
    this.date = Instant.now().toString();
    this.userId = userId;
    this.eventType = eventType;
    this.eventDetails = eventDetails;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    DtoMessage dto = (DtoMessage) o;
    return userId.equals(dto.userId) && eventType.equals(dto.eventType) && eventDetails.equals(dto.eventDetails);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userId, eventType, eventDetails);
  }
}
