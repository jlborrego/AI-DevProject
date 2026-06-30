# AI-DevProject TaskMaster

A **backend API** for managing daily tasks and team assignments.

- Tasks are scheduled with specific priority levels, due dates, and descriptions.

- Categories or tags can be assigned to tasks for better organization and filtering.

- Task status lifecycle: pending → in_progress → completed, or archived/cancelled paths.

- A user is identified by their email address and has a name and a specific role.

- One user can be assigned to multiple tasks, but tasks can also be collaborative.

- Automated reminders or alerts are triggered when a task is close to its deadline.

> [!WARNING]
> TaskMaster is a training project designed for demonstration purposes.
> The system is focused on mastering Java core concepts and object-oriented programming.
> Not for production use; data is managed in-memory, so no database is required at this initial stage.

---

- [Repository at GitHub](https://github.com/TuUsuario/task-master)
- Default branch: `main`

- **Author**: [Tu Nombre](https://tusitio.dev)
- **Project Scope**: Java Core & OOP Training
- **Socials**:
  - [GitHub](https://github.com/TuUsuario)
  - [LinkedIn](https://www.linkedin.com/in/TuUsuario/)

## Running the Project

Start the API server with Maven:

```bash
mvn -q exec:java -Dexec.mainClass=com.example.App
```

The application listens on `http://localhost:7000`.

### Web dashboard
- Open `http://localhost:7000/` in your browser to access the TaskMaster dashboard.
- Use the search box to find tasks by title or description.
- Filter tasks by status: `pending`, `in_progress`, or `completed`.
- Create and edit tasks through the modal dialog.
- Delete tasks directly from the task cards.

The REST API remains available at the same host for integrations and tests.

## Task Management API

- `GET /tasks` - list all tasks
- `GET /tasks/{id}` - retrieve a task by id
- `POST /tasks` - create a new task
- `PUT /tasks/{id}` - update an existing task
- `DELETE /tasks/{id}` - delete a task

Task JSON payload example:

```json
{
  "title": "Write release notes",
  "description": "Document the new task API endpoints",
  "status": "pending"
}
```

## Smoke Tests

Run the Playwright smoke tests with:

```bash
npm run test:smoke
```
