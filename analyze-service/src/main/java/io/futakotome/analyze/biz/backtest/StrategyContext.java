package io.futakotome.analyze.biz.backtest;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@SuppressWarnings({"unchecked", "rawtypes"})
public class StrategyContext {
    private final ApplicationContext applicationContext;

    public StrategyContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public AbstractStrategyHandler getInstance(Integer type) {
        Map<Integer, Class> map = (Map<Integer, Class>) applicationContext.getBean(StrategyType.class.getName());
        return (AbstractStrategyHandler) applicationContext.getBean(map.get(type));
    }
}
