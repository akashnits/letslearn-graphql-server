package com.letslearn.letslearngraphqlserver.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Book {
    private final String id;
    private final String name;
    private final int pageCount;
    private final String authorId;
}
