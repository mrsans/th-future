package org.future.message.raw;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

/**
 * 自定义分区
 * @author: zhpj
 * @date: 2022-03-02 16:53
 */
@Slf4j
public class KafkaCustomPartition implements Partitioner {

    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        if (Objects.isNull(key)) {
            throw new NullPointerException("key不能为空");
        }
        // 将分区分成   【0： 包含 future， 1：不包含future】
        if (StringUtils.contains(key.toString(), "future")) {
            return 0;
        }
        return 1;
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> configs) {

    }

    @Test
    public void testKafkaPartitionProducer_sync() throws ExecutionException, InterruptedException {
        Map<String, Object> configs = new HashMap<>();
        // 链接kafka集群
        configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.35.21:9092, 192.168.35.22:9092");
        // 配置key的序列化
        configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        // 配置value的序列化
        configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        // 配置分区
        configs.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, KafkaCustomPartition.class.getName());
        // 创建一个kafka对象
        KafkaProducer<String, String> producer = new KafkaProducer<>(configs);
        // String topic 发送的topic， Integer partition 发送的分区，
        // Long timestamp 发送的时间戳， Object key Object value
        ProducerRecord<String, String> record = new ProducerRecord<>("test-topic", "222222", "first-value");
        // 发送数据   record 是消息体，  Callback callback  回调函数
        // producer.send(record);
        producer.send(record, (metadata, exception) -> {
            if (exception == null) {
                log.info("发送完成");
                log.info("数据信息：话题：{}, 分区：{}", metadata.topic(), metadata.partition());
            }
        }).get();
        // 关闭资源
        producer.close();
    }

}
