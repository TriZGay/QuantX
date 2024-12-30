package io.futakotome.rtck.event;

import com.lmax.disruptor.RingBuffer;
import io.futakotome.rtck.mapper.dto.RTKLDto;

public class KLineReceivedEventProducer {
    private final RingBuffer<KLineReceivedEvent> buffer;

    public KLineReceivedEventProducer(RingBuffer<KLineReceivedEvent> buffer) {
        this.buffer = buffer;
    }

    public void onData(String tableName, RTKLDto dto) {
        long sequence = buffer.next();
        try {
            KLineReceivedEvent event = buffer.get(sequence);
            event.setTableName(tableName);
            event.setDto(dto);
        } finally {
            buffer.publish(sequence);
        }
    }
}
