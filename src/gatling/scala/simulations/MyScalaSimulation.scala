package simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class MyScalaSimulation extends Simulation {

  // Define the HTTP protocol configuration
  val httpProtocol = http
    .baseUrl("http://localhost:8080") // Base URL for the application
    .acceptHeader("application/json") // Accept header

  // Scenario definition
  val scn = scenario("My Combined Scala Scenario")
    .exec(
      // GET Employees
      http("Get Employees")
        .get("/emp") 
        .check(status.is(200)) 
    )
    .pause(2) // Pause between requests
    .exec(
      // GET Employee by ID (assuming ID 1)
      http("Get Employee by ID")
        .get("/emp/1") 
        .check(status.is(200)) 
    )
    .pause(2) // Pause between requests
    .exec(
      // POST Create Employee
      http("Create Employee")
        .post("/emp") 
        .header("Content-Type", "application/json") // Set content type to JSON
        .body(
          StringBody("""{
                       "emp_Name": "John Doe",
                       "emp_EmailId": "john.doe@example.com",
                       "emp_Age": 30
                     }""")
        ) // Employee details in JSON format
        .check(status.is(201)) 
    )
    

  // Simulation setup
  setUp(
    scn.inject(
      atOnceUsers(2) // Number of users to simulate at once
    )
  ).protocols(httpProtocol)
}
