package io.futakotome.sec.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.regex.Pattern;

@Component
public class SpaRedirectFilter implements WebFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(SpaRedirectFilter.class);

    // Forwards all routes except '/index.html', '/200.html', '/favicon.ico', '/sw.js' '/api/', '/api/**'
    private static final String REG = "(?!/actuator|/api|/_nuxt|/static|/index\\.html|/200\\.html|/favicon\\.ico|/sw\\.js).*$";
    private static final Pattern PATTERN = Pattern.compile(REG);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String uri = exchange.getRequest().getURI().getPath();
        if (PATTERN.matcher(uri).matches() && !uri.equals("/")) {
            exchange.getResponse().setStatusCode(HttpStatus.PERMANENT_REDIRECT);
            exchange.getResponse().getHeaders().setLocation(URI.create("/"));
            LOGGER.info("URL {} 重定向...", uri);
            return exchange.getResponse().setComplete();
        } else {
            return chain.filter(exchange);
        }
    }
}
