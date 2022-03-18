# SpringBoot_Hibernate_Framework
<p>
    Bare metal SpringBoot application which can be used as base for creating new applications by adding into it. The application provides below capabilities out of the box.
</p>
    <ul>
        <li>Spring security</li>
        <ul>
            <li>
                Authentication using JWT tokens
            </li>
            <li>
                Custom Authorization and roles
            </li>
        </ul>
        <li>LogOut functionality</li>
        <li>SignUp functionality with account verification</li>
        <li>Successful and failure login recording which can be further increased</li>
        <li>Admin functionality</li>
        <ul>
            <li>Delete Users</li>
            <li>Unblock Deactivated users</li>
            <li>Hard delete users</li>
        </ul>
        <li>Password Management</li>
        <ul>
            <li>Reset password</li>
            <li>Forgot Password</li>
            <li>Generate forgot password token</li>
        </ul>
        <li>Address API</li>
            <ul>
                <li>Load Country, State/Region, City and zipcode list from csv file</li>
                <li>Fetch all details of country</li>
                <li>Fetch single country details</li>
                <li>Fetch all country names</li>
                <li>Fetch all region/state of a country</li>
                <li>Fetch all cities of a region/state</li>
                <li>Fetch based on country and zipcode</li>
            </ul>
    </ul>

## Table of contents
* [Tech stack](#tech-stack)
* [Pre-Requisite](#pre-requisite)
* [Running the Project](#running-the-project)
* [API usage](#api-usage)

## Tech Stack
<ul>
    <li>Spring Boot 2.6.4 </li>
    <li>Spring REST API</li>
    <li>Spring security with JWT Authentication</li>
    <li>Spring AOP</li>
    <li>Hibernate</li>
    <li>H2 Database</li  >
</ul>

## Pre Requisite
<ul>
    <li>Maven</li>
    <li>Git</li>
    <li>IDE</li>
</ul>

## Running the project
<ol>
    <li>Open Terminal (in Linux/Mac) or cmd (in Windows)</li>
    <li>Navigate to project directory</li>
    <li>Run <pre><code>./mvnw install</code></pre></li>
    <li>Then, once done, run <pre><code>./mvnw spring-boot:run</code></pre></li>
</ol>

## API usage
<ol>
    <li><h3>Login</h3></li>
    <b>Method:</b><code>POST</code><br>
    <b>URL:</b><code>/baseFw/api/login</code><br>
    <b>Payload:</b><pre><code>{
    "username": "test1@e.com", 
    "password": "T"
}</code></pre>
    <li><h3>User Module</h3></li>
        <ol>
            <li><h4>SignUp:-</h4></li>
            <b>Method:</b><code>POST</code><br>
            <b>URL:</b><code>/baseFw/api/v1/users/register</code><br>
            <b>Payload:</b>
            <pre><code>{
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
}</code></pre>
            <li><h4>Get User details:-</h4></li>
            <b>Method:</b><code>GET</code><br>
            <b>URL:</b><code>/baseFw/api/v1/users/{email}</code><br>
            <li><h4>Update User:-</h4></li>
            <b>Method:</b><code>PATCH</code><br>
            <b>URL:</b><code>/baseFw/api/v1/users</code><br>
        <pre><code>{
    "firstName": "Test",
    "lastName": "Something",
    "email": "test1@e.com",
    "dob": "2020-05-03"
}</code></pre>
            <li><h4>Delete user:-</h4></li>
            <b>Method:</b><code>DELETE</code><br>
            <b>URL:</b><code>/baseFw/api/v1/users/{email}</code><br><br>
            <li><h4>Activate User:-</h4></li>
            <b>Method:</b><code>PATCH</code><br>
            <b>URL:</b><code>/baseFw/api/v1/users/register/verify/{token}</code><br>
        </ol>
    <li><h3>Password Module</h3></li>
    <ol>
        <li><h4>Reset password</h4></li>
        <b>Method:</b><code>POST</code><br>
        <b>URL:</b><code>/baseFw/api/v1/password/reset</code><br>
        <b>Payload:</b><pre><code>{
    "password": "Test@123",
    "matchingPassword": "Test@123",
    "email": "Test@example.com"
}</code></pre>
        <li><h4>Generate forgot password token</h4></li>
        <b>Method:</b><code>POST</code><br>
        <b>URL:</b><code>/baseFw/api/v1/password/token/{email}</code><br>
        <li><h4>Forgot password</h4></li>
        <b>Method:</b><code>PATCH</code><br>
        <b>URL:</b><code>/baseFw/api/v1/password/forgot/{token}</code><br>
    </ol>
    <li><h3>Admin Module</h3></li>
    <ol>
        <li><h4>Get all users:-</h4></li>
        <b>Method:</b><code>GET</code><br>
        <b>URL:</b><code>/baseFw/api/v1/admin/users</code><br>
        <li><h4>Unlock user accounts:-</h4></li>
        <b>Method:</b><code>PATCH</code><br>
        <b>URL:</b><code>/baseFw/api/v1/admin/account/unlock</code><br>
        <b>Payload:</b><pre><code>[ 
    "test3@e.com", 
    "test4@e.com" 
]</code></pre>
        <li><h4>Delete Users:-</h4></li>
        <b>Method:</b><code>DELETE</code><br>
        <b>URL:</b><code>/baseFw/api/v1/admin/account/delete</code><br>
        <b>Payload:</b><pre><code>[ 
    "test3@e.com", 
    "test4@e.com" 
]</code></pre>
        <li><h4>Hard Delete:-</h4></li>
        <b>Method:</b><code></code><br>
        <b>URL:</b><code>/baseFw/api/v1/admin/account/hardDelete</code><br>
        <b>Payload:</b><pre><code>[ 
    "test3@e.com", 
    "test4@e.com" 
]</code></pre>
    </ol>
    <li><h3>Address Module</h3></li>
    <ol>
        <li><h4>All Countries:-</h4></li>
        <b>Method:</b><code>GET</code><br>
        <b>URL:</b><code>/baseFw/api/v1/address/admin/country</code><br>
        <li><h4>All country names:-</h4></li>
        <b>Method:</b><code>GET</code><br>
        <b>URL:</b><code>/baseFw/api/v1/address/country</code><br>
        <li><h4>Country:-</h4></li>
        <b>Method:</b><code>GET</code><br>
        <b>URL:</b><code>/baseFw/api/v1/address/country/{countryId}</code><br>
        <li><h4>All states data of a country:-</h4></li>
        <b>Method:</b><code>GET</code><br>
        <b>URL:</b><code>/baseFw/api/v1/address/country/{countryId}/states</code><br>
        <li><h4>State data for a country:-</h4></li>
        <b>Method:</b><code>GET</code><br>
        <b>URL:</b><code>/baseFw/api/v1/address/country/{countryId}/states/{stateId}</code><br>
        <li><h4>All cities data for state of a country:-</h4></li>
        <b>Method:</b><code>GET</code><br>
        <b>URL:</b><code>/baseFw/api/v1/address/country/{countryId}/states/{stateId}/cities</code><br>
        <li><h4>A city data for state of a country:-</h4></li>
        <b>Method:</b><code>GET</code><br>
        <b>URL:</b><code>/baseFw/api/v1/address/country/{countryId}/states/{stateId}/cities/{cityId}</code><br>
        <li><h4>City data based on zipcode:-</h4></li>
        <b>Method:</b><code>GET</code><br>
        <b>URL:</b><code>/baseFw/api/v1/address/country/{countryId}/zipcode/{zipcode}</code><br>
    </ol>
</ol>
    
