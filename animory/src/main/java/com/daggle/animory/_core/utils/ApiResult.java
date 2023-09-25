package com.daggle.animory._core.utils;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ApiResult<T>(
        @JsonProperty("success") boolean success,
        @JsonProperty("response") T response,
        @JsonProperty("error") ApiError error) {
}
