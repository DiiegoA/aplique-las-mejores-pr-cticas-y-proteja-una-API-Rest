# Med Voll API

## Description
The **Med Voll API** is a comprehensive Java-based application built with Spring Boot, designed to streamline the management of medical professionals and patients. This application supports secure authentication, role-based access control, and robust CRUD operations for managing doctors, patients, and associated data such as addresses. With its modular architecture and service-oriented design, the API ensures scalability, maintainability, and seamless integration with external systems. 

Key features include token-based authentication, advanced error handling, and dynamic role management, making it suitable for real-world healthcare systems.

## Core Capabilities:
- **Doctor Management**: Handles operations such as creation, updates, logical deletions, and listing of doctors, with validations for essential fields like email, phone, and specialty.
- **Patient Management**: Provides functionality for registering, updating, and managing patient details, ensuring data integrity and logical deletion.
- **Authentication and Authorization**: Offers secure login with JWT-based tokens and role-based access control for administrators, doctors, and patients.
- **Address Management**: Embedded support for managing addresses associated with doctors and patients.
- **Global Exception Handling**: Ensures robust error management with meaningful messages for validation errors, authentication failures, and other runtime exceptions.
- **Scalable Architecture**: Modular components and RESTful APIs designed for scalability and maintainability.

## Key Features

### 1. Main Application
- **`med.voll.api.ApiApplication`**: The Spring Boot entry point that initializes the environment and runs the application.

### 2. CORS Configuration
- **`med.voll.api.config.CorsConfiguration`**: Configures CORS to allow cross-origin requests, enabling interaction with external frontends or services.

### 3. Authentication Controller
- **`med.voll.api.controller.AutenticacionController`**: Manages user authentication and generates JWT tokens for secure API access.

### 4. Doctor Controller
- **`med.voll.api.controller.MedicoController`**: Handles endpoints for managing doctors, including listing, registration, updates, and logical deletions.

### 5. Patient Controller
- **`med.voll.api.controller.PacienteController`**: Provides endpoints for managing patients, including registration, updates, and retrieval.

### 6. Address Data
- **`med.voll.api.domain.direccion.DatosDireccion`**: Represents structured data for addresses, with validation constraints to ensure data integrity.

### 7. Address Model
- **`med.voll.api.domain.direccion.Direccion`**: Embeddable entity for storing and managing address details dynamically within other entities.

### 8. Doctor Update Data
- **`med.voll.api.domain.medico.DatosActualizaMedico`**: Captures the data required to update doctor information, with validation support.

### 9. Doctor List Data
- **`med.voll.api.domain.medico.DatosListadoMedico`**: Provides a summary of doctor information, including name, specialty, and contact details.

### 10. Doctor Registration Data
- **`med.voll.api.domain.medico.DatosRegistroMedico`**: Defines the structure for registering a new doctor, with validations for mandatory fields.

### 11. Doctor Response Data
- **`med.voll.api.domain.medico.DatosRespuestaMedico`**: Provides structured response data for doctor details, including addresses.

### 12. Specialty Enum
- **`med.voll.api.domain.medico.Especialidad`**: Enumeration for managing doctor specialties, supporting JSON serialization and deserialization.

### 13. Specialty Converter
- **`med.voll.api.domain.medico.EspecialidadConverter`**: Handles conversion of specialty data between the database and the application.

### 14. Doctor Model
- **`med.voll.api.domain.medico.Medico`**: Represents the `Medico` entity, encapsulating details such as name, email, specialty, and address. Supports logical deletions and updates.

### 15. Doctor Repository
- **`med.voll.api.domain.medico.MedicoRepository`**: Provides custom queries and database operations for the `Medico` entity.

### 16. Patient Update Data
- **`med.voll.api.domain.paciente.DatosActualizaPaciente`**: Captures the information required to update patient details, including address data.

### 17. Patient List Data
- **`med.voll.api.domain.paciente.DatosListadoPaciente`**: Summarizes patient information, such as name, email, and identification.

### 18. Patient Registration Data
- **`med.voll.api.domain.paciente.DatosRegistroPaciente`**: Defines the data structure for registering a new patient, with validations for key fields.

### 19. Patient Response Data
- **`med.voll.api.domain.paciente.DatosRespuestaPaciente`**: Offers a detailed response structure for patient data, including address information.

### 20. Patient Model
- **`med.voll.api.domain.paciente.Paciente`**: Represents the `Paciente` entity, managing attributes like name, email, and active status. Supports updates and logical deletion.

### 21. Patient Repository
- **`med.voll.api.domain.paciente.PacienteRepository`**: Provides database operations for managing patient entities, including custom queries for active patients.

### 22. User Authentication Data
- **`med.voll.api.domain.usuario.DatosAutenticacionUsuario`**: Represents user authentication data, including login credentials.

### 23. Role Enumeration
- **`med.voll.api.domain.usuario.RoleEnum`**: Defines roles in the system, such as ADMIN, USER_MEDIC, and USER_PATIENT.

### 24. User Model
- **`med.voll.api.domain.usuario.Usuario`**: Represents user credentials and roles, implementing `UserDetails` for integration with Spring Security.

### 25. User Repository
- **`med.voll.api.domain.usuario.UsuarioRepository`**: Manages database operations for the `Usuario` entity.

### 26. Global Error Handler
- **`med.voll.api.infra.errors.GlobalErrorHandler`**: Handles global exceptions, ensuring meaningful error responses for the API.

### 27. Authentication Service
- **`med.voll.api.infra.security.AutenticacionService`**: Implements user authentication and integration with Spring Security.

### 28. JWT Authentication Filter
- **`med.voll.api.infra.security.JwtAuthenticationFilter`**: Processes and validates JWT tokens in incoming requests.

### 29. Security Configuration
- **`med.voll.api.infra.security.SecurityConfiguration`**: Configures authentication mechanisms and role-based access control.

### 30. Token Service
- **`med.voll.api.infra.security.TokenService`**: Generates and validates JWT tokens for secure access.

---

## System Requirements
To run this application:
- **Java SDK 17 or higher**: The application is developed with Java 17.
- **Spring Boot Framework**: Simplifies application setup and management.
- **Internet Connection**: Required for API calls and JWT validation.

## How to Run
1. Clone the repository:
   ```bash
   git clone https://github.com/DiiegoA/aplique-las-mejores-practicas-y-proteja-una-API-Rest.git
