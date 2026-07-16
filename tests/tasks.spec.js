const { test, expect } = require('@playwright/test');

test.describe('Task Management API', () => {
  test('create, list, retrieve, update, and delete a task', async ({ request }) => {
    const createResponse = await request.post('/api/tasks', {
      headers: { 'Content-Type': 'application/json' },
      data: JSON.stringify({
        title: 'Prepare release notes',
        description: 'Create API tasks for release validation',
        status: 'pending',
      }),
    });

    expect(createResponse.status()).toBe(201);
    const task = await createResponse.json();
    expect(task).toMatchObject({
      title: 'Prepare release notes',
      description: 'Create API tasks for release validation',
      status: 'pending',
    });
    expect(typeof task.id).toBe('number');

    const listResponse = await request.get('/api/tasks');
    expect(listResponse.ok()).toBeTruthy();
    const tasks = await listResponse.json();
    expect(Array.isArray(tasks)).toBeTruthy();
    expect(tasks.some((item) => item.id === task.id)).toBeTruthy();

    const getResponse = await request.get(`/api/tasks/${task.id}`);
    expect(getResponse.ok()).toBeTruthy();
    await expect(getResponse.json()).resolves.toMatchObject(task);

    const updateResponse = await request.put(`/api/tasks/${task.id}`, {
      headers: { 'Content-Type': 'application/json' },
      data: JSON.stringify({
        title: 'Prepare release notes',
        description: 'Update task API acceptance coverage',
        status: 'in_progress',
      }),
    });
    expect(updateResponse.ok()).toBeTruthy();
    const updated = await updateResponse.json();
    expect(updated.status).toBe('in_progress');
    expect(updated.description).toBe('Update task API acceptance coverage');

    const deleteResponse = await request.delete(`/api/tasks/${task.id}`);
    expect(deleteResponse.status()).toBe(204);

    const notFoundResponse = await request.get(`/api/tasks/${task.id}`);
    expect(notFoundResponse.status()).toBe(404);
  });

  test('rejects invalid status values', async ({ request }) => {
    const response = await request.post('/api/tasks', {
      headers: { 'Content-Type': 'application/json' },
      data: JSON.stringify({
        title: 'Invalid status task',
        description: 'This should fail',
        status: 'unknown',
      }),
    });

    expect(response.status()).toBe(400);
    const payload = await response.json();
    expect(payload.error).toContain('Invalid status');
  });

  test('rejects incomplete payloads', async ({ request }) => {
    const response = await request.post('/api/tasks', {
      headers: { 'Content-Type': 'application/json' },
      data: JSON.stringify({
        title: 'Missing description',
        status: 'pending',
      }),
    });

    expect(response.status()).toBe(400);
    const payload = await response.json();
    expect(payload.error).toContain('description');
  });

  test('returns 404 for non-existent tasks', async ({ request }) => {
    const response = await request.get('/api/tasks/999999');
    expect(response.status()).toBe(404);
    const payload = await response.json();
    expect(payload.error).toContain('Task not found');
  });
});
