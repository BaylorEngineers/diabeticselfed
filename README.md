# README for DiabeticSelfEd Spring Boot Project

## Introduction
Welcome to the DiabeticSelfEd project, a Spring Boot application designed for diabetic self-education. This guide provides instructions for cloning, setting up, and running the project.

### Prerequisites
- Java JDK 17
- Maven (for building and running the project)
- IntelliJ IDEA (optional, for running via IDE)

## Cloning the Project
1. Open a terminal or command prompt.
2. Navigate to the directory where you want to clone the project.
3. Run the following command to clone the repository:
   git clone https://github.com/BaylorEngineers/diabeticselfed

## Setting Up the Project
1. ## Setting Up PostgreSQL Database

### Step 1: Install PostgreSQL
Ensure that PostgreSQL is installed on your system. You can download it from [the official PostgreSQL website](https://www.postgresql.org/download/).

### Step 2: Create a New Database
Use the PostgreSQL command line or a GUI tool like pgAdmin to create a new database.

### Step 3: Update `application.yml`
Open the `application.yml` file in the DiabeticSelfEd project directory and update the database configuration section:

```yaml
spring:
   datasource:
      url: jdbc:postgresql://localhost:5432/your_database_name
      username: your_username
      password: your_password
```

- Replace your_database_name with the name of the database you created.
- Replace your_username and your_password with your PostgreSQL credentials.

### Step 4: Update the `application.properties` file with your ChatGPT API key:
openai.api.key = "Shared via Slack Message"


### Running from Terminal
1. Use Maven to run the project directly:
   ```mvn spring-boot:run```

### Importing and Running in IntelliJ IDEA
1. Open IntelliJ IDEA and select `File > Open`.
2. Choose the project directory and click `Open`.
3. Once the project is loaded, click on `Run > Run 'Application'`.

## Testing the Application
- For testing purposes, use the following credentials:
- Admin User Login: `admin@mail.com`
- Clinician User Login: `clinician@mail.com`
- Then with the admin, please invite patient user via account manager.

## Conclusion
Follow these instructions to get the DiabeticSelfEd Spring Boot project up and running. For further assistance, refer to the project's documentation or contact the support team.
