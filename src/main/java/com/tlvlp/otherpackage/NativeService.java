package com.tlvlp.otherpackage;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tlvlp.springnativedemo.AppendRequest;
import com.tlvlp.springnativedemo.AppendResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Service
@RequiredArgsConstructor
public class NativeService {

    private final ObjectMapper mapper;
    private final AtomicReference<String> stringCollector = new AtomicReference<>("START");

    @SneakyThrows
    public AppendResponse append(JsonNode requestNode) {

        // Operation using reflection that is not automatically discovered by GraalVM AOT.
        // It either needs to be registered manually or viewed by the GraalVM Tracing Agent during "training".
        val request = mapper.treeToValue(requestNode, AppendRequest.class);

        val result = stringCollector.updateAndGet(coll -> coll + "-" + request.getAppendWith());
        log.info("collected: " + request.getAppendWith());
        return new AppendResponse(result);
    }
}
