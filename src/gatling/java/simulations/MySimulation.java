package simulations;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.StringBody;
import static io.gatling.javaapi.core.CoreDsl.scenario;
import static io.gatling.javaapi.core.OpenInjectionStep.atOnceUsers;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

public class MySimulation extends Simulation {

    // Define the HTTP protocol configuration
    private final HttpProtocolBuilder httpProtocol = HttpDsl.http
        .baseUrl("http://localhost:8080"); // Base URL for the application

    // Define the scenario with all three requests combined
    private final ScenarioBuilder combinedScenario = CoreDsl.scenario("Combined Scenario")
        .exec(HttpDsl.http("Get All Employees")
            .get("/emp")
            .check(HttpDsl.status().is(200)))
        .pause(1) 
        .exec(HttpDsl.http("Get Employee By ID")
            .get("/emp/1") 
            .check(HttpDsl.status().is(200)))
        .pause(1) 
        .exec(HttpDsl.http("Create Employee")
            .post("/emp")
            .header("Content-Type", "application/json") // Set the content type
            .body(StringBody("{\"id\": 2, \"emp_Name\": \"John Doe\", \"emp_EmailId\": \"john.doe@example.com\", \"emp_Age\": 30}")) // Set the request body
            .check(HttpDsl.status().is(201))); // Check for successful creation

    {
        setUp(
            combinedScenario.injectOpen(CoreDsl.atOnceUsers(3)) // Number of users to simulate
        ).protocols(httpProtocol);
    }
}
