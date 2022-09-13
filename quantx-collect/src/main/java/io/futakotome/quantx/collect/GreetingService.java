package io.futakotome.quantx.collect;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GreetingService {

    public String greeting(String name) {
        return "halo" + name;
    }
}
