package com.cupia.framework.batch.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaTemplate;

/**
 * Auto-configuration for Kafka integration in CUPIA Batch Framework
 * 
 * This configuration is automatically enabled when:
 * 1. Kafka classes are present on the classpath
 * 2. Property 'cupia.batch.kafka.enabled' is set to true (default: true)
 * 
 * @author CUPIA Framework Team
 * @version 1.0.0
 */
@AutoConfiguration
@ConditionalOnClass({KafkaTemplate.class})
@ConditionalOnProperty(
    name = "cupia.batch.kafka.enabled", 
    havingValue = "true", 
    matchIfMissing = true
)
public class KafkaAutoConfiguration {

    @Configuration(proxyBeanMethods = false)
    @EnableKafka
    static class KafkaConfiguration {
        // Kafka-specific beans will be configured here
    }
}