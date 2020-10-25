package com.letslearn.letslearngraphqlserver.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Author {
    private final String id;
    private final String firstName;
    private final String lastName;
}
