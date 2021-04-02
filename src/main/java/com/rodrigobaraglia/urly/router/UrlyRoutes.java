package com.rodrigobaraglia.urly.router;


import com.rodrigobaraglia.urly.handler.UrlyHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;


@Configuration
public class UrlyRoutes {

    @Bean
    public RouterFunction<ServerResponse> route(UrlyHandler handler) {
        return RouterFunctions.route()
                .GET("/top", handler::getTop10)
                .POST("/",handler::createUrl)
                .GET("/{shorturl}", handler::visit)
                .GET("/{shorturl}/visits", handler::getVisits)
                .build();
    }


}
