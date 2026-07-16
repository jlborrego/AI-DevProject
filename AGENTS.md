# Agents Instructions

## Product Overview
- TaskMaster is a small Java backend for task management.
- It exposes a REST API and a simple browser dashboard.

## Technical Implementation

### Tech Stack
- Language: **Java 21**
- Framework: **Spring Boot 3.3.5**
- Database: **None, in-memory storage**
- Security: **No auth in the demo app**
- Testing: **Playwright 1.30.0**
- Logging: **SLF4J (with Logback/Spring Boot defaults)**

### Development workflow
```bash
# Set up the project
mvn -q -DskipTests compile
npm install

# Build/Compile the project
mvn -q -DskipTests compile

# Run the project
mvn spring-boot:run

# Test the project
npm run test:smoke

# Deploy the project
mvn package
```

### Folder structure
```text
.                         # Project root
├── AGENTS.md             # Agent instructions
├── README.md             # Human documentation
├── pom.xml               # Maven build file
├── package.json          # Playwright test script
├── src/                  # Java source code
├── tests/                # Playwright tests
└── target/               # Build output
```

## Environment
- Code and documentation must be in English.
- Chat responses must be in the language of the user prompt.
- Sacrifice grammar for conciseness in responses.
- This is a Windows environment using Git Bash.
- My default branch is `main`.

## Available Specialized Agents
You have access to specialized sub-agent definitions inside the `./agents/agents/` directory. Depending on the user's explicit request or the nature of the task, you MUST read the corresponding markdown file to adopt that specific persona, skillset, and constraints BEFORE writing any code:

- **Analyst Agent**: Read `./agents/agents/1-analyst.agent.md` for UI and dashboard tasks.
<!-- - **QA & Testing Agent**: Read `./agents/agents/qa-tester.md` when writing or debugging Playwright tests.
- **Backend/API Agent**: Read `./agents/agents/backend-dev.md` for REST endpoints and Javalin architecture. -->

If the user mentions a specific agent (e.g., "Usa el agente de QA"), immediately read its file from `./agents/agents/` and execute the task following those strict rules.

