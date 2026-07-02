package com.example;

import com.example.controller.TaskController;
import com.example.service.TaskService;
import io.javalin.Javalin;
import io.javalin.http.Context;
import java.util.Map;

public class App {
    private static final int PORT = 7000;

    public static void main(String[] args) {
        TaskService taskService = new TaskService();
        TaskController taskController = new TaskController(taskService);

        Javalin app = createApp(taskController);
        app.start(PORT);
        System.out.println("Server started on http://localhost:" + PORT);
    }

    private static Javalin createApp(TaskController taskController) {
        return Javalin.create(config -> {
            config.routes.get("/health", App::health)
                .get("/", ctx -> ctx.html(dashboardPage()));
            taskController.registerRoutes(config.routes);
        });
    }

    private static void health(Context ctx) {
        ctx.contentType("application/json");
        ctx.json(Map.of("status", "UP"));
    }

    private static String dashboardPage() {
        return """
                <!doctype html>
                <html lang="en">
                <head>
                  <meta charset="utf-8">
                  <meta name="viewport" content="width=device-width, initial-scale=1">
                  <title>TaskMaster Dashboard</title>
                  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.4.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-" crossorigin="anonymous">
                  <style>
                    body {
                      min-height: 100vh;
                      background: linear-gradient(135deg, #0b1120 0%, #131f3b 100%);
                      color: #eef2ff;
                    }
                    .page-shell {
                      max-width: 1240px;
                      margin: 0 auto;
                      padding: 2.5rem 1.25rem 3rem;
                    }
                    .brand-band {
                      display: flex;
                      flex-wrap: wrap;
                      align-items: center;
                      justify-content: space-between;
                      gap: 1rem;
                      margin-bottom: 2rem;
                    }
                    .brand-band h1 {
                      margin: 0;
                      font-size: clamp(2rem, 2.6vw, 3rem);
                    }
                    .hero-copy p {
                      margin: .75rem 0 0;
                      color: #a3b4d2;
                    }
                    .dashboard-card {
                      background: rgba(18, 32, 62, 0.92);
                      border: 1px solid rgba(166, 192, 255, 0.14);
                      box-shadow: 0 30px 60px rgba(8, 18, 34, 0.22);
                      border-radius: 24px;
                    }
                    .task-card {
                      border-radius: 20px;
                      border: 1px solid rgba(166, 192, 255, 0.12);
                      background: rgba(15, 26, 48, 0.94);
                    }
                    .task-status {
                      text-transform: uppercase;
                      letter-spacing: .08em;
                      font-size: .75rem;
                      font-weight: 700;
                    }
                    .badge-pending { background: rgba(255, 193, 7, 0.16); color: #ffdd57; }
                    .badge-in_progress { background: rgba(56, 133, 255, 0.16); color: #90c8ff; }
                    .badge-completed { background: rgba(37, 211, 102, 0.16); color: #b4f2c0; }
                    .form-control, .form-select {
                      background: rgba(15, 26, 48, 0.94);
                      color: white;
                      border-color: rgba(255,255,255,0.1);
                    }
                    .form-control:focus, .form-select:focus {
                      box-shadow: 0 0 0 0.2rem rgba(91, 140, 255, 0.24);
                    }
                    .filter-bar {
                      gap: 0.75rem;
                      align-items: center;
                    }
                    .task-grid { gap: 1rem; }
                    .task-count { color: #b7c6e2; }
                    .no-tasks { color: #9aa7bd; }
                    .modal-header { border-bottom: 1px solid rgba(255,255,255,0.08); }
                    .modal-footer { border-top: 1px solid rgba(255,255,255,0.08); }
                    .btn-soft { background: rgba(255,255,255,0.08); color: #eef2ff; border: 1px solid rgba(255,255,255,0.12); }
                    .btn-soft:hover { background: rgba(255,255,255,0.13); }
                  </style>
                </head>
                <body>
                  <div class="page-shell">
                    <div class="brand-band">
                      <div class="hero-copy">
                        <h1>TaskMaster Dashboard</h1>
                        <p>Search, filter and manage tasks with a clean browser experience.</p>
                      </div>
                      <div>
                        <button id="openModal" class="btn btn-primary btn-lg">New task</button>
                      </div>
                    </div>

                    <div class="row gy-4">
                      <div class="col-lg-8">
                        <section class="dashboard-card p-4">
                          <div class="d-flex flex-wrap justify-content-between align-items-center mb-3 gap-3">
                            <div>
                              <h2 class="h5 mb-1">Task board</h2>
                              <p class="task-count mb-0">Manage tasks with live search and status filters.</p>
                            </div>
                            <div class="d-flex flex-wrap filter-bar">
                              <input id="searchInput" type="search" class="form-control form-control-sm" placeholder="Search tasks..." aria-label="Search tasks">
                              <select id="statusFilter" class="form-select form-select-sm">
                                <option value="all">All statuses</option>
                                <option value="pending">Pending</option>
                                <option value="in_progress">In progress</option>
                                <option value="completed">Completed</option>
                              </select>
                            </div>
                          </div>
                          <div id="taskList" class="row task-grid"></div>
                          <div id="emptyState" class="text-center py-5 no-tasks d-none">
                            <p class="mb-0">No tasks found. Use the button above to add your first task.</p>
                          </div>
                        </section>
                      </div>

                      <div class="col-lg-4">
                        <section class="dashboard-card p-4">
                          <h2 class="h5 mb-3">Overview</h2>
                          <div class="d-flex justify-content-between align-items-center mb-2">
                            <span>Tasks total</span>
                            <strong id="totalCount">0</strong>
                          </div>
                          <div class="d-flex justify-content-between align-items-center mb-2">
                            <span>Pending</span>
                            <strong id="pendingCount">0</strong>
                          </div>
                          <div class="d-flex justify-content-between align-items-center mb-2">
                            <span>In progress</span>
                            <strong id="progressCount">0</strong>
                          </div>
                          <div class="d-flex justify-content-between align-items-center">
                            <span>Completed</span>
                            <strong id="completedCount">0</strong>
                          </div>
                        </section>
                      </div>
                    </div>
                  </div>

                  <div class="modal fade" id="taskModal" tabindex="-1" aria-labelledby="taskModalLabel" aria-hidden="true">
                    <div class="modal-dialog modal-dialog-centered">
                      <div class="modal-content bg-dark text-white border-secondary">
                        <div class="modal-header">
                          <h5 class="modal-title" id="taskModalLabel">Create task</h5>
                          <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                          <form id="taskForm">
                            <input id="taskId" type="hidden">
                            <div class="mb-3">
                              <label for="titleInput" class="form-label">Title</label>
                              <input id="titleInput" type="text" class="form-control" placeholder="Write release notes" required>
                            </div>
                            <div class="mb-3">
                              <label for="descriptionInput" class="form-label">Description</label>
                              <textarea id="descriptionInput" class="form-control" rows="4" placeholder="Describe the task" required></textarea>
                            </div>
                            <div class="mb-3">
                              <label for="statusInput" class="form-label">Status</label>
                              <select id="statusInput" class="form-select">
                                <option value="pending">Pending</option>
                                <option value="in_progress">In progress</option>
                                <option value="completed">Completed</option>
                              </select>
                            </div>
                          </form>
                        </div>
                        <div class="modal-footer">
                          <button id="cancelButton" type="button" class="btn btn-soft" data-bs-dismiss="modal">Cancel</button>
                          <button id="saveButton" type="button" class="btn btn-primary">Save task</button>
                        </div>
                      </div>
                    </div>
                  </div>

                  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.4.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-" crossorigin="anonymous"></script>
                  <script>
                    const state = { tasks: [], filter: 'all', query: '', editingId: null };
                    const taskModal = new bootstrap.Modal(document.getElementById('taskModal'));
                    const searchInput = document.getElementById('searchInput');
                    const statusFilter = document.getElementById('statusFilter');
                    const taskList = document.getElementById('taskList');
                    const emptyState = document.getElementById('emptyState');
                    const totalCount = document.getElementById('totalCount');
                    const pendingCount = document.getElementById('pendingCount');
                    const progressCount = document.getElementById('progressCount');
                    const completedCount = document.getElementById('completedCount');
                    const taskForm = document.getElementById('taskForm');
                    const saveButton = document.getElementById('saveButton');
                    const openModalButton = document.getElementById('openModal');
                    const taskModalLabel = document.getElementById('taskModalLabel');

                    async function loadTasks() {
                      const response = await fetch('/tasks');
                      state.tasks = await response.json();
                      updateCounts();
                      renderTasks();
                    }

                    function updateCounts() {
                      totalCount.textContent = state.tasks.length;
                      pendingCount.textContent = state.tasks.filter(task => task.status === 'pending').length;
                      progressCount.textContent = state.tasks.filter(task => task.status === 'in_progress').length;
                      completedCount.textContent = state.tasks.filter(task => task.status === 'completed').length;
                    }

                    function filteredTasks() {
                      return state.tasks.filter(task => {
                        const matchesFilter = state.filter === 'all' || task.status === state.filter;
                        const text = `${task.title} ${task.description}`.toLowerCase();
                        const matchesSearch = !state.query || text.includes(state.query.toLowerCase());
                        return matchesFilter && matchesSearch;
                      });
                    }

                    function renderTasks() {
                      const tasks = filteredTasks();
                      taskList.innerHTML = tasks.map(task => `
                        <div class="col-12">
                          <div class="task-card p-4 d-flex flex-column gap-3">
                            <div class="d-flex flex-column flex-md-row justify-content-between align-items-start gap-3">
                              <div>
                                <h3 class="h5 mb-2">${escapeHtml(task.title)}</h3>
                                <p class="mb-2 text-muted">${escapeHtml(task.description)}</p>
                                <span class="badge task-status badge-${task.status}">${task.status.replace('_', ' ')}</span>
                              </div>
                              <div class="d-flex gap-2 flex-wrap">
                                <button class="btn btn-outline-light btn-sm" data-action="edit" data-id="${task.id}">Edit</button>
                                <button class="btn btn-danger btn-sm" data-action="delete" data-id="${task.id}">Delete</button>
                              </div>
                            </div>
                          </div>
                        </div>
                      `).join('');

                      emptyState.classList.toggle('d-none', tasks.length !== 0);
                    }

                    function openTaskModal(task = null) {
                      taskForm.reset();
                      state.editingId = null;
                      taskModalLabel.textContent = task ? 'Edit task' : 'Create task';
                      if (task) {
                        document.getElementById('taskId').value = task.id;
                        document.getElementById('titleInput').value = task.title;
                        document.getElementById('descriptionInput').value = task.description;
                        document.getElementById('statusInput').value = task.status;
                        state.editingId = task.id;
                      }
                      taskModal.show();
                    }

                    function escapeHtml(value) {
                      return String(value)
                        .replace(/&/g, '&amp;')
                        .replace(/</g, '&lt;')
                        .replace(/>/g, '&gt;')
                        .replace(/"/g, '&quot;')
                        .replace(/'/g, '&#39;');
                    }

                    async function saveTask() {
                      const payload = {
                        title: document.getElementById('titleInput').value.trim(),
                        description: document.getElementById('descriptionInput').value.trim(),
                        status: document.getElementById('statusInput').value
                      };
                      const url = state.editingId ? `/tasks/${state.editingId}` : '/tasks';
                      const method = state.editingId ? 'PUT' : 'POST';

                      const response = await fetch(url, {
                        method,
                        headers: { 'Content-Type': 'application/json' },
                        body: JSON.stringify(payload)
                      });

                      if (!response.ok) {
                        const error = await response.json().catch(() => ({ error: 'Unable to save task' }));
                        alert(error.error || 'Unable to save task');
                        return;
                      }

                      taskModal.hide();
                      await loadTasks();
                    }

                    searchInput.addEventListener('input', (event) => {
                      state.query = event.target.value;
                      renderTasks();
                    });

                    statusFilter.addEventListener('change', (event) => {
                      state.filter = event.target.value;
                      renderTasks();
                    });

                    openModalButton.addEventListener('click', () => openTaskModal());

                    saveButton.addEventListener('click', saveTask);

                    taskList.addEventListener('click', async (event) => {
                      const button = event.target.closest('button[data-action]');
                      if (!button) return;
                      const action = button.dataset.action;
                      const id = button.dataset.id;
                      const task = state.tasks.find(item => String(item.id) === id);

                      if (action === 'edit' && task) {
                        openTaskModal(task);
                        return;
                      }

                      if (action === 'delete') {
                        const response = await fetch(`/tasks/${id}`, { method: 'DELETE' });
                        if (response.ok) await loadTasks();
                      }
                    });

                    loadTasks();
                  </script>
                </body>
                </html>
                """;
    }
}

