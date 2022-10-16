package ru.skillbox.services

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.workDataOf
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.skillbox.services.databinding.FragmentWorkManagerBinding
import timber.log.Timber
import java.util.concurrent.TimeUnit

class WorkManagerFragment : Fragment(R.layout.fragment_work_manager) {

    private val binding by viewBinding(FragmentWorkManagerBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        WorkManager.getInstance(requireContext())
            .getWorkInfosForUniqueWorkLiveData(DOWNLOAD_WORK_ID)
            .observe(viewLifecycleOwner, { handleWorkInfo(it.first()) })

        binding.startDownload.setOnClickListener {
            startDownload()
        }

        binding.cancelDownload.setOnClickListener {
            stopDownload()
        }
    }

    private fun stopDownload() {
        WorkManager.getInstance(requireContext()).cancelUniqueWork(DOWNLOAD_WORK_ID)
    }

    private fun startDownload() {
        val urlToDownload = binding.downloadUrl.text.toString()

        val workData = workDataOf(
            DownloadWorker.DOWNLOAD_URL_KEY to urlToDownload
        )

        val workConstraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.NOT_ROAMING)
            .build()

        val workRequest = OneTimeWorkRequestBuilder<DownloadWorker>()
            .setInputData(workData)
            .setBackoffCriteria(BackoffPolicy.LINEAR, 10, TimeUnit.SECONDS)
            .setConstraints(workConstraints)
            .build()

        WorkManager.getInstance(requireContext())
            .enqueueUniqueWork(DOWNLOAD_WORK_ID, ExistingWorkPolicy.REPLACE, workRequest)
    }

    private fun handleWorkInfo(workInfo: WorkInfo) {
        Timber.d("handleWorkInfo new state = ${workInfo.state}")
        val isFinished = workInfo.state.isFinished

        binding.startDownload.isVisible = isFinished
        binding.cancelDownload.isVisible = !isFinished
        binding.downloadProgress.isVisible = !isFinished

        if (isFinished) {
            Toast.makeText(requireContext(), "work finished with state = ${workInfo.state}", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        private const val DOWNLOAD_WORK_ID = "download work"
    }
}