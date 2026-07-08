const { defineConfig } = require('@playwright/test');

module.exports = defineConfig({
  testDir: './tests',
  timeout: 30000,
  use: {
    baseURL: 'http://127.0.0.1:7000',
    trace: 'on-first-retry',
  },
  webServer: {
    command: 'mvn -q spring-boot:run',
    url: 'http://127.0.0.1:7000/health',
    reuseExistingServer: true,
    timeout: 240000,
  },
});
