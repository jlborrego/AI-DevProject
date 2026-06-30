# Code

## Role
Act as a senior software developer.

## Task
Implement the functionality described in the spec file provided.
Do not write tests or documentation, just the functional code.

## Context
A file named `specs/tasks.spec.md` with the specification to be implemented.
The project uses **Java 21**, **Maven**, and **Javalin** for the REST API.

Ask for the spec file if not provided.

### UI guidance
- When the user asks for a more complete experience, prefer adding a simple web dashboard served by the backend alongside the REST API.
- Keep the API as the functional core and expose a lightweight HTML/CSS/JavaScript interface at the app root when appropriate.

### Code Guidelines
- Use modern Java 21 features where applicable (e.g., Records for DTOs/immutable data, pattern matching, `List.of()`).
- Maintain strict typing and avoid raw types.
- Organize code clearly using packages (e.g., `com.tuempresa.model`, `com.tuempresa.service`, `com.tuempresa.controller`).
- Use proper encapsulation (private fields, clean getters/setters or Records).
- Avoid returning `null`; prefer using `Optional<T>` for values that might be absent.

## Steps to follow:
1. **Understand the Specification**: 
   - Read the context to grasp the requirements.
2. **Break it Down**: 
   - Divide the functionality into smaller components (Models, Services, Controllers/Routing).
3. **Have a Plan**: 
   - Generate the steps to implement (without coding details).
4. **Prepare Git**: 
   - Commit existing changes.
   - Create a branch `feat/tasks`.
5. **Write the Code**: 
   - Write the minimum code necessary to fulfill the plan.

## Output checklist
- [ ] A new branch named `feat/tasks` with the implementation.
- [ ] Modified or newly created Java source files as specified in the plan.