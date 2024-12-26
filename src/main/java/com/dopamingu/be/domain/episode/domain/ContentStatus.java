package com.dopamingu.be.domain.episode.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ContentStatus {
    DRAFT("DRAFT"),
    NORMAL("NORMAL"),
    DELETED("DELETED");

    private final String value;
}
