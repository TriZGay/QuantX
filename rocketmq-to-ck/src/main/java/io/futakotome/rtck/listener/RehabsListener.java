package io.futakotome.rtck.listener;

import io.futakotome.common.MessageCommon;
import io.futakotome.common.message.RehabMessage;
import io.futakotome.rtck.mapper.RehabsMapper;
import io.futakotome.rtck.mapper.dto.RehabDto;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
//@RocketMQMessageListener(consumerGroup = MessageCommon.REHAB_CONSUMER_GROUP, topic = MessageCommon.REHAB_TOPIC)
public class RehabsListener implements RocketMQListener<RehabMessage> {
    private static final Logger LOGGER = LoggerFactory.getLogger(RehabsListener.class);

    private final RehabsMapper mapper;

    public RehabsListener(RehabsMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void onMessage(RehabMessage rehabMessage) {
        RehabDto rehabDto = new RehabDto();
        rehabDto.setMarket(rehabMessage.getMarket());
        rehabDto.setCode(rehabMessage.getCode());
        rehabDto.setCompanyActFlag(rehabMessage.getCompanyActFlag());
        rehabDto.setFwdFactorA(rehabMessage.getFwdFactorA());
        rehabDto.setFwdFactorB(rehabMessage.getFwdFactorB());
        rehabDto.setBwdFactorA(rehabMessage.getBwdFactorA());
        rehabDto.setBwdFactorB(rehabMessage.getBwdFactorB());
        rehabDto.setSplitBase(rehabMessage.getSplitBase());
        rehabDto.setSplitErt(rehabMessage.getSplitErt());
        rehabDto.setJoinBase(rehabMessage.getJoinBase());
        rehabDto.setJoinErt(rehabMessage.getJoinErt());
        rehabDto.setBonusBase(rehabMessage.getBonusBase());
        rehabDto.setBonusErt(rehabMessage.getBonusErt());
        rehabDto.setTransferBase(rehabMessage.getTransferBase());
        rehabDto.setTransferErt(rehabMessage.getTransferErt());
        rehabDto.setAllotBase(rehabMessage.getAllotBase());
        rehabDto.setAllotErt(rehabMessage.getAllotErt());
        rehabDto.setAllotPrice(rehabMessage.getAllotPrice());
        rehabDto.setAddBase(rehabMessage.getAddBase());
        rehabDto.setAddErt(rehabMessage.getAddErt());
        rehabDto.setAddPrice(rehabMessage.getAddPrice());
        rehabDto.setDividend(rehabMessage.getDividend());
        rehabDto.setSpDividend(rehabMessage.getSpDividend());
        rehabDto.setTime(rehabMessage.getTime());
        if (mapper.insertOneRehab(rehabDto)) {
            LOGGER.info("复权因子数据入库成功.");
        }
    }
}
