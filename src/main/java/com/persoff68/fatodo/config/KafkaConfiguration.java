package com.persoff68.fatodo.config;

import com.persoff68.fatodo.config.annotation.ConditionalOnPropertyNotNull;
import com.persoff68.fatodo.config.util.KafkaUtils;
import com.persoff68.fatodo.model.dto.ActivationDTO;
import com.persoff68.fatodo.model.dto.NotificationDTO;
import com.persoff68.fatodo.model.dto.ResetPasswordDTO;
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
public class KafkaConfiguration {

    @Value(value = "${kafka.bootstrapAddress}")
    private String bootstrapAddress;

    @Value(value = "${kafka.groupId}")
    private String groupId;

    @Value(value = "${kafka.partitions}")
    private int partitions;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        return KafkaUtils.buildKafkaAdmin(bootstrapAddress);
    }

    @Bean
    public NewTopic activationNewTopic() {
        return KafkaUtils.buildTopic("mail_activation", partitions);
    }

    @Bean
    public NewTopic resetPasswordNewTopic() {
        return KafkaUtils.buildTopic("mail_resetPassword", partitions);
    }

    @Bean
    public NewTopic notificationNewTopic() {
        return KafkaUtils.buildTopic("mail_notification", partitions);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ActivationDTO> activationContainerFactory() {
        return KafkaUtils.buildJsonContainerFactory(bootstrapAddress, groupId, ActivationDTO.class);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ResetPasswordDTO> resetPasswordContainerFactory() {
        return KafkaUtils.buildJsonContainerFactory(bootstrapAddress, groupId, ResetPasswordDTO.class);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, NotificationDTO> notificationContainerFactory() {
        return KafkaUtils.buildJsonContainerFactory(bootstrapAddress, groupId, NotificationDTO.class);
    }

}
