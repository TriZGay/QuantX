package io.futakotome.analyze.job;

import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.TriggerListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class QuantxTriggerListener implements TriggerListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(QuantxTriggerListener.class);

    @Override
    public String getName() {
        return "quantxTriggerListener";
    }

    @Override
    public void triggerFired(Trigger trigger, JobExecutionContext jobExecutionContext) {
        LOGGER.info("triggerFired:{}", jobExecutionContext.toString());
    }

    @Override
    public boolean vetoJobExecution(Trigger trigger, JobExecutionContext jobExecutionContext) {
        LOGGER.info("vetoJobExecution:{}", jobExecutionContext.toString());
        return false;
    }

    @Override
    public void triggerMisfired(Trigger trigger) {
        LOGGER.info("triggerMisfired:{}", trigger.toString());
    }

    @Override
    public void triggerComplete(Trigger trigger, JobExecutionContext jobExecutionContext, Trigger.CompletedExecutionInstruction completedExecutionInstruction) {
        LOGGER.info("triggerComplete:{},{}", jobExecutionContext.toString(), completedExecutionInstruction);
    }
}
