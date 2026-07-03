# Agents Instructions

## Product Overview
- TaskMaster is a small Java backend for task management.
- It exposes a REST API and a simple browser dashboard.

## Technical Implementation

### Tech Stack
- Language: **Java 25**
- Framework: **Javalin 7.2.2**
- Database: **None, in-memory storage**
- Security: **No auth in the demo app**
- Testing: **Playwright 1.30.0**
- Logging: **SLF4J Simple**

### Development workflow
```bash
# Set up the project
mvn -q -DskipTests compile
npm install

# Build/Compile the project
mvn -q -DskipTests compile

# Run the project
mvn -q exec:java -Dexec.mainClass=com.example.App

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
