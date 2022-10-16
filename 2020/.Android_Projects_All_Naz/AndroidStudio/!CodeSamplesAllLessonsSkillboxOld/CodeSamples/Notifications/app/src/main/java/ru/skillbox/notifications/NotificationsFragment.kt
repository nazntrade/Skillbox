package ru.skillbox.notifications

import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.os.Bundle
import android.view.View
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.skillbox.notifications.databinding.FragmentNotificationsBinding

class NotificationsFragment : Fragment(R.layout.fragment_notifications) {

    private val binding: FragmentNotificationsBinding by viewBinding(FragmentNotificationsBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.createSimpleNotificationButton.setOnClickListener {
            showSimpleNotification()
        }
        binding.createMessageNotificationButton.setOnClickListener {
            showMessageNotification()
        }
        binding.createNewsNotificationButton.setOnClickListener {
            showNewsNotification()
        }
        binding.createNotificationGroupButton.setOnClickListener {
            showNotificationGroup()
        }
        binding.createProgressNotificationButton.setOnClickListener {
            showProgressNotification()
        }
    }

    private fun showSimpleNotification() {
        val notification = NotificationCompat.Builder(requireContext(), "channel_id")
            .setContentTitle("My notification title")
            .setContentText("My content text")
            .setSmallIcon(R.drawable.ic_notifications)
            .build()

        NotificationManagerCompat.from(requireContext())
            .notify(SIMPLE_NOTIFICATION_ID, notification)
    }

    private fun showMessageNotification() {

        val largeIcon = BitmapFactory.decodeResource(resources, R.drawable.grapefruit)

        val notification = NotificationCompat.Builder(requireContext(), NotificationChannels.MESSAGE_CHANNEL_ID)
            .setContentTitle("You have a new message")
            .setContentText("Message text from time ${System.currentTimeMillis()}")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            .setVibrate(longArrayOf(100, 200, 500, 500))
            .setSmallIcon(R.drawable.ic_message)
            .setLargeIcon(largeIcon)
            .build()

        NotificationManagerCompat.from(requireContext())
            .notify(MESSAGE_NOTIFICATION_ID, notification)
    }

    //background thread
    private fun loadBitmapWithGlide(url: String) {
        val bitmap = Glide.with(this)
            .asBitmap()
            .load(url)
            .submit()
            .get()
    }

    private fun showNewsNotification() {
        val notification = NotificationCompat.Builder(requireContext(), NotificationChannels.NEWS_CHANNEL_ID)
            .setContentTitle("Application updates")
            .setContentText("Updates from time ${System.currentTimeMillis()}")
            .setSmallIcon(R.drawable.ic_notifications)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()

        NotificationManagerCompat.from(requireContext())
            .notify(NEWS_NOTIFICATION_ID, notification)
    }

    private fun showNotificationGroup() {
        val messageCount = 3
        val groupId = "message group"
        (0 until messageCount).forEach { messageIndex ->
            val messageNumber = messageIndex + 1
            val notification = NotificationCompat.Builder(
                requireContext(),
                NotificationChannels.MESSAGE_CHANNEL_ID
            )
                .setContentTitle("You have a new message from user $messageNumber")
                .setContentText("Message text from time ${System.currentTimeMillis()}")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSmallIcon(R.drawable.ic_message)
                .setGroup(groupId)
                .build()

            NotificationManagerCompat.from(requireContext())
                .notify(messageNumber, notification)
        }

        val summaryNotification = NotificationCompat.Builder(
            requireContext(),
            NotificationChannels.MESSAGE_CHANNEL_ID
        )
            .setContentTitle("Summary")
            .setContentText("Message text from time ${System.currentTimeMillis()}")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setSmallIcon(R.drawable.ic_message)
            .setGroup(groupId)
            .setGroupSummary(true)
            .build()

        NotificationManagerCompat.from(requireContext())
            .notify(SUMMARY_NOTIFICATION_ID, summaryNotification)
    }

    private fun showProgressNotification() {
        val notificationBuilder = NotificationCompat.Builder(
            requireContext(),
            NotificationChannels.NEWS_CHANNEL_ID
        )
            .setContentTitle("Update downloading")
            .setContentText("Download in progress")
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setSmallIcon(R.drawable.ic_notifications)

        val maxProgress = 10
        lifecycleScope.launch {
            (0 until maxProgress).forEach { progress ->
                val notification = notificationBuilder
                    .setProgress(maxProgress, progress, false)
                    .build()

                NotificationManagerCompat.from(requireContext())
                    .notify(PROGRESS_NOTIFICATION_ID, notification)

                delay(500)
            }

            val finalNotification = notificationBuilder
                .setContentText("Download is completed")
                .setProgress(0, 0, false)
                .build()

            NotificationManagerCompat.from(requireContext())
                .notify(PROGRESS_NOTIFICATION_ID, finalNotification)
            delay(500)

            NotificationManagerCompat.from(requireContext())
                .cancel(PROGRESS_NOTIFICATION_ID)

        }

    }

    companion object {
        private const val SIMPLE_NOTIFICATION_ID = 12343
        private const val MESSAGE_NOTIFICATION_ID = 12344
        private const val SUMMARY_NOTIFICATION_ID = 12341
        private const val NEWS_NOTIFICATION_ID = 12345
        private const val PROGRESS_NOTIFICATION_ID = 22345
    }
}