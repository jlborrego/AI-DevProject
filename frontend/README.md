# TaskFlow Frontend - React + Vite Dashboard

Modern, reactive frontend for task management built with React, TypeScript, and Vite.

## Quick Start

```bash
cd frontend
npm install
npm run dev
```

The dashboard will be available at `http://localhost:5173`

## Project Structure

```
frontend/
├── src/
│   ├── components/          # React components
│   │   ├── TaskCard.tsx       # Individual task display
│   │   ├── TaskForm.tsx       # Create/Edit task form
│   │   ├── TaskList.tsx       # Task list with search/filter
│   │   └── Overview.tsx       # Statistics dashboard
│   ├── hooks/               # Custom React hooks
│   │   └── useTasks.ts       # Task management hook
│   ├── services/            # API communication
│   │   └── api.ts            # Axios API client
│   ├── stores/              # State management (Zustand)
│   │   └── taskStore.ts      # Task store
│   ├── types/               # TypeScript types
│   │   └── index.ts          # Type definitions
│   ├── styles/              # Global styles
│   │   └── globals.css       # Tailwind setup
│   ├── test/                # Test setup
│   ├── App.tsx              # Main app component
│   └── main.tsx             # React entry point
├── public/                  # Static files
├── vite.config.ts           # Vite configuration
├── tailwind.config.js       # Tailwind CSS config
├── tsconfig.json            # TypeScript config
├── .env                     # Environment variables
└── package.json             # Dependencies
```

## Available Scripts

- `npm run dev` - Start development server with HMR
- `npm run build` - Build for production
- `npm run preview` - Preview production build
- `npm run test` - Run tests with Vitest
- `npm run test:ui` - Run tests with UI
- `npm run test:coverage` - Generate coverage report

## Tech Stack

- **React 18** - UI library
- **Vite 5** - Build tool with HMR
- **TypeScript** - Type safety
- **Tailwind CSS** - Styling
- **Zustand** - State management
- **Axios** - HTTP client
- **Framer Motion** - Animations
- **Lucide React** - Icons
- **Vitest** - Testing framework

## Key Features

✅ Real-time task management (CRUD)
✅ Search and filter tasks
✅ Task status tracking (pending, in_progress, completed)
✅ Smooth animations and transitions
✅ Responsive design (mobile, tablet, desktop)
✅ Modern dark theme
✅ Type-safe with TypeScript

## Development

### Hot Module Replacement (HMR)
Changes to components are reflected instantly during development without full page reload.

### API Integration
The app communicates with the Spring Boot backend at `http://localhost:7000/api/tasks`

### Styling
- **Tailwind CSS** for utility-first styling
- Custom dark theme colors
- Responsive breakpoints

### State Management
**Zustand** store handles:
- Task CRUD operations
- Loading states
- Error handling
- Statistics calculation

## Configuration

### Environment Variables

Create a `.env` file in the frontend directory:

```env
VITE_API_BASE_URL=http://localhost:7000
VITE_APP_TITLE=TaskFlow
```

### Vite Proxy

API calls are proxied to the backend via Vite's proxy configuration in `vite.config.ts`

## Testing

```bash
# Run all tests
npm run test

# Run tests in watch mode
npm run test -- --watch

# Generate coverage report
npm run test:coverage

# Run tests with UI
npm run test:ui
```

## Building for Production

```bash
npm run build
```

Output will be in the `dist/` directory, ready to be served by the backend.

## Performance Optimizations

- **Code Splitting** - Vendor and app code separated
- **Lazy Loading** - Components loaded on demand
- **Tree Shaking** - Unused code removed
- **Minification** - Production code optimized
- **Compression** - Assets gzipped

## Browser Support

- Chrome (latest)
- Firefox (latest)
- Safari (latest)
- Edge (latest)

## Contributing

Follow the project conventions:
- Components use functional style with hooks
- TypeScript types for all props
- Consistent error handling
- Responsive-first design

## License

Same as parent project
