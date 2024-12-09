package com.dopamingu.be.domain.episode.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EpisodeStatus {

    DRAFT("DRAFT"),
    NORMAL("NORMAL"),
    DELETED("DELETED");

    private final String value;
}