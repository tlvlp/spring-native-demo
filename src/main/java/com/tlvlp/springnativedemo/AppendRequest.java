package com.tlvlp.springnativedemo;

import jakarta.validation.constraints.NotEmpty;

public record AppendRequest(
        @NotEmpty String appendWith
) {
}
