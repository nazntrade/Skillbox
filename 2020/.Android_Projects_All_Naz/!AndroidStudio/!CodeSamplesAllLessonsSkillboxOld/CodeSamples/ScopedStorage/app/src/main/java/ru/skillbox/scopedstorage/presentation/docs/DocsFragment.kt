package ru.skillbox.scopedstorage.presentation.docs

import android.net.Uri
import android.os.Bundle
import android.provider.DocumentsContract
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.skillbox.scopedstorage.databinding.FragmentDocsBinding
import ru.skillbox.scopedstorage.utils.ViewBindingFragment
import ru.skillbox.scopedstorage.utils.toast
import timber.log.Timber

class DocsFragment : ViewBindingFragment<FragmentDocsBinding>(FragmentDocsBinding::inflate) {

    private lateinit var openDocumentLauncher: ActivityResultLauncher<Array<String>>
    private lateinit var createDocumentLauncher: ActivityResultLauncher<String>
    private lateinit var selectDocumentDirectoryLauncher: ActivityResultLauncher<Uri>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initOpenDocumentLauncher()
        initCreateDocumentLauncher()
        initSelectDocumentFolderLauncher()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.readFileButton.setOnClickListener {
            readFile()
        }
        binding.createFileButton.setOnClickListener {
            createFile()
        }
        binding.selectDirectoryButton.setOnClickListener {
            selectDirectory()
        }
    }

    private fun initOpenDocumentLauncher() {
        openDocumentLauncher = registerForActivityResult(
            ActivityResultContracts.OpenDocument()
        ) { uri ->
            handleOpenDocument(uri)
        }
    }

    private fun initCreateDocumentLauncher() {
        createDocumentLauncher = registerForActivityResult(
            ActivityResultContracts.CreateDocument()
        ) { uri ->
            handleCreateFile(uri)
        }
    }

    private fun initSelectDocumentFolderLauncher() {
        selectDocumentDirectoryLauncher = registerForActivityResult(
            ActivityResultContracts.OpenDocumentTree()
        ) { uri ->
            handleSelectDirectory(uri)
        }
    }

    private fun readFile() {
        openDocumentLauncher.launch(arrayOf("text/plain"))
    }

    private fun createFile() {
        createDocumentLauncher.launch("new file.txt")
    }

    private fun selectDirectory() {
        selectDocumentDirectoryLauncher.launch(null)
    }

    private fun handleOpenDocument(uri: Uri?) {
        if (uri == null) {
            toast("not selected")
            return
        }

        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                requireContext().contentResolver.openInputStream(uri)
                    ?.bufferedReader()
                    ?.use {
                        val content = it.readLines().joinToString("\n")
                        Timber.d("read content\n$content")
                    }
            }
        }
    }

    private fun handleCreateFile(uri: Uri?) {
        if (uri == null) {
            toast("file not created")
            return
        }

        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                requireContext().contentResolver.openOutputStream(uri)?.bufferedWriter()
                    ?.use {
                        it.write("some test content")
                    }
            }
        }
    }

    private fun handleSelectDirectory(uri: Uri?) {
        if (uri == null) {
            toast("directory not selected")
            return
        }
        toast("Selected directory = $uri")
    }
}