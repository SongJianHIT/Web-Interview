/**
 * @projectName Web-Interview
 * @package tech.songjian.disruptor.demo
 * @className tech.songjian.disruptor.demo.OrderEventFactory
 */
package tech.songjian.disruptor.demo;

import com.lmax.disruptor.EventFactory;

/**
 * OrderEventFactory
 * @description 订单工厂类，用于创建Event的实例（OrderEvent)
 * @author SongJian
 * @date 2023/6/8 19:08
 * @version
 */
public class OrderEventFactory implements EventFactory<OrderEvent> {

    public OrderEvent newInstance() {
        return new OrderEvent();
    }
}

