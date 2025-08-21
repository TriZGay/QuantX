package io.futakotome.analyze.biz.backtest;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface StrategyType {
    int value() default 0;
}
