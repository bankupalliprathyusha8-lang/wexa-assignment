package com.wexa.assignment;

import org.neo4j.driver.Driver;
import org.neo4j.driver.Record;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;

import java.util.List;

public class GraphDataService {
    private final Driver driver;

    public GraphDataService(Driver driver) {
        this.driver = driver;
    }

    public void clearGraph() {
        try (Session session = driver.session()) {
            session.run("MATCH (n) DETACH DELETE n");
        }
    }

    public void loadSampleData() {
        String cypher = """
            CREATE
              (u1:User {name: 'Aisha', role: 'Student'}),
              (u2:User {name: 'Daniel', role: 'Student'}),
              (u3:User {name: 'Priya', role: 'Mentor'}),

              (c1:Course {title: 'Java Fundamentals', level: 'beginner'}),
              (c2:Course {title: 'Graph Databases', level: 'intermediate'}),
              (c3:Course {title: 'AI for Developers', level: 'advanced'}),

              (s1:Skill {name: 'Java'}),
              (s2:Skill {name: 'Neo4j'}),
              (s3:Skill {name: 'Graph Theory'}),
              (s4:Skill {name: 'AI'}),

              (u1)-[:ENROLLED_IN]->(c1),
              (u2)-[:ENROLLED_IN]->(c2),
              (u3)-[:TEACHES]->(c2),

              (c1)-[:COVERS]->(s1),
              (c2)-[:COVERS]->(s2),
              (c2)-[:COVERS]->(s3),
              (c3)-[:COVERS]->(s4),

              (u1)-[:INTERESTED_IN]->(s1),
              (u1)-[:INTERESTED_IN]->(s2),
              (u2)-[:INTERESTED_IN]->(s3),
              (u2)-[:INTERESTED_IN]->(s4),
              (u3)-[:HAS_EXPERTISE]->(s2),
              (u3)-[:HAS_EXPERTISE]->(s4),

              (c1)-[:PREREQ_FOR]->(c2),
              (c2)-[:PREREQ_FOR]->(c3)
            """;

        try (Session session = driver.session()) {
            session.run(cypher);
        }
    }

    public void printExampleQueries() {
        System.out.println("\n=== Graph database example queries ===");

        String[] queries = {
            "MATCH (u:User)-[:ENROLLED_IN]->(c:Course) RETURN u.name, c.title ORDER BY u.name",
            "MATCH (c:Course)-[:COVERS]->(s:Skill) RETURN c.title, collect(s.name) AS skills ORDER BY c.title",
            "MATCH path = (u:User)-[:INTERESTED_IN]->(s:Skill)<-[:COVERS]-(c:Course) RETURN u.name, c.title, s.name ORDER BY u.name"
        };

        for (String query : queries) {
            System.out.println("\nQuery: " + query);
            try (Session session = driver.session()) {
                Result result = session.run(query);
                List<Record> records = result.list();
                for (Record record : records) {
                    System.out.println(record.asMap());
                }
            }
        }
    }

    public void printRecommendationFor(String skillName) {
        String query = """
            MATCH (u:User)-[:INTERESTED_IN]->(s:Skill)
            WHERE s.name = $skillName
            MATCH (u)-[:ENROLLED_IN]->(c:Course)-[:COVERS]->(matchedSkill:Skill)
            WHERE matchedSkill.name = $skillName
            RETURN DISTINCT u.name AS userName, c.title AS courseTitle
            ORDER BY userName
            """;

        System.out.println("\nRecommended training path for skill: " + skillName);
        try (Session session = driver.session()) {
            Result result = session.run(query, java.util.Map.of("skillName", skillName));
            result.list().forEach(record -> System.out.println(record.asMap()));
        }
    }
}
