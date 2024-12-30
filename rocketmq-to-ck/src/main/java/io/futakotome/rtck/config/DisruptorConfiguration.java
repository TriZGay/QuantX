package io.futakotome.rtck.config;

import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import io.futakotome.rtck.event.KLineReceivedEvent;
import io.futakotome.rtck.event.KLineReceivedEventHandler;
import io.futakotome.rtck.event.KLineReceivedEventProducer;
import io.futakotome.rtck.factory.KLineReceivedEventFactory;
import io.futakotome.rtck.mapper.RTKLMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;

@Configuration
public class DisruptorConfiguration {
    private final RTKLMapper mapper;

    public DisruptorConfiguration(RTKLMapper mapper) {
        this.mapper = mapper;
    }

    @Bean
    public KLineReceivedEventProducer kLineReceivedEventProducer() {
        KLineReceivedEventFactory factory = new KLineReceivedEventFactory();
        int bufferSize = 1024 * 8;
        Disruptor<KLineReceivedEvent> disruptor = new Disruptor<>(
                factory,
                bufferSize,
                Executors.defaultThreadFactory(),
                ProducerType.MULTI,
                new YieldingWaitStrategy());
        disruptor.handleEventsWith(new KLineReceivedEventHandler(mapper));
        disruptor.start();
        return new KLineReceivedEventProducer(disruptor.getRingBuffer());
    }
}
