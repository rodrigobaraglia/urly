package com.rodrigobaraglia.urly.utils;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class Base62Test {


    @Test
    void encode() {
        Base62 encoder = new Base62();
        long key = Math.abs(UUID.randomUUID().hashCode());
        String enc = encoder.encode(key);
        long dec = encoder.decode(enc);
        assertEquals(key, dec);
    }


}