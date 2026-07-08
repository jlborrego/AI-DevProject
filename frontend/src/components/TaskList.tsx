import { useState, useMemo } from 'react'
import { Task, TaskStatus } from '@/types'
import { TaskCard } from './TaskCard'
import { AnimatePresence } from 'framer-motion'
import { Search } from 'lucide-react'

interface TaskListProps {
  tasks: Task[]
  onEdit: (task: Task) => void
  onDelete: (id: string) => void
  loading?: boolean
}

type FilterStatus = TaskStatus | 'all'

export function TaskList({ tasks, onEdit, onDelete, loading = false }: TaskListProps) {
  const [searchTerm, setSearchTerm] = useState('')
  const [filterStatus, setFilterStatus] = useState<FilterStatus>('all')

  const filteredTasks = useMemo(() => {
    return tasks.filter((task) => {
      const matchesSearch =
        task.title.toLowerCase().includes(searchTerm.toLowerCase()) ||
        task.description.toLowerCase().includes(searchTerm.toLowerCase())

      const matchesFilter = filterStatus === 'all' || task.status === filterStatus

      return matchesSearch && matchesFilter
    })
  }, [tasks, searchTerm, filterStatus])

  return (
    <div className="space-y-4">
      <div className="flex flex-col gap-3">
        <div className="relative">
          <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 text-light-emphasis/50" size={18} />
          <input
            type="search"
            placeholder="Search tasks..."
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
            disabled={loading}
            className="w-full pl-10 pr-4 py-2 rounded-lg bg-slate-900/50 border border-blue-400/20 text-light-text placeholder-light-emphasis/50 focus:outline-none focus:ring-2 focus:ring-blue-500 disabled:opacity-50"
          />
        </div>

        <select
          value={filterStatus}
          onChange={(e) => setFilterStatus(e.target.value as FilterStatus)}
          disabled={loading}
          className="px-4 py-2 rounded-lg bg-slate-900/50 border border-blue-400/20 text-light-text focus:outline-none focus:ring-2 focus:ring-blue-500 disabled:opacity-50"
        >
          <option value="all">All statuses</option>
          <option value="pending">Pending</option>
          <option value="in_progress">In Progress</option>
          <option value="completed">Completed</option>
        </select>
      </div>

      {loading && <p className="text-center text-light-emphasis/50 py-8">Loading tasks...</p>}

      {!loading && filteredTasks.length === 0 && (
        <div className="text-center py-12 text-light-emphasis/50">
          <p>No tasks found. Create your first one to get started.</p>
        </div>
      )}

      <div className="grid grid-cols-1 md:grid-cols-2 gap-3">
        <AnimatePresence>
          {filteredTasks.map((task) => (
            <TaskCard
              key={task.id}
              task={task}
              onEdit={onEdit}
              onDelete={onDelete}
            />
          ))}
        </AnimatePresence>
      </div>
    </div>
  )
}
