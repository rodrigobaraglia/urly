package com.rodrigobaraglia.urly.utils;

import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

@Component
@NoArgsConstructor
public class Base62 {

    private static final String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private int base = alphabet.length();

    public String encode(long input) {
        var encoded = new StringBuilder();
        if(input == 0) {
            return String.valueOf(alphabet.charAt(0));
        }
        while (input > 0) {
            encoded.append(alphabet.charAt((int) (input % base)));
            input = input / base;
        }
        return encoded.reverse().toString();
    }

    @Deprecated
    public long decode(String input) {
        System.out.println("Decoding: " + input);
        var decoded = 0;
        var counter = 1;
        var length = input.length();
        for(int i = 0; i < length; i++) {
            decoded += alphabet.indexOf(input.charAt(i)) * Math.pow(base, length - counter);
            counter ++;
        }
        return decoded;
    }
}
