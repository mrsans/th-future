package org.future.message.raw;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: zhpj
 * @date: 2022-03-03 0:03
 */
@Slf4j
public class KafkaTranactionsTest {

    @Test
    public void kafkaTranactionTest_1() {
        Map<String, Object> configs = new HashMap<>();
        // 链接kafka集群
        configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.35.21:9092, 192.168.35.22:9092");
        // 配置key的序列化
        configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        // 配置value的序列化
        configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        // 创建一个kafka对象
        KafkaProducer<String, String> producer = new KafkaProducer<>(configs);
        // String topic 发送的topic， Integer partition 发送的分区，
        // Long timestamp 发送的时间戳， Object key Object value
        ProducerRecord<String, String> record = new ProducerRecord<>("test-topic", "first-value");
        // 指定事务ID
        configs.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, "trans_id");
        // 初始化事务
        producer.initTransactions();
        // 开启事务
        producer.beginTransaction();
        try {
            // 发送数据   record 是消息体，  Callback callback  回调函数
            // producer.send(record);
            producer.send(record, (metadata, exception) -> {
                if (exception == null) {
                    log.info("发送完成");
                    log.info("数据信息：话题：{}, 分区：{}", metadata.topic(), metadata.partition());
                }
            });
            // 事务提交
            producer.commitTransaction();
        } catch (Exception ex) {
            // 事务回滚
            producer.abortTransaction();
        } finally {
            // 关闭资源
            producer.close();
        }
    }

}
