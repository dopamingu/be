package com.dopamingu.be.domain.episode.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EpisodeTheme {
    FLIRTING("썸"),
    CONFESSION("고백"),
    REUNION("재회"),
    BREAKUP("이별"),
    BLIND_DATE("소개팅"),
    DATE("데이트");

    private final String value;

}