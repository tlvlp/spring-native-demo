package com.tlvlp.springnativedemo;

import jakarta.validation.constraints.NotEmpty;

public record NativeRequest(
        @NotEmpty String appendWith
) {
}
