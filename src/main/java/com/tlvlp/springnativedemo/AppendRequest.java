package com.tlvlp.springnativedemo;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class AppendRequest {
    @NotEmpty
    private String appendWith;
}
