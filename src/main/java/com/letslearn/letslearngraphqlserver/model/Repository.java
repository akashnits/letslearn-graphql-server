package com.letslearn.letslearngraphqlserver.model;

import java.util.*;

public class Repository {
    public static final List<Book> BOOKS = new ArrayList<>();
    public static final List<Author> AUTHORS = new ArrayList<>();

    static {
        BOOKS.add(new Book("book-1","Harry Potter and the Philosopher's Stone", 223,"author-1"));
        BOOKS.add(new Book("book-2", "Moby", 635, "author-1"));
        BOOKS.add(new Book("book-3", "Interview with the vampire", 371, "author-3"));

        AUTHORS.add(new Author("author-1", "Joanne", "Rowling"));
        AUTHORS.add(new Author("author-2", "Herman","Melville"));
        AUTHORS.add(new Author("author-3","Anne","Rice"));
    }
}
