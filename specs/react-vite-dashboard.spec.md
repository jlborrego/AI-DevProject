# React/Vite Dashboard Migration Specification

**Document ID**: SPEC-001  
**Status**: Draft  
**Last Updated**: 2026-07-08  
**Author**: Analyst  

---

## 1. Overview

Migrar el dashboard actual de Thymeleaf + HTML/CSS/Vanilla JS a una moderna arquitectura con React y Vite. Esto mejorará la experiencia del usuario, proporcionará mejor reactividad, mejor mantenibilidad del código frontend y escalabilidad para futuras funcionalidades.

---

## 2. Context & Motivation

### Current State
- Dashboard servido por Thymeleaf (plantilla server-side)
- Estilo con Bootstrap 5 CDN
- JavaScript vanilla para interactividad básica
- Búsqueda y filtrado en cliente
- Modal simple para crear/editar tareas
- Estadísticas de resumen en barra lateral

### Pain Points
- Acoplamiento entre el servidor Java y la lógica de presentación
- Dificultad para mantener y escalar la UI
- Falta de componentes reutilizables
- Problemas de rendimiento con actualizaciones del DOM
- Difícil agregar funcionalidades complejas de UI

### Oportunidades
- Separación clara frontend/backend (SPA)
- Mejor experiencia de usuario con reactividad
- Componentes reutilizables con React
- Mejor tooling con Vite (HMR, build optimizado)
- Facilita agregar gráficos, tablas avanzadas, etc.

---

## 3. Functional Requirements

### REQ-001: Migración de Componentes Principales
- [x] Listado de tareas con búsqueda
- [x] Filtrado por estado (pending, in_progress, completed)
- [x] Modal para crear nuevas tareas
- [x] Modal para editar tareas existentes
- [x] Eliminación de tareas
- [x] Panel de estadísticas/resumen
- [x] Indicadores visuales de estado

### REQ-002: Mejoras de UI/UX
- [x] Interfaz moderna y responsive
- [x] Animaciones suaves en transiciones
- [x] Loading states durante operaciones
- [x] Mensajes de error/éxito mejorados
- [x] Mejor accesibilidad
- [x] Soporte para temas (light/dark)

### REQ-003: Funcionalidad de API
- [x] Consultas GET `/api/tasks`
- [x] Creación POST `/api/tasks`
- [x] Actualización PUT `/api/tasks/{id}`
- [x] Eliminación DELETE `/api/tasks/{id}`
- [x] Endpoint `/health` debe mantenerse funcional

### REQ-004: Performance
- [x] Carga inicial < 3 segundos
- [x] Hot Module Replacement (HMR) en desarrollo
- [x] Build optimizado con code-splitting
- [x] Assets comprimidos

---

## 4. Technical Requirements

### TechStack
| Aspecto | Tecnología | Versión |
|--------|-----------|---------|
| Framework | React | 18+ |
| Build Tool | Vite | 5+ |
| Lenguaje | TypeScript | 5+ |
| Styling | Tailwind CSS | 3+ |
| HTTP Client | Axios | 1.6+ |
| State Management | Zustand | 4+ |
| UI Components | Shadcn/ui | Latest |
| Testing | Vitest + React Testing Library | Latest |
| Backend API | Spring Boot (sin cambios) | 3.3.5 |

### Project Structure
```
frontend/                    # Nuevo directorio para React app
├── src/
│   ├── components/
│   │   ├── TaskCard.tsx
│   │   ├── TaskForm.tsx
│   │   ├── TaskList.tsx
│   │   ├── Overview.tsx
│   │   └── common/
│   ├── pages/
│   │   └── Dashboard.tsx
│   ├── stores/
│   │   └── taskStore.ts
│   ├── services/
│   │   └── api.ts
│   ├── hooks/
│   │   └── useTasks.ts
│   ├── types/
│   │   └── index.ts
│   ├── styles/
│   │   └── globals.css
│   ├── App.tsx
│   └── main.tsx
├── public/
├── vite.config.ts
├── tsconfig.json
├── tailwind.config.js
└── package.json
```

