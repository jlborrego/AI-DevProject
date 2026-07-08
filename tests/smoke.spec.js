const { test, expect } = require('@playwright/test');

test('health endpoint returns an UP status', async ({ request }) => {
  const response = await request.get('/health');

  expect(response.ok()).toBeTruthy();
  await expect(response.json()).resolves.toEqual({ status: 'UP' });
});

test('root route serves the React SPA dashboard', async ({ page }) => {
  await page.goto('/');
  
  // Wait for the app to load
  await page.waitForLoadState('networkidle');
  
  // Check that the React app is loaded
  const title = await page.title();
  expect(title).toContain('TaskFlow');
  
  // Check for main UI elements from the React app
  const heading = page.locator('h1');
  await expect(heading).toContainText('TaskFlow Dashboard');
  
  // Check for the Create Task button
  const createBtn = page.locator('button:has-text("Create Task")');
  await expect(createBtn).toBeVisible();
});

test('API tasks endpoint works correctly', async ({ request }) => {
  const response = await request.get('/api/tasks');

  expect(response.ok()).toBeTruthy();
  const tasks = await response.json();
  expect(Array.isArray(tasks)).toBeTruthy();
});
