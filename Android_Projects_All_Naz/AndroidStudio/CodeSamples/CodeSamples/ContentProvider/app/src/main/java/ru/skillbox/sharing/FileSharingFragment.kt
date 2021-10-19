package ru.skillbox.sharing

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.core.content.FileProvider
import androidx.core.content.FileProvider.getUriForFile
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.fragment_file_sharing.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.skillbox.contentprovider.BuildConfig
import ru.skillbox.contentprovider.R
import java.io.File

class FileSharingFragment : Fragment(R.layout.fragment_file_sharing) {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        saveFileWithTextToExternalStorage()
        shareButton.setOnClickListener {
            shareFile()
        }
    }

    private fun shareText() {
        val intent = Intent(Intent.ACTION_SEND).apply {
            putExtra(Intent.EXTRA_TEXT, "Отправленный текст")
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(intent, null)
        startActivity(shareIntent)
    }

    private fun saveFileWithTextToExternalStorage() = lifecycleScope.launch(Dispatchers.IO) {
        if (Environment.getExternalStorageState() != Environment.MEDIA_MOUNTED) return@launch
        val dir = requireContext().getExternalFilesDir("saved_files")
        val file = File(dir, "textfile.txt")
        if (file.exists()) return@launch
        try {
            file.outputStream().buffered().use { stream ->
                (0..1000).forEach {
                    stream.write(it.toString().toByteArray())
                }
            }
            Log.d("FileSharingFragment", "file save success")
        } catch (t: Throwable) {
            Log.e("FileSharingFragment", "file save error", t)
        }
    }

    private fun shareFile() {
        lifecycleScope.launch(Dispatchers.IO) {
            if (Environment.getExternalStorageState() != Environment.MEDIA_MOUNTED) return@launch
            val dir = requireContext().getExternalFilesDir("saved_files")
            val file = File(dir, "textfile.txt")
            if (file.exists().not()) return@launch

            val uri = FileProvider.getUriForFile(
                requireContext(),
                "${BuildConfig.APPLICATION_ID}.file_provider",
                file
            )

            val intent = Intent(Intent.ACTION_SEND).apply {
                putExtra(Intent.EXTRA_STREAM, uri)
                type = requireContext().contentResolver.getType(uri)
                setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }

            val shareIntent = Intent.createChooser(intent, null)
            startActivity(shareIntent)
        }



    }
}