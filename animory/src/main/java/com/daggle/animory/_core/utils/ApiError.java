package com.daggle.animory._core.utils;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ApiError(
        @JsonProperty("message") String message,
        @JsonProperty("status") int status) {

}
