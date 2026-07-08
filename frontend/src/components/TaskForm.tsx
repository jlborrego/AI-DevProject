import { useState } from 'react'
import { Task, TaskRequest, TaskStatus } from '@/types'
import { motion } from 'framer-motion'

interface TaskFormProps {
  task?: Task
  onSubmit: (data: TaskRequest) => Promise<void>
  onCancel: () => void
  isLoading?: boolean
}

export function TaskForm({ task, onSubmit, onCancel, isLoading = false }: TaskFormProps) {
  const [title, setTitle] = useState(task?.title || '')
  const [description, setDescription] = useState(task?.description || '')
  const [status, setStatus] = useState<TaskStatus>(task?.status || 'pending')
  const [error, setError] = useState('')

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault()
    setError('')

    if (!title.trim()) {
      setError('Title is required')
      return
    }

    if (!description.trim()) {
      setError('Description is required')
      return
    }

    try {
      await onSubmit({ title, description, status })
      setTitle('')
      setDescription('')
      setStatus('pending')
    } catch (err) {
      setError(err instanceof Error ? err.message : 'Failed to save task')
    }
  }

  return (
    <motion.form
      onSubmit={handleSubmit}
      className="space-y-4"
      initial={{ opacity: 0 }}
      animate={{ opacity: 1 }}
    >
      {error && (
        <div className="p-3 rounded bg-red-900/30 border border-red-500/30 text-red-300 text-sm">
          {error}
        </div>
      )}

      <div>
        <label className="block text-sm font-medium text-light-emphasis mb-2">
          Title
        </label>
        <input
          type="text"
          value={title}
          onChange={(e) => setTitle(e.target.value)}
          placeholder="Enter task title"
          disabled={isLoading}
          className="w-full rounded-lg bg-slate-900/50 border border-blue-400/20 px-4 py-2 text-light-text placeholder-light-emphasis/50 focus:outline-none focus:ring-2 focus:ring-blue-500 disabled:opacity-50"
          required
        />
      </div>

      <div>
        <label className="block text-sm font-medium text-light-emphasis mb-2">
          Description
        </label>
        <textarea
          value={description}
          onChange={(e) => setDescription(e.target.value)}
          placeholder="Enter task description"
          disabled={isLoading}
          rows={4}
          className="w-full rounded-lg bg-slate-900/50 border border-blue-400/20 px-4 py-2 text-light-text placeholder-light-emphasis/50 focus:outline-none focus:ring-2 focus:ring-blue-500 disabled:opacity-50"
          required
        />
      </div>

      <div>
        <label className="block text-sm font-medium text-light-emphasis mb-2">
          Status
        </label>
        <select
          value={status}
          onChange={(e) => setStatus(e.target.value as TaskStatus)}
          disabled={isLoading}
          className="w-full rounded-lg bg-slate-900/50 border border-blue-400/20 px-4 py-2 text-light-text focus:outline-none focus:ring-2 focus:ring-blue-500 disabled:opacity-50"
        >
          <option value="pending">Pending</option>
          <option value="in_progress">In Progress</option>
          <option value="completed">Completed</option>
        </select>
      </div>

      <div className="flex gap-2 justify-end pt-4">
        <button
          type="button"
          onClick={onCancel}
          disabled={isLoading}
          className="px-4 py-2 rounded-lg bg-slate-800 text-light-text hover:bg-slate-700 disabled:opacity-50 transition-colors"
        >
          Cancel
        </button>
        <button
          type="submit"
          disabled={isLoading}
          className="px-4 py-2 rounded-lg bg-blue-600 text-white hover:bg-blue-700 disabled:opacity-50 transition-colors"
        >
          {isLoading ? 'Saving...' : task ? 'Update' : 'Create'}
        </button>
      </div>
    </motion.form>
  )
}
