import axios, { AxiosInstance } from 'axios'
import { Task, TaskRequest } from '@/types'

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:7000'

const client: AxiosInstance = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
})

export const taskApi = {
  getTasks: async (): Promise<Task[]> => {
    const response = await client.get<Task[]>('/api/tasks')
    return response.data
  },

  getTask: async (id: string): Promise<Task> => {
    const response = await client.get<Task>(`/api/tasks/${id}`)
    return response.data
  },

  createTask: async (task: TaskRequest): Promise<Task> => {
    const response = await client.post<Task>('/api/tasks', task)
    return response.data
  },

  updateTask: async (id: string, task: TaskRequest): Promise<Task> => {
    const response = await client.put<Task>(`/api/tasks/${id}`, task)
    return response.data
  },

  deleteTask: async (id: string): Promise<void> => {
    await client.delete(`/api/tasks/${id}`)
  },
}

export default client
