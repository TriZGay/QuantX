package io.futakotome.trade;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class FutuCollectApplication {
    public static void main(String[] args) {
        SpringApplication.run(FutuCollectApplication.class, args);
    }
}
