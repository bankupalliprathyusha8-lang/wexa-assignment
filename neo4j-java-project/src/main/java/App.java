import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;

public class App {
    public static void main(String[] args) {
        String uri = System.getenv().getOrDefault("NEO4J_URI", "bolt://localhost:7687");
        String username = System.getenv().getOrDefault("NEO4J_USERNAME", "neo4j");
        String password = System.getenv().getOrDefault("NEO4J_PASSWORD", "password");

        System.out.println("Testing Neo4j connection...");
        System.out.println("URI: " + uri);
        System.out.println("Username: " + username);

        try (Driver driver = GraphDatabase.driver(uri, AuthTokens.basic(username, password))) {
            try (var session = driver.session()) {
                var result = session.run("RETURN 1 AS ok");
                int value = result.single().get("ok").asInt();
                System.out.println("Connection successful. Test query result: " + value);
            }
        } catch (Exception e) {
            System.err.println("Connection failed. Please check that Neo4j is running and the environment variables are set.");
            e.printStackTrace();
        }
    }
}
