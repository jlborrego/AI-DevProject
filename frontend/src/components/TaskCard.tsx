import { Task, TaskStatus } from '@/types'
import { Trash2, Edit2 } from 'lucide-react'
import { motion } from 'framer-motion'

interface TaskCardProps {
  task: Task
  onEdit: (task: Task) => void
  onDelete: (id: string) => void
}

const statusConfig: Record<TaskStatus, { label: string; bg: string; text: string }> = {
  pending: {
    label: 'Pending',
    bg: 'bg-yellow-900/30',
    text: 'text-yellow-300',
  },
  in_progress: {
    label: 'In Progress',
    bg: 'bg-blue-900/30',
    text: 'text-blue-300',
  },
  completed: {
    label: 'Completed',
    bg: 'bg-green-900/30',
    text: 'text-green-300',
  },
}

export function TaskCard({ task, onEdit, onDelete }: TaskCardProps) {
  const config = statusConfig[task.status]

  return (
    <motion.div
      initial={{ opacity: 0, y: 10 }}
      animate={{ opacity: 1, y: 0 }}
      exit={{ opacity: 0, y: -10 }}
      className="rounded-lg border border-blue-400/15 bg-slate-900/50 p-4 hover:bg-slate-900/70 transition-colors"
    >
      <div className="flex justify-between items-start gap-3">
        <div className="flex-1 min-w-0">
          <h3 className="font-semibold text-light-text truncate">{task.title}</h3>
          <p className="text-sm text-light-emphasis mt-1 line-clamp-2">
            {task.description}
          </p>
          <div className="mt-3 flex items-center gap-2">
            <span
              className={`inline-block px-2 py-1 rounded text-xs font-medium uppercase tracking-wide ${config.bg} ${config.text}`}
            >
              {config.label}
            </span>
          </div>
        </div>
        <div className="flex gap-2 flex-shrink-0">
          <button
            onClick={() => onEdit(task)}
            className="p-2 rounded hover:bg-blue-500/20 text-blue-300 transition-colors"
            aria-label="Edit task"
          >
            <Edit2 size={16} />
          </button>
          <button
            onClick={() => onDelete(task.id)}
            className="p-2 rounded hover:bg-red-500/20 text-red-300 transition-colors"
            aria-label="Delete task"
          >
            <Trash2 size={16} />
          </button>
        </div>
      </div>
    </motion.div>
  )
}
