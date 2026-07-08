# Copilot instructions for TaskFlow

## Project context
- This project is a Java 21 Spring Boot application that exposes a task management API and a browser dashboard.
- Prefer small, focused changes that preserve the current API contract and user experience.
- Keep code in English and follow the repository's Java conventions.

## Architecture guidance
- Use Spring MVC and dependency injection for controllers and services.
- Keep business logic inside the service layer and avoid leaking persistence concerns into controllers.
- Prefer immutable DTOs and records for request/response models.
- Use meaningful exception handling with consistent error payloads.

## Development expectations
- Preserve the existing routes: `/health`, `/`, `/tasks`, and `/tasks/{id}`.
- When changing the UI, keep the dashboard accessible and functional in a browser.
- Favor maintainability over clever shortcuts.
- Match the existing package structure under `src/main/java/com/example`.

## Quality bar
- Verify with `mvn -q -DskipTests compile` and `npm run test:smoke` before considering work complete.
- Prefer adding or updating tests when behavior changes significantly.
