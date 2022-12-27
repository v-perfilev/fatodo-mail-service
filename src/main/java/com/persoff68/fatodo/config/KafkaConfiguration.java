package com.persoff68.fatodo.config;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.persoff68.fatodo.config.annotation.ConditionalOnPropertyNotNull;
import com.persoff68.fatodo.config.util.KafkaUtils;
import com.persoff68.fatodo.model.dto.FeedbackDTO;
import com.persoff68.fatodo.model.dto.NotificationDTO;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.KafkaAdmin;

@Configuration
@EnableKafka
@ConditionalOnPropertyNotNull(value = "kafka.bootstrapAddress")
@RequiredArgsConstructor
public class KafkaConfiguration {

    private final ObjectMapper objectMapper;

    @Value(value = "${kafka.bootstrapAddress}")
    private String bootstrapAddress;

    @Value(value = "${kafka.groupId}")
    private String groupId;

    @Value(value = "${kafka.partitions}")
    private int partitions;

    @Value(value = "${kafka.autoOffsetResetConfig:latest}")
    private String autoOffsetResetConfig;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        return KafkaUtils.buildKafkaAdmin(bootstrapAddress);
    }

    @Bean
    public NewTopic authNewTopic() {
        return KafkaUtils.buildTopic("mail_auth", partitions);
    }

    @Bean
    public NewTopic notificationNewTopic() {
        return KafkaUtils.buildTopic("mail_notification", partitions);
    }

    @Bean
    public NewTopic feedbackNewTopic() {
        return KafkaUtils.buildTopic("mail_feedback", partitions);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> authContainerFactory() {
        return KafkaUtils.buildStringContainerFactory(bootstrapAddress, groupId, autoOffsetResetConfig);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, NotificationDTO> notificationContainerFactory() {
        JavaType javaType = objectMapper.getTypeFactory().constructType(NotificationDTO.class);
        return KafkaUtils.buildJsonContainerFactory(bootstrapAddress, groupId, autoOffsetResetConfig, javaType);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, FeedbackDTO> feedbackContainerFactory() {
        JavaType javaType = objectMapper.getTypeFactory().constructType(FeedbackDTO.class);
        return KafkaUtils.buildJsonContainerFactory(bootstrapAddress, groupId, autoOffsetResetConfig, javaType);
    }

}
