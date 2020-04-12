package org.uma.external.handler;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.uma.external.jvlink.exception.JvLinkErrorCode;
import org.uma.external.jvlink.exception.JvLinkRuntimeException;
import org.uma.external.jvlink.response.JvStringContent;
import org.uma.external.jvlink.util.ByteUtil;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Supplier;

@Slf4j
public class BaseHandler {

    protected static final String OK = "OK";


    private static Flux<ExternalResponse> toPublisher(Supplier<List<JvStringContent>> supplier) {
        // blockingする
        List<JvStringContent> jvStringContents = Objects.requireNonNull(supplier).get();

        return Flux.fromIterable(jvStringContents)
                .map(JvStringContent::getLine)
                .map(ByteUtil::toByte)
                .map(bytes -> Base64.getEncoder().encode(bytes))
                .map(encoded -> new String(encoded, StandardCharsets.ISO_8859_1))
                .map(data -> ExternalResponse.builder().data(data).message(OK).build());
    }

    @NonNull
    protected static Mono<ServerResponse> okMono(Supplier<List<JvStringContent>> supplier) {
        try {
            return ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromPublisher(toPublisher(supplier).single(), ExternalResponse.class));

            // Monoに変換するの失敗したら。
        } catch (IndexOutOfBoundsException | NoSuchElementException e) {
            log.error("Mono convert Error:", e);
            throw new JvLinkRuntimeException(JvLinkErrorCode._401);
        }
    }

    @NonNull
    protected static Mono<ServerResponse> okFlux(Supplier<List<JvStringContent>> supplier) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(toPublisher(supplier), ExternalResponse.class));
    }

    @NonNull
    protected static Mono<ServerResponse> errorPublisher(HttpStatus httpStatus, String errorMessage) {
        Mono<ExternalResponse> mono = Mono.just(ExternalResponse.builder()
                .data("")
                .message(errorMessage)
                .build());

        return ServerResponse.status(httpStatus)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(mono, ExternalResponse.class));
    }

    @NonNull
    protected static Mono<ServerResponse> jvLinkErrorHandle(
            ServerRequest request, HandlerFunction<ServerResponse> next) {
        try {
            return next.handle(request);

        } catch (JvLinkRuntimeException e) {
            log.error("JvLink Error: ", e);
            JvLinkErrorCode code = JvLinkErrorCode.of(e.getErrorCode());

            switch (code) {
                case _1:
                    // 404
                    return errorPublisher(HttpStatus.NOT_FOUND, e.getMessage());
                case _504:
                    // 503
                    return errorPublisher(HttpStatus.SERVICE_UNAVAILABLE, e.getMessage());
                default:
                    // 400
                    return errorPublisher(HttpStatus.BAD_REQUEST, e.getMessage());
            }
        }
    }


    @Getter
    @ToString
    protected static class ExternalResponse {

        private final String data;

        private final String message;

        @JsonCreator
        public ExternalResponse(
                @JsonProperty("data") String data,
                @JsonProperty("message") String message) {
            this.data = data;
            this.message = message;
        }

        public static ExternalResponseBuilder builder() {
            return new ExternalResponseBuilder();
        }


        public static class ExternalResponseBuilder {
            private String data;
            private String message;

            ExternalResponseBuilder() {
            }

            public ExternalResponseBuilder data(String data) {
                this.data = data;
                return this;
            }

            public ExternalResponseBuilder message(String message) {
                this.message = message;
                return this;
            }

            public ExternalResponse build() {
                return new ExternalResponse(data, message);
            }

            public String toString() {
                return "BaseHandler.ExternalResponse.ExternalResponseBuilder(data=" + this.data + ", message=" + this.message + ")";
            }
        }
    }
}
