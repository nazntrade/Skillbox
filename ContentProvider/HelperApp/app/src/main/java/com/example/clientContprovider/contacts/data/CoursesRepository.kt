package com.example.clientContprovider.contacts.data

import android.content.ContentProviderOperation
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.provider.ContactsContract
import android.util.Log
import com.example.clientContprovider.contacts.detailInfo.DetailCourseInfoFragmentArgs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CoursesRepository(
    private val context: Context
) {

    suspend fun saveCourse(title: String) {
        withContext(Dispatchers.IO) {
            if (title.isBlank()
            ) {
                throw IncorrectFormException()
            }
            val courseId = saveRowCourse()
            saveCourse(courseId, title)
        }
    }

    private fun saveRowCourse(): Long {
        val uri = context.contentResolver.insert(
            ContactsContract.RawContacts.CONTENT_URI,
            ContentValues()
        )
        Log.d("saveRawCourse", "uri = $uri")
        return uri?.lastPathSegment?.toLongOrNull() ?: error("cannot save raw course")
    }

    private fun saveCourse(courseId: Long, title: String) {
        val contentValues = ContentValues().apply {
            put(ContactsContract.Data.RAW_CONTACT_ID, courseId)
            put(
                ContactsContract.Data.MIMETYPE,
                ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE
            )
            put(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
                title
            )
        }
        context.contentResolver.insert(ContactsContract.Data.CONTENT_URI, contentValues)
    }

    suspend fun deleteCourse(args: DetailCourseInfoFragmentArgs) {
        withContext(Dispatchers.IO) {
            val ops = ArrayList<ContentProviderOperation>()
            ops.add(
                ContentProviderOperation.newDelete(ContactsContract.RawContacts.CONTENT_URI)
                    .withSelection(
                        ContactsContract.RawContacts._ID + "=?",
                        arrayOf(java.lang.String.valueOf(args.currentContact.id))
                    )
                    .build()
            )
            context.contentResolver.applyBatch(ContactsContract.AUTHORITY, ops)
        }
    }

    suspend fun getAllCourses(): List<Course> = withContext(Dispatchers.IO) {
        context.contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
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
            val nameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY)
            val title = cursor.getString(nameIndex).orEmpty()

            val idIndex = cursor.getColumnIndex(ContactsContract.Contacts._ID)
            val id = cursor.getLong(idIndex)

            list.add(Course(id = id, title = title))
        } while (cursor.moveToNext())
        return list
    }
}