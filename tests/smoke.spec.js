const { test, expect } = require('@playwright/test');

test('health endpoint returns an UP status', async ({ request }) => {
  const response = await request.get('/health');

  expect(response.ok()).toBeTruthy();
  await expect(response.json()).resolves.toEqual({ status: 'UP' });
});

test('root route serves the task dashboard UI', async ({ request }) => {
  const response = await request.get('/');

  expect(response.ok()).toBeTruthy();
  const body = await response.text();
  expect(body).toContain('TaskMaster Dashboard');
  expect(body).toContain('Create task');
});
