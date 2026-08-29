package com.wexa.assignment;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;

public class App {
    public static void main(String[] args) {
        try {
            DatabaseConfig config = DatabaseConfig.fromEnvironment();
            System.out.println("Connecting to Neo4j at: " + config.uri());

            try (Driver driver = GraphDatabase.driver(config.uri(), AuthTokens.basic(config.username(), config.password()))) {
                GraphDataService service = new GraphDataService(driver);

                if (DatabaseConnectionTest.testConnection(config.uri(), config.username(), config.password())) {
                    System.out.println("Database connection successful.");
                }

                service.clearGraph();
                service.loadSampleData();
                service.printExampleQueries();
                service.printRecommendationFor("Java");
                System.out.println("\nAssignment-ready graph database project is running successfully.");
            }
        } catch (Exception e) {
            System.err.println("Application failed to start: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
