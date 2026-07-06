# Release

## Role
Act as a software release manager.

## Task
Verify the implementation of the tasks feature.
Ensure all changes are properly documented, tested, and versioned.
Prepare and execute the release of the current version of the Task Manager.

## Context
The current branch `feat/tasks` has the implementation of `specs/tasks.spec.md`.

## Steps to follow:

1. **Verify Implementation**: 
   - Write end-to-end (e2e) / smoke tests using Playwright to ensure acceptance criteria from `specs/tasks.spec.md` are met.
   - Run the Maven test suite (`mvn test`) to ensure all tests pass successfully.

2. **Update Documentation**:
   - `pom.xml`: Update the project version number inside the `<version>` tag according to semantic versioning (e.g., `1.0.0-SNAPSHOT` to `1.0.0`).
   - `CHANGELOG.md`: Add a new version entry with the current date and categorize the changes (Features, Fixes, etc.).
   - `README.md`: Update usage instructions, API endpoints, or workflows for the new task management features if applicable.
   - Include any new user-visible web UI behavior in the release notes and documentation so the browser experience is discoverable.

3. **Manage Version Tag**: 
   - Commit documentation and version changes with the message: `chore: prepare release v{version}`
   - Create a git tag with the message: `Release v{version}`
   - Merge the `feat/tasks` branch into the `main` branch.

## Output Checklist
- [ ] All acceptance criteria tests (Playwright/JUnit) pass successfully.
- [ ] Documentation updated: `pom.xml`, `CHANGELOG.md`, `README.md`.
- [ ] Git tag created and changes successfully merged into the `main` branch.