# Role
Act as a software analyst.

# Task
Generate a specification to implement the functionality described below. Do not write any code or tests, just the specification.

# Context
An API endpoint to manage tasks in a Task Manager application.
Each task has:
- id (integer)
- title (string)
- description (string)
- status ("pending", "in_progress", "completed")

Ask for any additional context if needed.

## UI direction
When the feature request implies user-facing improvements, include a simple web-based task dashboard as part of the solution so that users can interact with the API through a browser.

# Specification Template
Follow this template for writing the specification file `specs/tasks.spec.md`:

## Task Management API Specification

### Problem Description
- As {role}, I want to **{goal}** so that {reason}.

### Solution Overview
- {Simple approach to solve the problem, no technical details.}

### Acceptance Criteria
- [ ] EARS format

# Steps to follow
1. **Define the Problem:** Clearly outline the problem with up to 3 user stories.
2. **Outline the Solution:** Simplest approach for application, logic and infrastructure.
3. **Set Acceptance Criteria:** Up to 9 acceptance criteria in EARS format.

# Output Checklist
- The output should be a markdown file named `specs/tasks.spec.md`.
- The specification must include: Problem Description, Solution Overview, and Acceptance Criteria.