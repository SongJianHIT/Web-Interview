/**
 * @projectName Web-Interview
 * @package tech.songjian.disruptor.demo
 * @className tech.songjian.disruptor.demo.OrderEventHandler
 */
package tech.songjian.disruptor.demo;

import com.lmax.disruptor.EventHandler;

/**
 * OrderEventHandler
 * @description 消费者
 * @author SongJian
 * @date 2023/6/8 19:20
 * @version
 */
public class OrderEventHandler implements EventHandler<OrderEvent> {

    public void onEvent(OrderEvent orderEvent, long l, boolean b) throws Exception {
        //取出订单对象的价格。
        System.err.println("消费者:"+ orderEvent.getValue());
    }
}

