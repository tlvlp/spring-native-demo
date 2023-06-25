package com.tlvlp.springnativedemo;

import com.fasterxml.jackson.databind.JsonNode;
import com.tlvlp.otherpackage.NativeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@RestController
public class NativeController {

    private final NativeService service;

    @PostMapping(value = "/append",
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE)
    public AppendResponse append(@RequestBody JsonNode requestNode) {
        return service.append(requestNode);
    }

}
