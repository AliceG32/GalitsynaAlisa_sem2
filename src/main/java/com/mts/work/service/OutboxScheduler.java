package com.mts.work.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mts.work.entity.Outbox;
import com.mts.work.kafka.DtoMessage;
import com.mts.work.repository.OutboxRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class OutboxScheduler {
  private final KafkaTemplate<String, String> kafkaTemplate;
  private final String topic;
  private final OutboxRepository outboxRepository;
  private final KafkaProducerService kafkaProducerService;
  ObjectMapper objectMapper = new ObjectMapper();

  public OutboxScheduler(
          KafkaTemplate<String, String> kafkaTemplate,
          OutboxRepository outboxRepository,
          @Value("${topic-to-send-message}") String topic,
          KafkaProducerService kafkaProducerService
  ) {
    this.kafkaTemplate = kafkaTemplate;
    this.topic = topic;
    this.outboxRepository = outboxRepository;
    this.kafkaProducerService = kafkaProducerService;
  }

  @Transactional
  @Scheduled(fixedDelay = 10000)
  public void processOutbox() throws JsonProcessingException {
    List<Outbox> result = outboxRepository.findAll();
    for (Outbox outboxRecord : result) {
      CompletableFuture<SendResult<String, String>> sendResult = kafkaProducerService.sendMessage(
              objectMapper.readValue(outboxRecord.getData(), DtoMessage.class)
      );
    }
    outboxRepository.deleteAll(result);
  }
}