### Backend Changes
- Remover Thymeleaf dependency (opcional, puede coexistir)
- Mantener endpoint `/` que sirva la SPA
- Asegurar CORS si es necesario
- APIs REST ya existen, validar que retornan formato correcto

---

## 5. Implementation Plan

### Phase 1: Setup & Configuration (1-2 horas)
1. Crear nuevo proyecto Vite con React + TypeScript
2. Configurar Tailwind CSS y Shadcn/ui
3. Configurar Zustand para state management
4. Configurar Axios para llamadas HTTP
5. Setup de variables de entorno para API base URL

### Phase 2: Core Components (4-6 horas)
1. Crear componente `TaskList` con búsqueda y filtrado
2. Crear componente `TaskCard` para render individual
3. Crear `TaskForm` en modal para CRUD
4. Crear `Overview` para estadísticas
5. Integrar custom hook `useTasks`

### Phase 3: State Management & API (2-3 horas)
1. Implementar store de Zustand para tareas
2. Crear servicio API con métodos GET/POST/PUT/DELETE
3. Integrar llamadas a `/api/tasks`
4. Manejar loading states y errores
5. Agregar validaciones de formulario

### Phase 4: Styling & Polish (2-3 horas)
1. Aplicar Tailwind CSS completo
2. Agregar animaciones con Framer Motion (opcional)
3. Responsive design para mobile/tablet/desktop
4. Tema oscuro/claro (toggle)
5. Iconos con Lucide React

### Phase 5: Testing & Quality (2-3 horas)
1. Tests unitarios con Vitest
2. Tests de componentes con React Testing Library
3. Tests de integración (API mocking)
4. Actualizar Playwright tests para nueva UI
5. Coverage > 80%

### Phase 6: Integration & Deployment (1-2 horas)
1. Configurar build en CI/CD
2. Build optimizado para producción
3. Servir SPA desde Spring Boot (distinto endpoint o carpeta public)
4. Testing en navegador
5. Performance profiling

### Phase 7: Migration & Cleanup (1 hora)
1. Remover Thymeleaf templates (opcional)
2. Actualizar documentación
3. Actualizar tests de smoke (Playwright)
4. Validar funcionalidad end-to-end

---

## 6. Risks & Mitigation

| Risk | Impact | Mitigation |
|------|--------|-----------|
| Compatibilidad API | Alto | Validar contrato de API antes de migración |
| Performance | Medio | Implementar lazy loading y code splitting |
| Curva aprendizaje | Bajo | Documentar decisiones y patterns |
| CORS issues | Medio | Configurar headers en Spring Boot si es necesario |
| Compatibilidad navegadores | Bajo | Testing en múltiples navegadores |

---

## 7. Success Criteria

- [x] Todos los componentes migrados y funcionales
- [x] 100% de cobertura de funcionalidad anterior
- [x] Tests de Playwright pasen sin cambios
- [x] Performance comparable o mejor
- [x] Código limpio y documentado
- [x] Sin breaking changes en API existente
- [x] Dashboard accesible (WCAG 2.1 AA mínimo)

---

## 8. Acceptance Tests

### AT-001: Listar Tareas
```gherkin
Given: Usuario abre el dashboard
When: Se carga la página
Then: Se muestran todas las tareas en una lista
```

### AT-002: Crear Tarea
```gherkin
Given: Usuario hace clic en "Create task"
When: Completa el formulario y presiona enviar
Then: Nueva tarea aparece en la lista
```

### AT-003: Filtrar por Estado
```gherkin
Given: Hay tareas con diferentes estados
When: Usuario selecciona un estado en el filtro
Then: Solo se muestran tareas de ese estado
```

### AT-004: Búsqueda
```gherkin
Given: Hay múltiples tareas
When: Usuario escribe en el campo de búsqueda
Then: Se filtran tareas en tiempo real
```

### AT-005: API Health
```gherkin
Given: El servidor está corriendo
When: Se accede a `/health`
Then: Retorna status "UP"
```

---

## 9. Related Documents

- Issue GitHub: [Link será agregado]
- Task Specification: `specs/tasks.spec.md`
- AGENTS.md: Instrucciones del proyecto

---

## 10. Approval

- [ ] Product Owner: _____
- [ ] Tech Lead: _____
- [ ] QA Lead: _____

