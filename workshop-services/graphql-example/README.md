1. Run the project
2. Access the URL http://localhost:8080/graphiql
3. Try a GraphQL query, example:
```
query {
    recentFood(count: 3, offset: 0) {
        id
        title
        category
        restaurant {
            id
            name
            thumbnail
        }
    }
}
```