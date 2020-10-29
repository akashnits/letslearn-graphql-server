package com.letslearn.letslearngraphqlserver;


import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class GraphQLProvider {
    private GraphQL graphQL;

    @Value("file:src/main/resources/schema/*.graphqls")
    private Resource[] schemaResources;

    @Bean
    public GraphQL graphQL() {
        return graphQL;
    }

    @Autowired
    private GraphQLDataFetchers graphQLDataFetchers;

    @PostConstruct
    public void init() {
        final List<File> schemas = Arrays.stream(schemaResources).filter(Resource::isFile).map(resource -> {
            try {
                return resource.getFile();
            } catch (IOException ex) {
                throw new RuntimeException("Unable to load schema files");
            }
        }).collect(Collectors.toList());

        final GraphQLSchema graphQLSchema = buildSchema(schemas);

        this.graphQL = GraphQL
                .newGraphQL(graphQLSchema)
                .build();
    }

    private GraphQLSchema buildSchema(final List<File> schemas) {
        final SchemaParser schemaParser = new SchemaParser();
        final SchemaGenerator schemaGenerator = new SchemaGenerator();
        final TypeDefinitionRegistry typeDefinitionRegistry = new TypeDefinitionRegistry();

        for (final File schema : schemas) {
            typeDefinitionRegistry.merge(schemaParser.parse(schema));
        }

        final RuntimeWiring runtimeWiring = buildWiring();
        return schemaGenerator.makeExecutableSchema(typeDefinitionRegistry, runtimeWiring);
    }

    private RuntimeWiring buildWiring() {
        return RuntimeWiring.newRuntimeWiring()
                .type(TypeRuntimeWiring.newTypeWiring("Query").dataFetcher("getBooks", graphQLDataFetchers.getBooks()))
                .type(TypeRuntimeWiring.newTypeWiring("Query").dataFetcher("getBookById", graphQLDataFetchers.getBookById()))
                .type(TypeRuntimeWiring.newTypeWiring("Query").dataFetcher("getAuthors", graphQLDataFetchers.getAuthors()))
                .type(TypeRuntimeWiring.newTypeWiring("Query").dataFetcher("getAuthorById", graphQLDataFetchers.getAuthorById()))
                .type(TypeRuntimeWiring.newTypeWiring("Book").dataFetcher("title", graphQLDataFetchers.getTitleDataFetcher()))
                .type(TypeRuntimeWiring.newTypeWiring("Book").dataFetcher("author", graphQLDataFetchers.getAuthorDataFetcher()))
                .type(TypeRuntimeWiring.newTypeWiring("Author").dataFetcher("books", graphQLDataFetchers.getBooksForAnAuthor()))
                .type(TypeRuntimeWiring.newTypeWiring("Author").dataFetcher("fullName", graphQLDataFetchers.getAuthorFullName()))
                .type(TypeRuntimeWiring.newTypeWiring("Mutation").dataFetcher("createBook", graphQLDataFetchers.createBook()))
                .type(TypeRuntimeWiring.newTypeWiring("Mutation").dataFetcher("deleteBook", graphQLDataFetchers.deleteBook()))
                .build();
    }
}
