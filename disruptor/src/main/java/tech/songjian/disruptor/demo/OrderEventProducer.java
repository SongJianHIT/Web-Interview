/**
 * @projectName Web-Interview
 * @package tech.songjian.disruptor.demo
 * @className tech.songjian.disruptor.demo.OrderEventProducer
 */
package tech.songjian.disruptor.demo;

import com.lmax.disruptor.RingBuffer;

import java.nio.ByteBuffer;

/**
 * OrderEventProducer
 * @description 订单生产者
 * @author SongJian
 * @date 2023/6/8 19:13
 * @version
 */
public class OrderEventProducer {
    /**
     * 环形队列
     */
    private RingBuffer<OrderEvent> ringBuffer;

    public OrderEventProducer(RingBuffer<OrderEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    /**
     * 生产者投递数据
     */
    public void sendData(ByteBuffer data) {
        // 1、在生产者发送消息时，首先要从 ringBuffer 中找到一个可用的序号
        long sequence = ringBuffer.next();
        try {
            // 2、根据这个序号找到具体的 OrderEvent 元素
            // 此时获取到的对象是一个还未被赋值的对象
            OrderEvent event = ringBuffer.get(sequence);
            event.setValue(data.getLong(0));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 4、提交发布操作
            ringBuffer.publish(sequence);
        }
    }
}

