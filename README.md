# SpringBoot_Hibernate_Framework</h1>
### Bare metal SpringBoot application developed using below technologies:</h3>

## Table of contents
* [Tech stack](#tech-stack)
* [Pre-Requisite](#pre-requisite)
* [Running the Project](#running-the-project)
* [Input](#input)

## Tech Stack
<ul>
    <li>Spring Boot 2.6.4 </li>
    <li>Spring REST API</li>
    <li>Spring security with JWT Authentication</li>
    <li>Spring AOP</li>
    <li>Hibernate</li>
    <li>H2</li  >
</ul>

## Pre Requisite
<ul>
    <li>Maven</li>
    <li>Git</li>
    <li>IDE of your choice</li>
</ul>

## Running the project
<ol>
    <li>Open Terminal (in Linux/Mac) or cmd (in Windows)</li>
    <li>Navigate to project directory</li>
    <li>Run <pre><code>./mvnw install</code></pre></li>
    <li>Then, once done, run <pre><code>./mvnw spring-boot:run</code></pre></li>
</ol>

## Input
    {
        "firstName": "Test",
        "lastName": "Test",
        "email": "Test@example.com",
        "password": "Test@123",
        "matchingPassword": "Test@123",
        "dob": "1981-05-03",
        "gender": "F",
        "activeSw": true,
        "userRole": [
            {
                "roleName": "USER"
            }
        ]
    }
