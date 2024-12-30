package io.futakotome.rtck.event;

import com.lmax.disruptor.EventHandler;
import io.futakotome.rtck.mapper.RTKLMapper;
import org.assertj.core.util.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class KLineReceivedEventHandler implements EventHandler<KLineReceivedEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(KLineReceivedEventHandler.class);

    private static final int DB_BATCH_SIZE = 1000;
    private static final int RING_BATCH_SIZE = 1024;
    private final RTKLMapper rtklMapper;
    private static final List<KLineReceivedEvent> cache = Lists.newArrayList();

    public KLineReceivedEventHandler(RTKLMapper mapper) {
        this.rtklMapper = mapper;
    }

    @Override
    public void onEvent(KLineReceivedEvent kLineReceivedEvent, long sequence, boolean endOfBatch) throws Exception {
        cache.add(kLineReceivedEvent);
        if ((sequence + 1) % DB_BATCH_SIZE == 0) {
            LOGGER.info("DB_BATCH_SIZE入库:cache size-{},cache-{}", cache.size(), cache);
            cache.clear();
        }
        if (endOfBatch) {
            if ((sequence + 1) % RING_BATCH_SIZE != 0) {
                LOGGER.info("endOfBatch入库:cache size-{},cache-{}", cache.size(), cache);
                cache.clear();
            }
        }
    }

}
