package com.andriawan.hydrationtracker.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.andriawan.hydrationtracker.data.models.DailyHistory
import com.andriawan.hydrationtracker.data.repository.DailyHistoryRepository
import com.andriawan.hydrationtracker.utils.DateFormatter
import com.andriawan.hydrationtracker.utils.worker.WorkerHelper
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Date

@HiltWorker
class HistoryAddWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val historyRepository: DailyHistoryRepository
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        WorkerHelper.createHistoryDrinkWorker(
            context = applicationContext,
            scheduleType = WorkerHelper.ScheduleType.SCHEDULED
        )

        try {
            withContext(Dispatchers.IO) {
                val date = DateFormatter.formatDate(Date()) ?: Date()
                val history = historyRepository.getHistory(date)
                if (history == null) {
                    val dailyHistory = DailyHistory(date = date.time)
                    historyRepository.createHistory(dailyHistory)
                }
            }

            return Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            return Result.failure()
        }
    }

    companion object {
        const val UNIQUE_WORKER_NAME = "HISTORY_ADD_WORKER"
    }
}
