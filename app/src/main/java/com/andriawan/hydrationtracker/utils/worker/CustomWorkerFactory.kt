package com.andriawan.hydrationtracker.utils.worker

import android.content.Context
import androidx.work.*
import com.andriawan.hydrationtracker.data.repository.DailyHistoryRepository
import com.andriawan.hydrationtracker.worker.HistoryAddWorker
import javax.inject.Inject

class CustomWorkerFactory @Inject constructor(
    private val repository: DailyHistoryRepository
): WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        val workerClass = Class.forName(workerClassName).asSubclass(CoroutineWorker::class.java)
        val constructor = workerClass.getDeclaredConstructor(Context::class.java, WorkerParameters::class.java)
        val instance = constructor.newInstance(appContext, workerParameters)

        when (instance) {
            is HistoryAddWorker -> {
                instance.historyRepository = repository
            }
        }

        return instance
    }
}