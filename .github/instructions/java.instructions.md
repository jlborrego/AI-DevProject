---
description: Best practices for Java code style and clean code architecture.
applyTo: '**/*.java'
---
# Clean code best practices 

## Variables and naming
- Name variables, methods, and classes descriptively to enhance readability.
- Use named constants (`public static final`) instead of magic numbers or strings.

## Methods and complexity
- Keep methods small and focused on a single task (Single Responsibility Principle).
- Avoid deeply nested structures to reduce cognitive complexity.
- Use early returns to minimize indentation and `else` blocks.

## Classes and packages
- Avoid primitive obsession by defining strong types, domain objects, or Records.
- Favor composition over inheritance.
- Keep external dependencies to a minimum.
- Use the Adapter or Facade pattern to decouple the core logic from external libraries (like Javalin).
- Maintain a structured package hierarchy (e.g., `model`, `service`, `repository`, `controller`) for clean separation of concerns.

## Error handling and comments
- Handle errors gracefully using custom exceptions and meaningful error messages or status codes.
- Write comments to explain the "why" behind complex logic, not the "what".

## General principles
- Keep it simple and avoid over-engineering (KISS).
- Try to keep it DRY (Don't Repeat Yourself) by reusing code where applicable.

## Java specific guidelines

- Use modern Java 21 features where applicable (e.g., `switch` pattern matching, localized variables).
- Use **Java Records** for immutable data structures, DTOs, and API payloads instead of traditional classes.
- File names and class names must strictly follow `PascalCase` and match exactly (e.g., `TaskService.java`).
- Package names must be lowercase, continuous words (e.g., `com.tuempresa.taskmanager.service`).
- Maintain strict typing; avoid raw types for collections (always use generics like `List<Task>`).
- Use `final` for variables and parameters that should not change to enforce immutability.
- Avoid returning or passing `null`; use `Optional<T>` for values that may or may not be present.
- Use stream pipelines (`.stream().filter().map()`) for collection manipulation when it improves readability over loops.
- Handle asynchronous or blocking operations properly without swallowing checked exceptions; map them to clear runtime exceptions.