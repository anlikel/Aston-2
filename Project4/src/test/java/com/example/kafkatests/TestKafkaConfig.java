package com.example.kafkatests;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;

import java.util.HashMap;
import java.util.Map;

/**
 * Конфигурация для тестирования Kafka.
 *
 * <p>Этот класс предоставляет настроенный {@link KafkaTemplate} для использования
 * в интеграционных тестах с встроенным Kafka брокером.</p>
 *
 * <p>Конфигурация включает:
 * <ul>
 *   <li>Подключение к встроенному Kafka брокеру</li>
 *   <li>Сериализацию ключей как строк</li>
 *   <li>Сериализацию значений как JSON</li>
 *   <li>Настройки надежности доставки сообщений</li>
 * </ul>
 * </p>
 *
 */

@TestConfiguration
public class TestKafkaConfig {

    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;

    /**
     * Создает и настраивает {@link KafkaTemplate} для тестирования.
     *
     * <p>Шаблон настроен для работы со встроенным Kafka брокером и включает:
     * <ul>
     *   <li>Подключение к адресам брокеров из {@link EmbeddedKafkaBroker}</li>
     *   <li>Сериализатор ключей: {@link StringSerializer}</li>
     *   <li>Сериализатор значений: {@link JsonSerializer}</li>
     *   <li>Подтверждение всех реплик (acks=all)</li>
     *   <li>Отключение повторных попыток (retries=0)</li>
     *   <li>Отключение батчинга (batch.size=0)</li>
     * </ul>
     * </p>
     *
     * @return настроенный экземпляр {@link KafkaTemplate} для тестирования
     * @see KafkaTemplate
     * @see ProducerFactory
     * @see EmbeddedKafkaBroker
     */

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, embeddedKafkaBroker.getBrokersAsString());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        props.put(ProducerConfig.ACKS_CONFIG, "all");

        props.put(ProducerConfig.RETRIES_CONFIG, 0);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 0);

        ProducerFactory<String, Object> producerFactory =
                new org.springframework.kafka.core.DefaultKafkaProducerFactory<>(props);
        return new KafkaTemplate<>(producerFactory);
    }
}