**Movie Ticket Booking System (Backend)**
High-Concurrency Scalable Cinema Platform

A Spring Boot microservice designed to handle high-volume ticket bookings with real-time inventory management, tiered discount strategies, and robust concurrency control.

**Point to Note** : Since this is not a complete application, different microservices such as BookingService and PartnerService have been created as a separate modules. In original scenario, these will be SEPARATE MICROSERVICES. 

🚀 **Key Technical Features**
Concurrency Control: Implemented Optimistic Locking using JPA @Version to prevent double-booking of seats in high-traffic scenarios.

Design Patterns: * Strategy Pattern: Powering a flexible Discount Engine (50% off 3rd ticket, 20% Afternoon discounts).

Data Transfer Objects (DTOs): Using Java 17 Records for immutable, type-safe API contracts.

Global Exception Handling: Centralized @RestControllerAdvice for consistent RESTful error states (e.g., 409 Conflict for race conditions).

Advanced Querying: Specialized JPQL logic for Interval Overlap Detection to prevent screen double-booking during show creation.

Database Design: Normalized schema supporting B2B (Partners/Theatres) and B2C (Customers/Bookings) lifecycles.

🏗️ **System Architecture (HLD)**
The system follows a clean-layered architecture:

Controller Layer: Exposes RESTful endpoints for Catalog Browsing and Transactional Booking.

Service Layer: Contains core business logic, discount aggregation, and transactional boundaries.

Persistence Layer: Spring Data JPA with optimized queries to solve the "N+1 Problem."

🛠️ **Tech Stack**
Runtime: Java 17+

Framework: Spring Boot 3.x, Spring Data JPA

Database: PostgreSQL / H2 (Development)

Tools: Maven/Gradle, JUnit 5, Mockito

HLD : <img width="803" height="679" alt="Screenshot 2026-04-01 at 3 34 24 PM" src="https://github.com/user-attachments/assets/38f3295b-c8fb-468c-8c8f-523000fffcb4" />

Non Functional Arch : <img width="953" height="600" alt="Screenshot 2026-04-01 at 4 38 47 PM" src="https://github.com/user-attachments/assets/77545b72-b2b7-4f5b-a85a-428c7f814d37" />

ERD : <img width="950" height="535" alt="Screenshot 2026-04-01 at 5 32 07 PM" src="https://github.com/user-attachments/assets/e3329880-7718-4edb-88f0-58f2b77e74a5" />



