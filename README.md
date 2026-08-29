# Wexa AI Assignment - Neo4j Graph Database Project

This project is a Java 17 Maven application built for a graph database assignment using the Neo4j Java Driver.

## Why a graph database?

This use case is a learning and mentoring platform. The domain naturally forms a graph:

- Users can enroll in courses
- Courses cover skills
- Users have interests and expertise
- Mentors teach courses
- Courses have prerequisite relationships

A relational model would require many joins and repeated table lookups. In a graph database, these relationships are first-class citizens, making recommendations, pathway discovery, and skill mapping much easier to explore and explain.

## Data model

- `User` nodes
- `Course` nodes
- `Skill` nodes
- relationships such as `ENROLLED_IN`, `COVERS`, `INTERESTED_IN`, `HAS_EXPERTISE`, `TEACHES`, and `PREREQ_FOR`

## Example data

This project seeds sample users, courses, and skill relationships in the graph so you can test traversal queries quickly.

## Configuration

Set these environment variables before running the app:

```bash
NEO4J_URI=bolt+s://db-416dd553.bravo.databases.cognodb.com
NEO4J_USERNAME=cognodb
NEO4J_PASSWORD=your-password
```

## Run the app

```bash
mvn clean compile
mvn exec:java -Dexec.mainClass=com.wexa.assignment.App
```

## Notes

- This project is ready for assignment logic and extension.
- Keep credentials in environment variables and never commit them to source control.
