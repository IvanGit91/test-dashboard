# Test Results Dashboard

A comprehensive web-based dashboard for collecting, visualizing, and managing test results. This legacy application, originally developed in 2017, provides an intuitive interface for tracking test execution statistics, performance metrics, and detailed test reports across multiple projects and environments.

## 📋 Table of Contents

- [About the Project](#about-the-project)
- [Features](#features)
- [Architecture](#architecture)
- [Technology Stack](#technology-stack)
- [Prerequisites](#prerequisites)
- [Installation & Setup](#installation--setup)
- [Configuration](#configuration)
- [Building the Application](#building-the-application)
- [Running the Application](#running-the-application)
- [API Documentation](#api-documentation)
- [Mock Data Structure](#mock-data-structure)
- [Extending to Real Services](#extending-to-real-services)
- [Contributing](#contributing)
- [License](#license)

## About the Project

The Test Results Dashboard is a Spring Boot-based web application that serves as a centralized platform for test result management. Originally designed as a legacy system in 2017, it combines the robustness of Spring Boot with the dynamic frontend capabilities of AngularJS to provide a seamless user experience.

### Purpose and Scope

This dashboard is designed to:

- **Centralize Test Data**: Aggregate test results from multiple testing environments and frameworks
- **Visualize Performance**: Display comprehensive statistics, charts, and performance metrics
- **Track Progress**: Monitor test execution trends over time across different projects
- **Generate Reports**: Create detailed PDF reports for test analysis and documentation
- **Manage Projects**: Organize tests by projects, targets, and configurations
- **Support Multiple Environments**: Handle different testing environments and system configurations

### Key Use Cases

- **Quality Assurance Teams**: Track test execution results across sprint cycles
- **Development Teams**: Monitor code quality and regression testing outcomes  
- **Project Managers**: Generate comprehensive testing reports for stakeholders
- **DevOps Teams**: Integrate with CI/CD pipelines for automated test reporting
- **Test Engineers**: Analyze performance metrics and identify bottlenecks

## Features

### Core Functionality

- ✅ **Multi-Project Support**: Organize tests across different projects and targets
- ✅ **Real-time Statistics**: Live dashboard with test execution metrics
- ✅ **Interactive Charts**: Visual representation of test results and trends
- ✅ **PDF Report Generation**: Export detailed test reports in PDF format
- ✅ **Performance Tracking**: Monitor test execution performance over time
- ✅ **Detailed Test Analysis**: Drill down into individual test results and logs
- ✅ **System Under Test (SUT) Management**: Configure and manage test environments
- ✅ **User Authentication**: Secure access with Spring Security integration
- ✅ **Responsive Design**: AngularJS-powered responsive web interface

### Data Management

- 📊 **Test Statistics**: Pass/fail ratios, execution times, and trend analysis
- 🎯 **Target Management**: Different testing environments and configurations
- 📈 **Performance Metrics**: Execution time tracking and performance analysis
- 📄 **Report Details**: Comprehensive test execution details and logs
- 🔄 **Data Export**: CSV and PDF export capabilities

## Architecture

### System Architecture

The application follows a traditional MVC architecture pattern with the following layers:

```
┌─────────────────────────────────────────┐
│           Frontend (AngularJS)          │
├─────────────────────────────────────────┤
│        REST Controllers (Spring)        │
├─────────────────────────────────────────┤
│         Service Layer (Spring)          │
├─────────────────────────────────────────┤
│      Data Access Layer (Spring JPA)     │
├─────────────────────────────────────────┤
│       Database (PostgreSQL/Mock)        │
└─────────────────────────────────────────┘
```

### Key Components

#### DashboardStubController
The heart of the application's data layer, `DashboardStubController` provides a flexible architecture that:

- **Mock Data Integration**: Loads static JSON data from `src/main/resources/mockdata/` for demonstration and development
- **Extensible Design**: Can be easily modified to connect to external test result services or internal databases
- **RESTful APIs**: Provides standardized REST endpoints for frontend consumption
- **Error Handling**: Comprehensive error handling and logging for debugging

#### MockDataLoader Service
Handles the loading and caching of mock test data:

- **Performance Optimization**: Caches loaded data to improve response times
- **JSON Processing**: Handles complex nested JSON structures for test data
- **Data Transformation**: Converts JSON data to appropriate Java DTOs

#### Frontend Architecture
AngularJS-based single-page application featuring:

- **Modular Design**: Organized into controllers, directives, and services
- **Responsive Layout**: Bootstrap-based responsive design
- **Interactive Charts**: D3.js/Chart.js integration for data visualization
- **User Experience**: Intuitive navigation and real-time updates

## Technology Stack

### Backend
- **Java 8**: Core programming language
- **Spring Boot 1.5.13**: Main framework for REST APIs and application structure
- **Spring Security**: Authentication and authorization
- **Spring Data JPA**: Data persistence layer
- **PostgreSQL**: Production database (configurable)
- **Maven**: Build and dependency management
- **Lombok**: Code generation for DTOs

### Frontend
- **AngularJS**: Frontend JavaScript framework
- **HTML5/CSS3**: Markup and styling
- **Bootstrap**: Responsive design framework
- **JavaScript**: Client-side scripting

### Additional Libraries
- **Jackson/Gson**: JSON processing
- **iText PDF**: PDF report generation
- **Apache Commons**: Utility libraries
- **SLF4J/Logback**: Logging framework

## Prerequisites

Before setting up the project, ensure you have the following installed:

- **Java Development Kit (JDK) 8** or higher
- **Apache Maven 3.6+** for build management
- **PostgreSQL 12+** (optional, for production database)
- **Git** for version control
- **Node.js and npm** (optional, for frontend development tools)

### System Requirements

- **Memory**: Minimum 2GB RAM (4GB recommended)
- **Storage**: At least 500MB free disk space
- **OS**: Windows, macOS, or Linux
- **Browser**: Modern web browser (Chrome, Firefox, Safari, Edge)

## Installation & Setup

### 1. Clone the Repository

```bash
git clone <repository-url>
cd test-dashboard
```

### 2. Verify Java Installation

```bash
java -version
javac -version
```

Expected output should show Java 8 or higher.

### 3. Verify Maven Installation

```bash
mvn -version
```

### 4. Install Dependencies

```bash
mvn clean install
```

This command will:
- Download all required dependencies
- Compile the Java source code
- Run unit tests (if available)
- Package the application

## Configuration

### Application Properties

The application uses Spring Boot's configuration system with profile-based properties:

#### Main Configuration (`application.properties`)

The main configuration file contains:
- **Server Configuration**: Port (8081), session settings
- **File Upload Settings**: Maximum file sizes for multipart uploads
- **Security Settings**: Authentication and session management
- **Scheduler Configuration**: Background task settings

#### Development Configuration

1. **Copy the example configuration**:
   ```bash
   cp src/main/resources/application-dev.properties.example src/main/resources/application-dev.properties
   ```

2. **Edit the development configuration** (`application-dev.properties`):
   ```properties
   # Database Configuration (if using PostgreSQL)
   datasource.driver.classname=org.postgresql.Driver
   datasource.connection.url=jdbc:postgresql://localhost:5432/dashboard
   datasource.credential.user=your_username
   datasource.credential.password=your_password
   
   # Hibernate Settings
   hibernate.show_sql=true
   hibernate.hbm2ddl.auto=update
   
   # Logging Configuration
   logging.path=./log/
   logging.pattern.console=%-5level ---> %d{HH:mm:ss} [%line] [%thread] %logger{36} - %msg%n
   ```

### Database Setup (Optional)

#### For Mock Data (Default)
No database setup required. The application will use JSON files from `src/main/resources/mockdata/`.

#### For PostgreSQL Database
1. **Install PostgreSQL** and create a database:
   ```sql
   CREATE DATABASE dashboard;
   CREATE USER dashboard_user WITH ENCRYPTED PASSWORD 'your_password';
   GRANT ALL PRIVILEGES ON DATABASE dashboard TO dashboard_user;
   ```

2. **Update configuration** in `application-dev.properties` with your database credentials.

### Environment Variables (Optional)

You can override configuration using environment variables:

```bash
export SERVER_PORT=8082
export DB_URL=jdbc:postgresql://localhost:5432/dashboard
export DB_USERNAME=your_username
export DB_PASSWORD=your_password
```

## Building the Application

### Development Build

```bash
# Clean and compile
mvn clean compile

# Run tests
mvn test

# Package without tests
mvn clean package -DskipTests
```

### Production Build

```bash
# Full build with tests
mvn clean package

# Build with specific profile
mvn clean package -Pproduction
```

### Build Output

After successful build, you'll find:
- **JAR file**: `target/Dashboard_1.0.0.jar`
- **Classes**: `target/classes/`
- **Test results**: `target/surefire-reports/`

## Running the Application

### Method 1: Using Maven

```bash
# Run with default profile
mvn spring-boot:run

# Run with development profile
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

### Method 2: Using JAR File

```bash
# Build first
mvn clean package -DskipTests

# Run the JAR
java -jar target/Dashboard_1.0.0.jar

# Run with specific profile
java -jar target/Dashboard_1.0.0.jar --spring.profiles.active=dev
```

### Method 3: IDE Integration

Import the project into your preferred IDE (IntelliJ IDEA, Eclipse, VS Code) and run the `DashboardMain.java` class.

### Application Access

Once started, access the application at:
- **URL**: http://localhost:8081
- **Health Check**: http://localhost:8081/health
- **API Base**: http://localhost:8081/api (for REST endpoints)

## API Documentation

The application provides RESTful APIs for accessing test data. All endpoints return JSON responses.

### Core Endpoints

#### Projects Management

```bash
# Get all projects
GET /projects
```

**Response Example**:
```json
[
  {
    "id": 1,
    "name": "Mobile App Testing",
    "description": "Automated tests for mobile application"
  }
]
```

#### Targets Management

```bash
# Get targets for a specific project
GET /targets/{projectId}
```

**Response Example**:
```json
[
  {
    "id": 1,
    "type": "Android",
    "name": "Samsung Galaxy S10",
    "variant": "API Level 29",
    "revision": "10.0",
    "release": "Q",
    "description": "Android device testing",
    "created": "2023-01-15T10:30:00Z"
  }
]
```

#### Statistics

```bash
# Get statistics for project, target, and level
GET /statistics/{projectId}/{targetId}/{level}
```

**Response Example**:
```json
{
  "totalTest": 150,
  "clusters": [
    {
      "name": "UI Tests",
      "total": 50,
      "pass": [...],
      "fail": [...],
      "running": [...],
      "noRun": [...]
    }
  ],
  "reportGroup": {
    "passed": 120,
    "failed": 20,
    "skipped": 10
  }
}
```

#### Test Reports

```bash
# Get reports for project and target
GET /reports/{projectId}/{targetId}

# Get detailed report information
GET /reportDetails/{reportId}
```

#### Performance Data

```bash
# Get performance test results
GET /testsPerformance/{projectId}/{targetId}
```

#### SUT Configurations

```bash
# Get available System Under Test configurations
GET /availablesuts
```

#### Utility Endpoints

```bash
# Health check
GET /health

# Clear cache (development)
POST /clear-cache

# Generate PDF from JSON
POST /jsonToPdf
Content-Type: application/json
Body: {...}

# Generate detailed report PDF
POST /reportDetailToPdf
Content-Type: application/json
Body: {"id": reportId, ...}
```

## Mock Data Structure

The application uses structured JSON files for mock data, located in `src/main/resources/mockdata/`:

### File Structure

```
mockdata/
├── projects.json          # Project definitions
├── targets.json           # Target environments per project  
├── statistics.json        # Test execution statistics
├── reports.json           # Test report summaries
├── reportdetails.json     # Detailed test results
├── testperformance.json   # Performance metrics
└── sutconfigurations.json # System configurations
```

### Data Relationships

```
Projects (1) ──→ (N) Targets ──→ (N) Reports ──→ (1) ReportDetails
    │                                │
    └──────→ (N) TestPerformance ────┘
```

### Customizing Mock Data

To modify test data:

1. **Edit JSON files** in `src/main/resources/mockdata/`
2. **Maintain data structure** consistency
3. **Clear cache** using `/clear-cache` endpoint
4. **Restart application** for changes to take effect

### Example Data Structure

**projects.json**:
```json
[
  {
    "id": 1,
    "name": "Project Alpha",
    "description": "Main application testing suite"
  }
]
```

**targets.json**:
```json
{
  "1": [
    {
      "id": 1,
      "type": "Web",
      "name": "Chrome Browser",
      "variant": "Desktop",
      "revision": "91.0",
      "release": "Stable",
      "description": "Chrome desktop testing",
      "created": "2023-01-01T00:00:00 UTC"
    }
  ]
}
```

## Extending to Real Services

The `DashboardStubController` is designed to be easily extended for production use:

### Option 1: Database Integration

Replace mock data loading with JPA repositories:

```java
@RestController
public class DashboardController {
    
    @Autowired
    private ProjectRepository projectRepository;
    
    @GetMapping("/projects")
    public ResponseEntity<List<Project>> getProjects() {
        List<Project> projects = projectRepository.findAll();
        return ResponseEntity.ok(projects);
    }
}
```

### Option 2: External Service Integration

Connect to external test result services:

```java
@Service
public class TestResultService {
    
    @Autowired
    private RestTemplate restTemplate;
    
    public List<Project> getProjectsFromExternalService() {
        return restTemplate.getForObject(
            "http://external-service/api/projects", 
            Project[].class
        );
    }
}
```

### Option 3: Hybrid Approach

Combine database storage with external service calls:

```java
@Service
public class HybridDataService {
    
    public List<Project> getProjects() {
        // Try cache first
        List<Project> cached = cacheService.getProjects();
        if (cached != null) return cached;
        
        // Try database
        List<Project> fromDb = projectRepository.findAll();
        if (!fromDb.isEmpty()) return fromDb;
        
        // Fall back to external service
        return externalService.getProjects();
    }
}
```

### Migration Strategy

1. **Phase 1**: Keep mock controller alongside new implementation
2. **Phase 2**: Add feature flags to switch between data sources  
3. **Phase 3**: Gradually migrate endpoints to new implementation
4. **Phase 4**: Remove mock controller and data files

## Contributing

We welcome contributions to improve the Test Results Dashboard! Please follow these guidelines:

### Development Setup

1. Fork the repository
2. Create a feature branch: `git checkout -b feature/amazing-feature`
3. Make your changes
4. Add tests for new functionality
5. Ensure all tests pass: `mvn test`
6. Commit your changes: `git commit -m 'Add amazing feature'`
7. Push to the branch: `git push origin feature/amazing-feature`
8. Open a Pull Request

### Code Style

- Follow Java coding conventions
- Use meaningful variable and method names
- Add JavaDoc comments for public methods
- Maintain consistent indentation (4 spaces)
- Keep methods focused and small

### Testing

- Write unit tests for new functionality
- Ensure integration tests pass
- Test with different browser configurations
- Verify responsive design works properly

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

**Note**: This is a legacy application originally developed in 2017. While it provides solid functionality for test result management, consider modernizing the technology stack (Spring Boot 2.x+, Angular 12+) for new projects or major updates.

For questions, issues, or feature requests, please open an issue in the repository or contact the development team.