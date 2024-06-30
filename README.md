# PetStore

## Overview
PetStore is an advanced test automation project designed to validate the functionality of the Pet Store API. The project leverages RestAssured for API testing, TestNG for test execution, and integrates with TestRail and JIRA for comprehensive test management and defect tracking.

## Project Structure
The repository contains the following key directories and files:

### Root Directory
- **.settings**: Configuration settings for the Eclipse IDE.
- **Configuration**: Contains configuration files used for the test automation framework.
- **Logs**: Stores log files generated during test execution.
- **TestData**: Contains test data files used by the tests.
- **target**: Directory for compiled code and other build artifacts generated by Maven.
- **test-output**: Stores output from TestNG, including detailed HTML and XML test reports.
- **.classpath**: Eclipse classpath configuration file.
- **.project**: Eclipse project configuration file.
- **azure-pipeline.yaml**: Configuration file for setting up CI/CD pipelines in Azure.
- **docker-compose.yml**: Configuration file for setting up Docker containers.
- **jenkins-pipeline.txt**: Jenkins pipeline configuration script for CI/CD.
- **log4j2.xml**: Log4j2 configuration file for logging settings.
- **pom.xml**: Maven Project Object Model file for managing dependencies and build configurations.
- **testng.xml**: TestNG suite configuration file for defining test suites, groups, and parameters.

### src/test/java
#### api.endpoints
- **Routes**: Maintains all the base class URLs. This class centralizes the management of endpoints, making it easy to update and maintain the API endpoints across different environments.

#### api.payload
- Contains POJO (Plain Old Java Object) classes with getter and setter methods for each API. These classes represent the structure of the JSON payloads used in the API requests and responses.

#### api.utility
- **ExcelReader**: Utility class for reading data from Excel files.
- **ExtentManager**: Manages the ExtentReports configuration and report generation.
- **Log**: Utility class for logging detailed execution information using Log4j2.
- **Utils**: Contains custom utility functions for common actions such as extracting values from JSON responses, reading configurations, etc.
- **APIClient**, **APIException**, **TestRailManager**: Classes for integrating with TestRail. These classes provide methods to connect to TestRail, update test results, and manage test runs.
- **JiraIssueCreator**: Automates the creation of JIRA issues for failed test cases. It connects to JIRA using the provided credentials, creates a new issue with the relevant details, and logs the issue ID.
- **TestNGListener**: Implements TestNG listeners for updating test statuses in TestRail and logging test execution details.

#### api.test
- Contains all the scenario class files. These classes define the various test scenarios, each targeting specific functionalities of the Pet Store API. They use the endpoints defined in `api.endpoints.Routes` and the payloads in `api.payload` to send requests and validate responses.

## Prerequisites
- Java JDK 8 or higher
- Maven
- TestNG
- RestAssured
- TestRail (for test management integration)
- JIRA (for defect tracking integration)

## Setup and Installation

1. **Clone the Repository**:
   ```sh
   git clone https://github.com/maxxmuthu/PetStore.git
   cd PetStore
   
Install Dependencies mvn clean install

Running Tests Execute the following command to run the tests: 
mvn test

## Continuous Integration
## Azure Pipelines: The azure-pipeline.yaml file contains the necessary configuration for setting up a CI/CD pipeline in Azure DevOps.

## Jenkins: Use the jenkins-pipeline.txt file to configure your Jenkins pipeline for continuous integration and deployment.

## Docker: To start the services using Docker Compose: docker-compose up

## Logging Logs are configured using Log4j2 and stored in the Logs directory. The log4j2.xml file defines the logging levels and appenders.

## Test Data Test data is stored in the TestData directory and can be accessed using the ExcelReader utility class.

## Integration with TestRail and JIRA TestRail: The project includes classes (APIClient, APIException, TestRailManager) for integrating with TestRail. These classes help in updating the test case results in TestRail automatically after the test execution. JIRA: The JiraIssueCreator class automates the creation of JIRA issues for any test case failures, enabling seamless defect tracking.

## Detailed Explanation of Key Components
api.endpoints.Routes
The Routes class centralizes the management of API endpoints, making it easy to update and maintain the base URLs and paths across different environments. This class helps in maintaining clean and manageable code by avoiding hardcoding URLs in multiple places.

api.utility.Utils
The Utils class contains various custom utility functions, such as methods for extracting values from JSON responses, reading configuration files, and other common tasks. These utility methods help in reducing code duplication and improving code readability.

api.utility.ExtentManager
The ExtentManager class is responsible for configuring and managing the ExtentReports. It provides methods to initialize the report, create test nodes, and log the test execution details.

api.utility.TestRail Integration
The APIClient, APIException, and TestRailManager classes are designed for integrating with TestRail. They provide methods to connect to TestRail, update test results, and manage test runs. This integration helps in keeping track of the test case execution status in TestRail.

api.utility.JiraIssueCreator
The JiraIssueCreator class automates the process of raising defects in JIRA for any test case failures. It connects to JIRA using the provided credentials, creates a new issue with the relevant details, and logs the issue ID. This ensures that all failed test cases are properly tracked and assigned for resolution.

src/test/java
This directory contains all the test scenarios, organized in a way that makes it easy to add, modify, and manage tests. Each test class is designed to validate specific functionality of the Pet Store API, ensuring comprehensive test coverage.

Contributing
Contributions are welcome! Feel free to fork this repository, make your changes, and submit pull requests.

License
This project is licensed under the MIT License.
