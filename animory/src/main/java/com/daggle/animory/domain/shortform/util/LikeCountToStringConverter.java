package com.daggle.animory.domain.shortform.util;

public final class LikeCountToStringConverter {
    private LikeCountToStringConverter() {}

    public static String convert(final Integer likeCount) {
        if (likeCount < 1000) return String.valueOf(likeCount);
        else if (likeCount < 10000) return String.format("%.1f", likeCount / 1000.0) + "천";
        else return String.format("%.1f", likeCount / 10000.0) + "만";
    }
}
