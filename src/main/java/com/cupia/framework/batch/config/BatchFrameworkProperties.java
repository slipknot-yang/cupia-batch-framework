package com.cupia.framework.batch.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration properties for CUPIA Batch Framework
 * 
 * Allows users to customize framework behavior through application properties.
 * 
 * Example usage in application.yml:
 * <pre>
 * cupia:
 *   batch:
 *     kafka:
 *       enabled: true
 *     processor:
 *       type: simple
 * </pre>
 * 
 * @author CUPIA Framework Team
 * @version 1.0.0
 */
@ConfigurationProperties(prefix = "cupia.batch")
public class BatchFrameworkProperties {

    private Kafka kafka = new Kafka();
    private Processor processor = new Processor();

    public Kafka getKafka() {
        return kafka;
    }

    public void setKafka(Kafka kafka) {
        this.kafka = kafka;
    }

    public Processor getProcessor() {
        return processor;
    }

    public void setProcessor(Processor processor) {
        this.processor = processor;
    }

    public static class Kafka {
        /**
         * Whether to enable Kafka integration
         */
        private boolean enabled = true;

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }
    }

    public static class Processor {
        /**
         * Type of event processor to use (simple, batch)
         */
        private ProcessorType type = ProcessorType.SIMPLE;

        public ProcessorType getType() {
            return type;
        }

        public void setType(ProcessorType type) {
            this.type = type;
        }
    }

    public enum ProcessorType {
        SIMPLE, BATCH
    }
}