package com.tlvlp.springnativedemo;

import jakarta.validation.constraints.NotEmpty;

public record AppendResponse(
        @NotEmpty String appended
) {
}
