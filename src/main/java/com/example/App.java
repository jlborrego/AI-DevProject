package com.example;

import com.example.controller.TaskController;
import com.example.service.TaskService;
import io.javalin.Javalin;
import java.util.Map;

public class App {
    public static void main(String[] args) {
        TaskService taskService = new TaskService();
        TaskController taskController = new TaskController(taskService);

        Javalin app = Javalin.create(config -> {
            config.routes.get("/health", ctx -> {
                ctx.contentType("application/json");
                ctx.json(Map.of("status", "UP"));
            })
            .get("/", ctx -> {
                ctx.html("""
                <!doctype html>
                <html lang="en">
                <head>
                  <meta charset="utf-8">
                  <meta name="viewport" content="width=device-width, initial-scale=1">
                  <title>TaskMaster Dashboard</title>
                  <style>
                    :root { color-scheme: dark; }
                    body {
                      margin: 0;
                      font-family: Inter, system-ui, -apple-system, BlinkMacSystemFont, "Segoe UI", sans-serif;
                      background: linear-gradient(135deg, #07111f 0%, #13243d 100%);
                      color: #f5f7fb;
                    }
                    .shell { max-width: 1100px; margin: 0 auto; padding: 2rem 1.25rem 3rem; }
                    .hero { display: flex; justify-content: space-between; gap: 1rem; align-items: center; margin-bottom: 1.5rem; }
                    .hero h1 { margin: 0; font-size: 2rem; }
                    .hero p { margin: .35rem 0 0; color: #8fb0d9; }
                    .card { background: rgba(8, 18, 33, 0.86); border: 1px solid rgba(255,255,255,0.08); border-radius: 18px; box-shadow: 0 15px 40px rgba(0,0,0,0.2); }
                    .panel { padding: 1.25rem; }
                    .grid { display: grid; grid-template-columns: 1.1fr 0.9fr; gap: 1.25rem; }
                    form { display: grid; gap: .75rem; }
                    label { display: grid; gap: .25rem; font-size: .95rem; color: #b8c9e2; }
                    input, select, textarea, button { font: inherit; border-radius: 12px; border: 1px solid rgba(255,255,255,0.12); padding: .8rem .9rem; }
                    input, select, textarea { background: #0e1b2d; color: #f7f9fc; }
                    textarea { min-height: 90px; resize: vertical; }
                    button { cursor: pointer; transition: transform .15s ease, background .2s ease; }
                    button:hover { transform: translateY(-1px); }
                    .primary { background: #5b8cff; color: white; border-color: #5b8cff; }
                    .secondary { background: transparent; color: #8fb0d9; }
                    .task-list { display: grid; gap: .8rem; margin-top: 1rem; }
                    .task-item { padding: 1rem; display: flex; justify-content: space-between; align-items: flex-start; gap: 1rem; }
                    .task-title { font-weight: 700; margin: 0 0 .3rem; }
                    .task-meta { color: #8fb0d9; font-size: .9rem; }
                    .badge { display: inline-block; padding: .3rem .6rem; border-radius: 999px; font-size: .8rem; background: rgba(91,140,255,.18); color: #9ec2ff; }
                    .actions { display: flex; gap: .5rem; }
                    .empty { padding: 1rem; text-align: center; color: #8fb0d9; }
                    @media (max-width: 800px) { .grid { grid-template-columns: 1fr; } .hero { flex-direction: column; align-items: flex-start; } }
                  </style>
                </head>
                <body>
                  <div class="shell">
                    <section class="hero">
                      <div>
                        <h1>TaskMaster Dashboard</h1>
                        <p>Create, organize and track your work from one place.</p>
                      </div>
                      <div class="card panel">
                        <strong id="task-count">0 tasks</strong>
                      </div>
                    </section>

                    <section class="grid">
                      <div class="card panel">
                        <h2>Create task</h2>
                        <form id="task-form">
                          <input id="task-id" type="hidden">
                          <label>
                            Title
                            <input id="title" name="title" placeholder="Write release notes" required>
                          </label>
                          <label>
                            Description
                            <textarea id="description" name="description" placeholder="Describe what needs to be done" required></textarea>
                          </label>
                          <label>
                            Status
                            <select id="status" name="status">
                              <option value="pending">Pending</option>
                              <option value="in_progress">In progress</option>
                              <option value="completed">Completed</option>
                            </select>
                          </label>
                          <div class="actions">
                            <button class="primary" type="submit">Save task</button>
                            <button class="secondary" id="cancel-edit" type="button">Cancel</button>
                          </div>
                        </form>
                      </div>

                      <div class="card panel">
                        <h2>My tasks</h2>
                        <div id="task-list" class="task-list"></div>
                      </div>
                    </section>
                  </div>

                  <script>
                    const state = { tasks: [], editingId: null };
                    const form = document.getElementById('task-form');
                    const taskList = document.getElementById('task-list');
                    const taskCount = document.getElementById('task-count');

                    async function loadTasks() {
                      const response = await fetch('/tasks');
                      state.tasks = await response.json();
                      renderTasks();
                    }

                    function renderTasks() {
                      taskCount.textContent = `${state.tasks.length} task${state.tasks.length === 1 ? '' : 's'}`;
                      if (!state.tasks.length) {
                        taskList.innerHTML = '<div class="empty">No tasks yet. Add one above.</div>';
                        return;
                      }

                      taskList.innerHTML = state.tasks.map(task => `
                        <article class="task-item card">
                          <div>
                            <p class="task-title">${task.title}</p>
                            <p class="task-meta">${task.description}</p>
                            <span class="badge">${task.status.replace('_', ' ')}</span>
                          </div>
                          <div class="actions">
                            <button class="secondary" data-action="edit" data-id="${task.id}" type="button">Edit</button>
                            <button class="secondary" data-action="delete" data-id="${task.id}" type="button">Delete</button>
                          </div>
                        </article>
                      `).join('');
                    }

                    function resetForm() {
                      form.reset();
                      document.getElementById('task-id').value = '';
                      state.editingId = null;
                      document.querySelector('h2').textContent = 'Create task';
                    }

                    form.addEventListener('submit', async (event) => {
                      event.preventDefault();
                      const payload = {
                        title: document.getElementById('title').value.trim(),
                        description: document.getElementById('description').value.trim(),
                        status: document.getElementById('status').value
                      };

                      const method = state.editingId ? 'PUT' : 'POST';
                      const url = state.editingId ? `/tasks/${state.editingId}` : '/tasks';

                      const response = await fetch(url, {
                        method,
                        headers: { 'Content-Type': 'application/json' },
                        body: JSON.stringify(payload)
                      });

                      if (!response.ok) {
                        const errorData = await response.json().catch(() => ({ error: 'Unable to save task' }));
                        alert(errorData.error || 'Unable to save task');
                        return;
                      }

                      resetForm();
                      await loadTasks();
                    });

                    document.getElementById('cancel-edit').addEventListener('click', resetForm);
                    taskList.addEventListener('click', async (event) => {
                      const button = event.target.closest('button[data-action]');
                      if (!button) return;

                      const { action, id } = button.dataset;
                      if (action === 'delete') {
                        await fetch(`/tasks/${id}`, { method: 'DELETE' });
                        await loadTasks();
                        return;
                      }

                      const task = state.tasks.find(item => String(item.id) === id);
                      if (!task) return;
                      document.getElementById('task-id').value = task.id;
                      document.getElementById('title').value = task.title;
                      document.getElementById('description').value = task.description;
                      document.getElementById('status').value = task.status;
                      state.editingId = task.id;
                      document.querySelector('.grid h2').textContent = 'Edit task';
                    });

                    loadTasks();
                  </script>
                </body>
                </html>
                """);
            });
            taskController.registerRoutes(config.routes);
        });

        app.start(7000);
        System.out.println("Server started on http://localhost:7000");
    }
}
