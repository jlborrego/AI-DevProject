## Task Management API Specification

### Problem Description
- As a task manager user, I want to create, read, update, and delete tasks so that I can track work items and their progress.
- As a team member, I want to see task status clearly so that I can understand which tasks are pending, in progress, or completed.
- As an administrator, I want task data to be managed through a consistent API so that client applications can reliably interact with the task system.

### Solution Overview
- Provide a simple API endpoint for managing tasks with operations to create, retrieve, update, and delete tasks.
- Use a task model that includes an integer id, title, description, and a controlled status value.
- Ensure the API validates task data and returns standard responses for successful and failed operations.

### Acceptance Criteria
- [ ] GIVEN a client wants to add a new task WHEN they send valid task details THEN the API creates the task and returns it with an id.
- [ ] GIVEN a client wants to view tasks WHEN they request the task list THEN the API returns all tasks with id, title, description, and status.
- [ ] GIVEN a client wants to view a specific task WHEN they request a task by id THEN the API returns that task if it exists.
- [ ] GIVEN a client wants to update a task WHEN they submit valid changes THEN the API updates the task and returns the updated task.
- [ ] GIVEN a client wants to delete a task WHEN they request deletion by id THEN the API removes the task and confirms success.
- [ ] GIVEN a client provides an invalid status WHEN they create or update a task THEN the API rejects the request with an error.
- [ ] GIVEN a client provides incomplete task data WHEN they create or update a task THEN the API rejects the request with an error.
- [ ] GIVEN a client requests a non-existent task WHEN they query by id THEN the API returns a not-found response.
- [ ] GIVEN the task data model WHEN tasks are returned THEN each task includes id, title, description, and a status of pending, in_progress, or completed.
