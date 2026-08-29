package com.wexa.assignment;

public record DatabaseConfig(String uri, String username, String password) {
    public static DatabaseConfig fromEnvironment() {
        String uri = System.getenv().getOrDefault("NEO4J_URI", "bolt+s://db-416dd553.bravo.databases.cognodb.com");
        String username = System.getenv().getOrDefault("NEO4J_USERNAME", "cognodb");
        String password = System.getenv("NEO4J_PASSWORD");

        if (password == null || password.isBlank()) {
            throw new IllegalStateException("NEO4J_PASSWORD environment variable is not set.");
        }

        return new DatabaseConfig(uri, username, password);
    }
}
