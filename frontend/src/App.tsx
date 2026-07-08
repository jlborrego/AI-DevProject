import { useState, useCallback } from 'react'
import { useTasks } from '@/hooks/useTasks'
import { Task, TaskRequest } from '@/types'
import { TaskList } from '@/components/TaskList'
import { TaskForm } from '@/components/TaskForm'
import { Overview } from '@/components/Overview'
import { Plus, X } from 'lucide-react'
import { motion, AnimatePresence } from 'framer-motion'

export default function Dashboard() {
  const { tasks, loading, error, addTask, updateTask, deleteTask, clearError, getStats } = useTasks()
  const [isModalOpen, setIsModalOpen] = useState(false)
  const [selectedTask, setSelectedTask] = useState<Task | null>(null)
  const [isSubmitting, setIsSubmitting] = useState(false)

  const stats = getStats()

  const handleOpenModal = useCallback((task?: Task) => {
    setSelectedTask(task || null)
    setIsModalOpen(true)
  }, [])

  const handleCloseModal = useCallback(() => {
    setIsModalOpen(false)
    setSelectedTask(null)
  }, [])

  const handleSubmit = async (data: TaskRequest) => {
    setIsSubmitting(true)
    try {
      if (selectedTask) {
        await updateTask(selectedTask.id, data)
      } else {
        await addTask(data)
      }
      handleCloseModal()
    } finally {
      setIsSubmitting(false)
    }
  }

  const handleDelete = async (id: string) => {
    if (confirm('Are you sure you want to delete this task?')) {
      try {
        await deleteTask(id)
      } catch (err) {
        console.error('Failed to delete task:', err)
      }
    }
  }

  return (
    <div className="min-h-screen bg-gradient-dark text-light-text">
      <div className="max-w-7xl mx-auto px-4 py-8">
        {/* Header */}
        <motion.section
          initial={{ opacity: 0, y: -20 }}
          animate={{ opacity: 1, y: 0 }}
          className="rounded-2xl border border-blue-400/15 bg-slate-900/50 p-6 mb-8"
        >
          <div className="flex flex-col sm:flex-row justify-between items-start sm:items-center gap-4">
            <div>
              <div className="inline-block px-3 py-1 rounded-full bg-blue-500/20 text-blue-300 text-sm font-semibold mb-3">
                React • Vite • TaskFlow
              </div>
              <h1 className="text-3xl font-bold">TaskFlow Dashboard</h1>
              <p className="text-light-emphasis mt-2">
                Manage your tasks with a modern, reactive interface.
              </p>
            </div>
            <button
              onClick={() => handleOpenModal()}
              className="flex items-center gap-2 bg-blue-600 hover:bg-blue-700 text-white font-semibold py-2 px-6 rounded-lg transition-colors whitespace-nowrap"
            >
              <Plus size={20} />
              Create Task
            </button>
          </div>
        </motion.section>

        {/* Error Banner */}
        <AnimatePresence>
          {error && (
            <motion.div
              initial={{ opacity: 0, y: -10 }}
              animate={{ opacity: 1, y: 0 }}
              exit={{ opacity: 0, y: -10 }}
              className="mb-4 p-4 rounded-lg bg-red-900/30 border border-red-500/30 text-red-300 flex justify-between items-center"
            >
              <span>{error}</span>
              <button
                onClick={clearError}
                className="text-red-300 hover:text-red-100"
              >
                <X size={20} />
              </button>
            </motion.div>
          )}
        </AnimatePresence>

        {/* Main Content */}
        <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
          {/* Tasks Column */}
          <div className="lg:col-span-2">
            <motion.section
              initial={{ opacity: 0, y: 20 }}
              animate={{ opacity: 1, y: 0 }}
              transition={{ delay: 0.1 }}
              className="rounded-2xl border border-blue-400/15 bg-slate-900/50 p-6"
            >
              <div className="mb-6">
                <h2 className="text-xl font-bold mb-1">Live Task Board</h2>
                <p className="text-light-emphasis">
                  Search, filter, and manage your tasks in one view.
                </p>
              </div>
              <TaskList
                tasks={tasks}
                onEdit={handleOpenModal}
                onDelete={handleDelete}
                loading={loading}
              />
            </motion.section>
          </div>

          {/* Sidebar */}
          <motion.section
            initial={{ opacity: 0, y: 20 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ delay: 0.2 }}
            className="rounded-2xl border border-blue-400/15 bg-slate-900/50 p-6"
          >
            <h2 className="text-xl font-bold mb-4">Overview</h2>
            <Overview
              total={stats.total}
              pending={stats.pending}
              inProgress={stats.inProgress}
              completed={stats.completed}
            />
          </motion.section>
        </div>
      </div>

      {/* Modal */}
      <AnimatePresence>
        {isModalOpen && (
          <motion.div
            initial={{ opacity: 0 }}
            animate={{ opacity: 1 }}
            exit={{ opacity: 0 }}
            className="fixed inset-0 bg-black/50 flex items-center justify-center p-4 z-50"
            onClick={handleCloseModal}
          >
            <motion.div
              initial={{ scale: 0.9, opacity: 0 }}
              animate={{ scale: 1, opacity: 1 }}
              exit={{ scale: 0.9, opacity: 0 }}
              className="bg-slate-900 rounded-lg border border-blue-400/15 p-6 max-w-md w-full"
              onClick={(e) => e.stopPropagation()}
            >
              <div className="flex justify-between items-center mb-4">
                <h3 className="text-xl font-bold">
                  {selectedTask ? 'Edit Task' : 'Create New Task'}
                </h3>
                <button
                  onClick={handleCloseModal}
                  className="text-light-emphasis hover:text-light-text"
                >
                  <X size={24} />
                </button>
              </div>
              <TaskForm
                task={selectedTask || undefined}
                onSubmit={handleSubmit}
                onCancel={handleCloseModal}
                isLoading={isSubmitting}
              />
            </motion.div>
          </motion.div>
        )}
      </AnimatePresence>
    </div>
  )
}
