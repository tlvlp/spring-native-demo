package com.tlvlp.springnativedemo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.stream.Stream;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class NativeControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;

    static Stream<Arguments> testValues() {
        return Stream.of(
                Arguments.of("first", "first"),
                Arguments.of("second", "first-second"),
                Arguments.of("third", "first-second-third"),
                Arguments.of("fourth", "first-second-third-fourth")
        );
    }

    @ParameterizedTest
    @MethodSource("testValues")
    void testConcat(String toAppend, String appended) throws Exception {
        var content = mapper.writeValueAsString(new NativeRequest(toAppend));
        System.out.println("conent: " + content);
        mockMvc.perform(post("/append")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(appended)));
    }

}
