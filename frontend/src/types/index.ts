export type TaskStatus = 'pending' | 'in_progress' | 'completed'

export interface Task {
  id: string
  title: string
  description: string
  status: TaskStatus
  createdAt?: string
  updatedAt?: string
}

export interface TaskRequest {
  title: string
  description: string
  status: TaskStatus
}

export interface ApiResponse<T> {
  data: T
  message?: string
}

export interface TaskStats {
  total: number
  pending: number
  inProgress: number
  completed: number
}
