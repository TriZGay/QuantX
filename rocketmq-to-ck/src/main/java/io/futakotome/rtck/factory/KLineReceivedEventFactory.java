package io.futakotome.rtck.factory;

import com.lmax.disruptor.EventFactory;
import io.futakotome.rtck.event.KLineReceivedEvent;

public class KLineReceivedEventFactory implements EventFactory<KLineReceivedEvent> {
    @Override
    public KLineReceivedEvent newInstance() {
        return new KLineReceivedEvent();
    }
}
