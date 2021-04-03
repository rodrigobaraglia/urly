package com.rodrigobaraglia.urly.router;


import com.rodrigobaraglia.urly.handler.UrlyHandler;
import com.rodrigobaraglia.urly.model.dto.Ranking;
import com.rodrigobaraglia.urly.model.dto.UrlDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;


@Configuration
public class UrlyRoutes {

    @RouterOperations({
            @RouterOperation(path = "/create", operation = @Operation(tags = {"URL Shortener Service"}, operationId = "createShortUrl", summary = "Takes a long URL and returns a short URL",
                    requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = String.class))),
                    responses = {@ApiResponse(responseCode = "200", description = "Short URL successfully created", content = @Content(mediaType = "text/plain; charset=utf-8", schema = @Schema(implementation = String.class))),
                    @ApiResponse(responseCode = "400", description = "Bad Request")})),
            @RouterOperation(path = "/{shorturl}",
                    operation = @Operation(tags = {"URL Shortener Service"}, operationId = "navigateToUrl", summary = "Redirect to original url through short url",
                            parameters = {@Parameter(in = ParameterIn.PATH, name = "shorturl", description = "Short URL", schema = @Schema(implementation = String.class))},
                            responses = {@ApiResponse(responseCode = "307", description = "Succesful redirection to original URL"),
                                    @ApiResponse(responseCode = "404", description = "Short URL not found", content = @Content(mediaType = "text/plain; charset=utf-8"))})),
            @RouterOperation(path = "/{shorturl}/visits",
                    operation = @Operation(tags = {"URL Shortener Service"}, operationId = "getVisitsToUrl", summary = "Get the number of visits to a short URL",
                            parameters = {@Parameter(in = ParameterIn.PATH, name = "shorturl", description = "Short URL", schema = @Schema(implementation = String.class))},
                            responses = {@ApiResponse(responseCode = "200", description = "Succesful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UrlDTO.class))),
                            @ApiResponse(responseCode = "404", description = "Short URL not found", content = @Content(mediaType = "text/plain; charset=utf-8"))})),
            @RouterOperation(path = "/ranking", operation = @Operation(tags = {"URL Shortener Service"}, operationId = "getMostVisitedRanking", summary = "Returns a ranking of the most visited URLs", 
            responses = {@ApiResponse(responseCode = "200", description = "Succesfully got sorted list of short urls", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Ranking.class))),
            @ApiResponse(responseCode = "202", description = "Request accepted but no data to show yet", content = @Content(mediaType = "text/plain; charset=utf-8")),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "text/plain; charset=utf-8"))
            }))
    })
    @Bean
    public RouterFunction<ServerResponse> route(UrlyHandler handler) {
        return RouterFunctions.route()
                .GET("/ranking", handler::getRanking)
                .POST("/create", RequestPredicates.contentType(MediaType.TEXT_PLAIN), handler::createUrl)
                .GET("/{shorturl}", handler::visit)
                .GET("/{shorturl}/visits", handler::getVisits)

                .build();
    }


}
