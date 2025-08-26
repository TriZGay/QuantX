package io.futakotome.rtck.service;

import io.futakotome.rtck.mapper.KLMapper;
import io.futakotome.rtck.mapper.dto.RTKLDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class KLineService {
    private static final Logger LOGGER = LoggerFactory.getLogger(KLineService.class);
    private final KLMapper kLineMapper;

    public KLineService(KLMapper kLineMapper) {
        this.kLineMapper = kLineMapper;
    }

    @Async
    public void saveKLines(List<RTKLDto> toAddKLines, String table) {
        if (kLineMapper.insertBatch(toAddKLines, table)) {
            LOGGER.info("{},K线入库成功", table);
        }
    }
}
