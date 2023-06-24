package com.tlvlp.springnativedemo;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class AppendResponse {
    @NotEmpty private String appended;
}
