package ru.skillbox.services

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import kotlinx.coroutines.delay
import timber.log.Timber

class DownloadWorker(
    context: Context,
    params: WorkerParameters
): CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        val urlToDownload = inputData.getString(DOWNLOAD_URL_KEY)
        Timber.d("work started")
        delay(1000)
        return when(urlToDownload) {
            "1" -> Result.retry()
            "2" -> Result.failure()
            else -> Result.success()
        }
    }

    companion object {
        const val DOWNLOAD_URL_KEY = "download url"
    }
}