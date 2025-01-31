You can access the H2 console at http://localhost:8080/h2-console (assuming your application runs on port 8080).

Note
This configuration uses an in-memory database, which means data will be lost when the application stops. For persistent storage, you can change the URL to jdbc:h2:file:./data/testdb to save the database to a file.