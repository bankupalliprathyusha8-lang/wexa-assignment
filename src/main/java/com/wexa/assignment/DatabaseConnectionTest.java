package com.wexa.assignment;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;

public class DatabaseConnectionTest {
    public static boolean testConnection(String uri, String username, String password) {
        try (Driver driver = GraphDatabase.driver(uri, AuthTokens.basic(username, password))) {
            try (var session = driver.session()) {
                var result = session.run("RETURN 1 AS ok");
                return result.single().get("ok").asInt() == 1;
            }
        } catch (Exception e) {
            System.err.println("Database connection test failed: " + e.getMessage());
            return false;
        }
    }

    public static boolean testDefaultConnection(String password) {
        String uri = "bolt+s://db-416dd553.bravo.databases.cognodb.com";
        String username = "cognodb";
        return testConnection(uri, username, password);
    }
}
