import { create } from 'zustand'
import { Task, TaskRequest, TaskStatus } from '@/types'
import { taskApi } from '@/services/api'

interface TaskStore {
  tasks: Task[]
  loading: boolean
  error: string | null
  fetchTasks: () => Promise<void>
  addTask: (task: TaskRequest) => Promise<void>
  updateTask: (id: string, task: TaskRequest) => Promise<void>
  deleteTask: (id: string) => Promise<void>
  clearError: () => void
  getStats: () => {
    total: number
    pending: number
    inProgress: number
    completed: number
  }
}

export const useTaskStore = create<TaskStore>((set, get) => ({
  tasks: [],
  loading: false,
  error: null,

  fetchTasks: async () => {
    set({ loading: true, error: null })
    try {
      const tasks = await taskApi.getTasks()
      set({ tasks, loading: false })
    } catch (error) {
      const message = error instanceof Error ? error.message : 'Failed to fetch tasks'
      set({ error: message, loading: false })
    }
  },

  addTask: async (taskData: TaskRequest) => {
    set({ loading: true, error: null })
    try {
      const newTask = await taskApi.createTask(taskData)
      set((state) => ({
        tasks: [...state.tasks, newTask],
        loading: false,
      }))
    } catch (error) {
      const message = error instanceof Error ? error.message : 'Failed to create task'
      set({ error: message, loading: false })
      throw error
    }
  },

  updateTask: async (id: string, taskData: TaskRequest) => {
    set({ loading: true, error: null })
    try {
      const updated = await taskApi.updateTask(id, taskData)
      set((state) => ({
        tasks: state.tasks.map((t) => (t.id === id ? updated : t)),
        loading: false,
      }))
    } catch (error) {
      const message = error instanceof Error ? error.message : 'Failed to update task'
      set({ error: message, loading: false })
      throw error
    }
  },

  deleteTask: async (id: string) => {
    set({ loading: true, error: null })
    try {
      await taskApi.deleteTask(id)
      set((state) => ({
        tasks: state.tasks.filter((t) => t.id !== id),
        loading: false,
      }))
    } catch (error) {
      const message = error instanceof Error ? error.message : 'Failed to delete task'
      set({ error: message, loading: false })
      throw error
    }
  },

  clearError: () => set({ error: null }),

  getStats: () => {
    const { tasks } = get()
    return {
      total: tasks.length,
      pending: tasks.filter((t) => t.status === 'pending').length,
      inProgress: tasks.filter((t) => t.status === 'in_progress').length,
      completed: tasks.filter((t) => t.status === 'completed').length,
    }
  },
}))
