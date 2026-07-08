import { useEffect } from 'react'
import { useTaskStore } from '@/stores/taskStore'

export const useTasks = () => {
  const {
    tasks,
    loading,
    error,
    fetchTasks,
    addTask,
    updateTask,
    deleteTask,
    clearError,
    getStats,
  } = useTaskStore()

  useEffect(() => {
    fetchTasks()
  }, [])

  return {
    tasks,
    loading,
    error,
    fetchTasks,
    addTask,
    updateTask,
    deleteTask,
    clearError,
    getStats,
  }
}
