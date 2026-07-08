import { CheckCircle2, Clock, AlertCircle, BarChart3 } from 'lucide-react'
import { motion } from 'framer-motion'

interface OverviewProps {
  total: number
  pending: number
  inProgress: number
  completed: number
}

export function Overview({ total, pending, inProgress, completed }: OverviewProps) {
  const stats = [
    { label: 'Total', value: total, icon: BarChart3, color: 'text-blue-300' },
    { label: 'Pending', value: pending, icon: AlertCircle, color: 'text-yellow-300' },
    { label: 'In Progress', value: inProgress, icon: Clock, color: 'text-blue-300' },
    { label: 'Completed', value: completed, icon: CheckCircle2, color: 'text-green-300' },
  ]

  return (
    <div className="grid grid-cols-2 gap-3">
      {stats.map((stat, index) => {
        const Icon = stat.icon
        return (
          <motion.div
            key={stat.label}
            initial={{ opacity: 0, y: 10 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ delay: index * 0.1 }}
            className="rounded-lg border border-blue-400/15 bg-slate-900/50 p-4"
          >
            <div className="flex items-center justify-between">
              <div>
                <p className="text-sm text-light-emphasis">{stat.label}</p>
                <p className="text-2xl font-bold text-light-text mt-1">{stat.value}</p>
              </div>
              <Icon className={`${stat.color} opacity-50`} size={28} />
            </div>
          </motion.div>
        )
      })}
    </div>
  )
}
