package com.tlvlp.springnativedemo;

import jakarta.validation.constraints.NotEmpty;

public record NativeResponse(
        @NotEmpty String appended
) {
}
