package com.example.kafkatests;

import com.example.entities.UserEntity;
import com.example.notificationhandlers.EventType;
import com.example.notificationhandlers.ServiceEventDto;
import com.example.service.KafkaService;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import java.time.Duration;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Интеграционные тесты для сервиса Kafka, проверяющие функциональность отправки email при создании пользователя.
 *
 * <p>Тесты используют встроенный Kafka брокер для изоляции тестовой среды и проверяют:
 * <ul>
 *   <li>Корректность отправки событий создания пользователя</li>
 *   <li>Порядок обработки множественных событий</li>
 *   <li>Обработку ошибочных сценариев</li>
 *   <li>Наличие временной метки в событиях</li>
 * </ul>
 * </p>
 *
 */
@SpringBootTest
@EmbeddedKafka(partitions = 1, topics = {"user-topic"})
@ContextConfiguration(classes = {KafkaService.class, TestKafkaConfig.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class KafkaServiceIntegrationCreateUserTest {

    @Autowired
    private KafkaService kafkaService;

    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;

    private Consumer<String, ServiceEventDto> consumer;

    /**
     * Настраивает тестовое окружение перед каждым тестом.
     *
     * <p>Создает и настраивает Kafka consumer со следующими параметрами:
     * <ul>
     *   <li>Группа: "test-group-create"</li>
     *   <li>Автоподтверждение: true</li>
     *   <li>Сброс смещения: earliest</li>
     *   <li>Десериализатор ключей: StringDeserializer</li>
     *   <li>Десериализатор значений: JsonDeserializer</li>
     *   <li>Доверенные пакеты: все (*)</li>
     *   <li>Тип значения по умолчанию: ServiceEventDto</li>
     * </ul>
     * </p>
     */
    @BeforeEach
    void setUp() {
        Map<String, Object> consumerProps = KafkaTestUtils.consumerProps(
                "test-group-create", "true", embeddedKafkaBroker);
        consumerProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.springframework.kafka.support.serializer.JsonDeserializer");
        consumerProps.put("spring.json.trusted.packages", "*");
        consumerProps.put("spring.json.value.default.type", "com.example.notificationhandlers.ServiceEventDto");

        DefaultKafkaConsumerFactory<String, ServiceEventDto> consumerFactory =
                new DefaultKafkaConsumerFactory<>(consumerProps);
        consumer = consumerFactory.createConsumer();
        embeddedKafkaBroker.consumeFromAnEmbeddedTopic(consumer, "user-topic");
    }

    /**
     * Очищает тестовое окружение после каждого теста.
     *
     * <p>Закрывает consumer для освобождения ресурсов и предотвращения утечек памяти.</p>
     */
    @AfterEach
    void tearDown() {
        if (consumer != null) {
            consumer.close();
        }
    }

    /**
     * Проверяет успешную отправку события создания пользователя.
     *
     * <p>Тест проверяет, что при вызове метода {@link KafkaService#sendEmailOnUserCreate(UserEntity)}
     * в Kafka отправляется корректное событие с правильными данными:
     * <ul>
     *   <li>Ключ сообщения соответствует email пользователя</li>
     *   <li>Тип события равен {@link EventType#CREATE}</li>
     *   <li>Email и имя в событии соответствуют переданному пользователю</li>
     *   <li>Событие содержит положительную временную метку</li>
     * </ul>
     * </p>
     */
    @Test
    void sendEmailOnUserCreate_WhenEventSuccess_GetRecord() {

        UserEntity user = new UserEntity("Aaaa", "aaa@mail.com", 12);

        kafkaService.sendEmailOnUserCreate(user);

        await().atMost(Duration.ofSeconds(10))
                .untilAsserted(() -> {
                    ConsumerRecord<String, ServiceEventDto> record =
                            KafkaTestUtils.getSingleRecord(consumer, "user-topic");

                    assertThat(record).isNotNull();
                    assertThat(record.key()).isEqualTo("aaa@mail.com");

                    ServiceEventDto event = record.value();
                    assertThat(event.getEmail()).isEqualTo("aaa@mail.com");
                    assertThat(event.getName()).isEqualTo("Aaaa");
                    assertThat(event.getEventType()).isEqualTo(EventType.CREATE);
                    assertThat(event.getTimestamp()).isPositive();
                });
    }

    /**
     * Проверяет корректную обработку множественных событий создания пользователей.
     *
     * <p>Тест проверяет, что при отправке нескольких событий создания:
     * <ul>
     *   <li>Все события успешно доставляются в Kafka</li>
     *   <li>События сохраняют порядок отправки (по offset)</li>
     *   <li>Каждое событие содержит корректные данные соответствующего пользователя</li>
     *   <li>Тип события для всех сообщений равен {@link EventType#CREATE}</li>
     * </ul>
     * </p>
     */
    @Test
    void sendEmailOnUserCreate_WhenEventMultipleSuccess_GetRecordWithProperOrder() {

        UserEntity user1 = new UserEntity("Aa", "aaa@mail.com", 12);
        UserEntity user2 = new UserEntity("Bb", "bbb@mail.com", 12);

        kafkaService.sendEmailOnUserCreate(user1);
        kafkaService.sendEmailOnUserCreate(user2);

        await().atMost(Duration.ofSeconds(10))
                .pollInterval(Duration.ofMillis(500))
                .untilAsserted(() -> {
                    ConsumerRecords<String, ServiceEventDto> records =
                            consumer.poll(Duration.ofMillis(1000));

                    assertThat(records.count()).isEqualTo(2);

                    List<ConsumerRecord<String, ServiceEventDto>> recordList =
                            StreamSupport.stream(records.spliterator(), false)
                                    .collect(Collectors.toList());

                    recordList.sort(Comparator.comparing(ConsumerRecord::offset));

                    ConsumerRecord<String, ServiceEventDto> record1 = recordList.get(0);
                    assertThat(record1.key()).isEqualTo("aaa@mail.com");
                    assertThat(record1.value().getEventType()).isEqualTo(EventType.CREATE);
                    assertThat(record1.value().getEmail()).isEqualTo("aaa@mail.com");
                    assertThat(record1.value().getName()).isEqualTo("Aa");

                    ConsumerRecord<String, ServiceEventDto> record2 = recordList.get(1);
                    assertThat(record2.key()).isEqualTo("bbb@mail.com");
                    assertThat(record2.value().getEventType()).isEqualTo(EventType.CREATE);
                    assertThat(record2.value().getEmail()).isEqualTo("bbb@mail.com");
                    assertThat(record2.value().getName()).isEqualTo("Bb");
                });
    }

    /**
     * Проверяет обработку некорректного входного параметра.
     *
     * <p>Тест проверяет, что передача null в качестве параметра в метод
     * {@link KafkaService#sendEmailOnUserCreate(UserEntity)} приводит к выбрасыванию
     * исключения {@link NullPointerException}.
     * </p>
     */
    @Test
    void sendEmailOnUserCreate_WhenUserIsNull_ShouldThrowException() {

        assertThrows(NullPointerException.class,
                () -> kafkaService.sendEmailOnUserCreate(null));
    }
}