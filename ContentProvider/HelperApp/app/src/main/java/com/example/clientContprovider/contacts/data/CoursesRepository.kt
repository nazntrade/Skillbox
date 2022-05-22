package com.example.clientContprovider.contacts.data

import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.example.clientContprovider.contacts.detailInfo.DetailCourseInfoFragmentArgs
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Error
import java.lang.IllegalStateException

class CoursesRepository(
    private val context: Context
) {

    suspend fun getAllCourses(): List<Course> = withContext(Dispatchers.IO) {
        context.contentResolver.query(
            COURSE_URI,
            null,
            null,
            null,
            null
        )?.use {
            getCoursesFromCursor(it)
        }.orEmpty()
    }

    private fun getCoursesFromCursor(cursor: Cursor): List<Course> {
        if (cursor.moveToFirst().not()) return emptyList()
        val list = mutableListOf<Course>()
        do {
            val titleIndex = cursor.getColumnIndex(COLUMN_COURSE_TITLE)
            val title = cursor.getString(titleIndex).orEmpty()

            val idIndex = cursor.getColumnIndex(COLUMN_COURSE_ID)
            val id = cursor.getLong(idIndex)

            list.add(Course(id = id, title = title))
        } while (cursor.moveToNext())
        return list
    }

    suspend fun saveCourse(title: String) {
        withContext(Dispatchers.IO) {
            if (title.isBlank()) {
                throw IncorrectFormException()
            } else {
                val id = (1..999999999).random()
                val cv = ContentValues().apply {
                    put(COLUMN_COURSE_ID, id)
                    put(COLUMN_COURSE_TITLE, title)
                }
                val newUri = context.contentResolver.insert(COURSE_URI, cv)
                Log.d("saveCourse", "insert, result Uri : " + newUri.toString())
            }
        }
    }

    suspend fun deleteCourse(args: DetailCourseInfoFragmentArgs) {
        withContext(Dispatchers.IO) {
            val uri = ContentUris.withAppendedId(COURSE_URI, args.currentCourse.id)
            context.contentResolver.delete(uri, null, null)
        }
    }

    suspend fun deleteAllCourse() {
        withContext(Dispatchers.IO) {
            context.contentResolver.delete(COURSE_URI, null, null)
        }
    }

    suspend fun editCourse(editedCourse: Course): String {
        val newTitle = editedCourse.title
        withContext(Dispatchers.IO) {
            val cv = ContentValues().apply {
                put(COLUMN_COURSE_ID, editedCourse.id)
                put(COLUMN_COURSE_TITLE, newTitle)
            }
            val uri = ContentUris.withAppendedId(COURSE_URI, editedCourse.id)
            context.contentResolver.update(uri, cv, null, null)
        }
        return newTitle
    }

    companion object {
        private val COURSE_URI =
            Uri.parse("content://com.example.hw_contentprovider.provider/courses")
        private const val COLUMN_COURSE_ID = "id"
        private const val COLUMN_COURSE_TITLE = "title"
    }
}