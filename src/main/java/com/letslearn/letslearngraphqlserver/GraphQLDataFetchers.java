package com.letslearn.letslearngraphqlserver;

import com.letslearn.letslearngraphqlserver.model.Author;
import com.letslearn.letslearngraphqlserver.model.Book;
import com.letslearn.letslearngraphqlserver.model.Repository;
import graphql.schema.DataFetcher;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

@Component
public class GraphQLDataFetchers {

    public DataFetcher getBooks() {
        return dataFetchingEnvironment ->
                Repository.BOOKS;
    }

    public DataFetcher getBookById() {
        return dataFetchingEnvironment -> {
            final String bookId = dataFetchingEnvironment.getArgument("id");
            return Repository.BOOKS
                    .stream()
                    .filter(book -> book.getId().equals(bookId))
                    .findFirst().orElse(null);
        };
    }

    public DataFetcher getAuthors() {
        return dataFetchingEnvironment ->
                Repository.AUTHORS;

    }

    public DataFetcher getAuthorById() {
        return dataFetchingEnvironment -> {
            String id = dataFetchingEnvironment.getArgument("id");
            return Repository.AUTHORS
                    .stream()
                    .filter(author -> id.equals(author.getId()))
                    .findFirst().orElse(null);
        };
    }

    public DataFetcher getTitleDataFetcher() {
        return dataFetchingEnvironment -> {
            Book book = dataFetchingEnvironment.getSource();
            return book.getName();
        };
    }

    public DataFetcher getAuthorDataFetcher() {
        return dataFetchingEnvironment -> {
            Book book = dataFetchingEnvironment.getSource();
            return Repository.AUTHORS
                    .stream()
                    .filter(author -> author.getId().equals(book.getAuthorId()))
                    .findFirst().orElse(null);
        };
    }

    public DataFetcher getBooksForAnAuthor() {
        return dataFetchingEnvironment -> {
            Author author = dataFetchingEnvironment.getSource();
            return Repository.BOOKS
                    .stream()
                    .filter(book -> book.getAuthorId().equals(author.getId())).collect(Collectors.toList());
        };
    }

    public DataFetcher createBook() {
        return dataFetchingEnvironment -> {
            String id = dataFetchingEnvironment.getArgument("id");
            String title = dataFetchingEnvironment.getArgument("title");
            int pageCount = dataFetchingEnvironment.getArgument("pageCount");
            String authorId = dataFetchingEnvironment.getArgument("authorId");
            Book book = new Book(id, title, pageCount, authorId);

            Repository.BOOKS.add(book);
            return book;
        };
    }

    public DataFetcher deleteBook() {
        return dataFetchingEnvironment -> {
            String id = dataFetchingEnvironment.getArgument("id");
            Book book1 = Repository.BOOKS
                    .stream()
                    .filter(book -> book.getId().equals(id))
                    .findFirst().orElse(null);

            Repository.BOOKS.remove(book1);
            return "Deleted book: " + id;
        };
    }
}
