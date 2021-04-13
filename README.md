# Framework using Springboot as backend, Spring security for authentication and authorization and Hibernate for Persistence

# Input
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

# Running the project
1. Goto project folder
2. ./mvnw install
3. ./mvnw spring-boot:run
