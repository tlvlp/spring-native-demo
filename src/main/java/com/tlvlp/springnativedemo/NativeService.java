package com.tlvlp.springnativedemo;

import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicReference;

@Service
public class NativeService {

    private final AtomicReference<String> stringCollector = new AtomicReference<>();

    public NativeResponse append(NativeRequest request) {
        return new NativeResponse(
                stringCollector.updateAndGet(coll -> coll + "-" + request.appendWith())
        );
    }
}
