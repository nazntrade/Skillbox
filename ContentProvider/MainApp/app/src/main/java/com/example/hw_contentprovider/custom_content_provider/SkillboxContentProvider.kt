package com.example.hw_contentprovider.custom_content_provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.SharedPreferences
import android.content.UriMatcher
import android.database.Cursor
import android.database.MatrixCursor
import android.net.Uri
import android.util.Log
import com.example.hw_contentprovider.BuildConfig
import com.squareup.moshi.Moshi

class SkillboxContentProvider : ContentProvider() {

    private lateinit var userPrefs: SharedPreferences
    private lateinit var coursesPrefs: SharedPreferences

    private val userAdapter = Moshi.Builder().build().adapter(User::class.java)
    private val courseAdapter = Moshi.Builder().build().adapter(Course::class.java)

    private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
        addURI(AUTHORITIES, PATH_USERS, TYPE_USERS)
        addURI(AUTHORITIES, PATH_COURSES, TYPE_COURSES)
        addURI(AUTHORITIES, "$PATH_USERS/#", TYPE_USER_ID)
        addURI(AUTHORITIES, "$PATH_COURSES/#", TYPE_COURSE_ID)
    }

    override fun onCreate(): Boolean {
        Log.d("onCreateApplication", "Start SkillboxContentProvider on ${Thread.currentThread()}")
        userPrefs = context!!.getSharedPreferences("user_shared_prefs", Context.MODE_PRIVATE)
        coursesPrefs = context!!.getSharedPreferences("course_shared_prefs", Context.MODE_PRIVATE)
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        return when (uriMatcher.match(uri)) {
            TYPE_USERS -> getAllUsersCursor()
            TYPE_COURSES -> getAllCoursesCursor()
//            TYPE_USER_ID -> getUser()
            TYPE_COURSE_ID -> getCourseById(uri)  //t14.18  Continue ...
            else -> null
        }
    }

    private fun getAllUsersCursor(): Cursor {
        val allUsers = userPrefs.all.mapNotNull {
            val userJsonString = it.value as String
            userAdapter.fromJson(userJsonString)
        }

        val cursor = MatrixCursor(arrayOf(COLUMN_USER_ID, COLUMN_USER_NAME, COLUMN_USER_AGE))
        allUsers.forEach {
            cursor.newRow()
                .add(it.id)
                .add(it.name)
                .add(it.age)
        }
        return cursor
    }

    //New!
    private fun getAllCoursesCursor(): Cursor {
        val allCourses = coursesPrefs.all.mapNotNull {
            val courseJsonString = it.value as String
            courseAdapter.fromJson(courseJsonString)
        }

        val cursor = MatrixCursor(arrayOf(COLUMN_COURSE_ID, COLUMN_COURSE_TITLE))
        allCourses.forEach {
            cursor.newRow()
                .add(it.id)
                .add(it.title)
        }
        return cursor
    }

    //New! ///////////////////////////////////////////////////////!!!!!!!!!!!!!!!!!!!!!!!!!!
    private fun getCourseById(uri: Uri): Cursor {
        val courseId = uri.lastPathSegment?.toLongOrNull()?.toString()
        val courseJsonString = coursesPrefs.all.entries.first { entry ->
            entry.key == courseId
        }.key ?: null

        val course = courseJsonString?.let { courseAdapter.fromJson(it) }
            ?: error("error parsing JSON value")
        val cursor = MatrixCursor(arrayOf(COLUMN_COURSE_ID, COLUMN_COURSE_TITLE))
        return cursor.apply {
            newRow()
                .add(course.id)
                .add(course.title)
        }
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        values ?: return null
        return when (uriMatcher.match(uri)) {
            TYPE_USERS -> saveUser(values)
            TYPE_COURSES -> saveCourse(values)
            else -> null
        }
    }

    private fun saveUser(contentValues: ContentValues): Uri? {
        val id = contentValues.getAsLong(COLUMN_USER_ID) ?: return null
        val name = contentValues.getAsString(COLUMN_USER_NAME) ?: return null
        val age = contentValues.getAsInteger(COLUMN_USER_AGE) ?: return null
        val user = User(id, name, age)
        userPrefs.edit()
            .putString(id.toString(), userAdapter.toJson(user))
            .apply()
        return Uri.parse("content://$AUTHORITIES/$PATH_USERS/$id")
    }

    //New!
    private fun saveCourse(contentValues: ContentValues?): Uri? {
        val id = contentValues?.getAsLong(COLUMN_COURSE_ID) ?: return null
        val title = contentValues.getAsString(COLUMN_COURSE_TITLE) ?: return null
        val course = Course(id, title)
        coursesPrefs.edit()
            .putString(id.toString(), courseAdapter.toJson(course))
            .apply()
        return Uri.parse("content://$AUTHORITIES/$PATH_COURSES/$id")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        return when (uriMatcher.match(uri)) {
            TYPE_USER_ID -> deleteUser(uri)
            TYPE_COURSE_ID -> deleteCourse(uri)
            TYPE_COURSES -> deleteAllCourses()
            else -> 0
        }
    }

    private fun deleteUser(uri: Uri): Int {
        val userId = uri.lastPathSegment?.toLongOrNull()?.toString() ?: return 0
        return if (userPrefs.contains(userId)) {
            userPrefs.edit()
                .remove(userId)
                .apply()
            1
        } else {
            0
        }
    }

    //New!
    private fun deleteCourse(uri: Uri): Int {
        val courseId = uri.lastPathSegment?.toLongOrNull()?.toString() ?: return 0
        return if (coursesPrefs.contains(courseId)) {
            coursesPrefs.edit()
                .remove(courseId)
                .apply()
            1
        } else 0
    }

    //New!
    private fun deleteAllCourses(): Int {
        coursesPrefs.edit()
            .clear()
            .apply()
        return 1
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        values ?: return 0
        return when (uriMatcher.match(uri)) {
            TYPE_USER_ID -> updateUser(uri, values)
            TYPE_COURSE_ID -> updateCourse(uri, values)
            else -> 0
        }
    }

    private fun updateUser(uri: Uri, contentValues: ContentValues): Int {
        val userId = uri.lastPathSegment?.toLongOrNull()?.toString() ?: return 0
        return if (userPrefs.contains(userId)) {
            saveUser(contentValues)
            1
        } else {
            0
        }
    }

    //New!
    private fun updateCourse(uri: Uri, contentValues: ContentValues): Int {
        val courseId = uri.lastPathSegment?.toLongOrNull()?.toString() ?: return 0
        return if (coursesPrefs.contains(courseId)) {
            saveCourse(contentValues)
            1
        } else 0
    }

    companion object {
        private const val AUTHORITIES = "${BuildConfig.APPLICATION_ID}.provider"
        private const val PATH_USERS = "users"
        private const val PATH_COURSES = "courses"

        private const val TYPE_USERS = 1
        private const val TYPE_COURSES = 2
        private const val TYPE_USER_ID = 3
        private const val TYPE_COURSE_ID = 4

        private const val COLUMN_USER_ID = "id"
        private const val COLUMN_USER_NAME = "name"
        private const val COLUMN_USER_AGE = "age"

        private const val COLUMN_COURSE_ID = "id"
        private const val COLUMN_COURSE_TITLE = "title"
    }
}