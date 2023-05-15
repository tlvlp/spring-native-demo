package com.tlvlp.springnativedemo;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Service
public class NativeService {

    private final AtomicReference<String> stringCollector = new AtomicReference<>("START");

    public AppendResponse append(AppendRequest request) {
        val result = stringCollector.updateAndGet(coll -> coll + "-" + request.appendWith());
        log.info("collector: " + result);
        return new AppendResponse(result);
    }
}
