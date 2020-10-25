# letslearn-graphql-server
This is an Springboot Java application to learn how to build GraphQL server in Java

### Building docker image of this project
docker build . -t letslearn-graphql-server

### Running docker image of this project
docker run -p 8082:8080 letslearn-graphql-server

### Browsing GraphQL Server
http://localhost:8082/graphql

### Sample Graphql queries
```
{
 getBookById(id: "book-3") {
    id
    title
    pageCount
    author {
        firstName
        lastName
    }
  } 
}
```
```
{
  getBooks {
    id,
    title,
    pageCount,
    author {
        firstName
        lastName
      books {
        id
      }
    }
  }
}
```
```
mutation createBook {
  createBook(id: "book-5", title: "Battling Injustice", pageCount:60, authorId: "author-1") {
    id,
    title
  }
}
```
```
mutation deleteBook {
  deleteBook(id: "book-1")
}
```
```
{
getAuthors {
    id,
    firstName,
    lastName,
    books {
        title,
        pageCount
    }
  } 
}
```
