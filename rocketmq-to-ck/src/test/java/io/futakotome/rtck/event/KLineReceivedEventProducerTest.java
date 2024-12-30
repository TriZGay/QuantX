package io.futakotome.rtck.event;

import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import io.futakotome.rtck.factory.KLineReceivedEventFactory;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class KLineReceivedEventProducerTest {
    @Test
    public void testDisruptor() throws Exception {
        KLineReceivedEventProducer producer = buildProducer();
        ExecutorService executor = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            executor.submit(new TestRunnable("this_table",
                    producer));
        }
        while (!executor.isTerminated()) {
        }
    }

    private KLineReceivedEventProducer buildProducer() {
        KLineReceivedEventFactory factory = new KLineReceivedEventFactory();
        int bufferSize = 1024;
        Disruptor<KLineReceivedEvent> disruptor = new Disruptor<>(
                factory,
                bufferSize,
                Executors.defaultThreadFactory(),
                ProducerType.MULTI,
                new YieldingWaitStrategy());
//        disruptor.handleEventsWith(new KLineReceivedEventHandler());
        disruptor.start();

        return new KLineReceivedEventProducer(disruptor.getRingBuffer());
    }

    public static class TestRunnable implements Runnable {
        private String tableName;
        private KLineReceivedEventProducer producer;

        public TestRunnable(String tableName, KLineReceivedEventProducer producer) {
            this.tableName = tableName;
            this.producer = producer;
        }

        @Override
        public void run() {
//            while (true) {
//                producer.onData(Thread.currentThread().getName() + "_" + tableName, dto);
//            }

        }
    }
}