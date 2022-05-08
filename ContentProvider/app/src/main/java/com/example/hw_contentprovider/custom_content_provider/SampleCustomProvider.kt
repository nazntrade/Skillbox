package com.example.hw_contentprovider.custom_content_provider

import android.content.*
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.net.Uri
import android.text.TextUtils
import android.util.Log


class MyContactsProvider : ContentProvider() {
    private val LOG_TAG = "myLogs"

    companion object {
        // // Константы для БД
        // БД
        const val DB_NAME = "mydb"
        const val DB_VERSION = 1

        // Таблица
        const val CONTACT_TABLE = "contacts"

        // Поля
        const val CONTACT_ID = "_id"
        const val CONTACT_NAME = "name"
        const val CONTACT_EMAIL = "email"

        // Скрипт создания таблицы
        const val DB_CREATE = ("create table " + CONTACT_TABLE + "("
                + CONTACT_ID + " integer primary key autoincrement, "
                + CONTACT_NAME + " text, " + CONTACT_EMAIL + " text" + ");")

        // // Uri
        // authority
        private const val AUTHORITY = "ru.startandroid.providers.AdressBook"

        // path
        private const val CONTACT_PATH = "contacts"

        // Общий Uri
        val CONTACT_CONTENT_URI: Uri = Uri.parse(
            "content://"
                    + AUTHORITY + "/" + CONTACT_PATH
        )

        // Типы данных
        // набор строк
        const val CONTACT_CONTENT_TYPE = ("vnd.android.cursor.dir/vnd."
                + AUTHORITY + "." + CONTACT_PATH)

        // одна строка
        const val CONTACT_CONTENT_ITEM_TYPE = ("vnd.android.cursor.item/vnd."
                + AUTHORITY + "." + CONTACT_PATH)

        //// UriMatcher
        // общий Uri
        const val URI_CONTACTS = 1

        // Uri с указанным ID
        const val URI_CONTACTS_ID = 2

        // описание и создание UriMatcher
        private var uriMatcher: UriMatcher? = null

        init {
            uriMatcher = UriMatcher(UriMatcher.NO_MATCH)
            uriMatcher!!.addURI(AUTHORITY, CONTACT_PATH, URI_CONTACTS)
            uriMatcher!!.addURI(AUTHORITY, "$CONTACT_PATH/#", URI_CONTACTS_ID)
        }
    }

    private var dbHelper: DBHelper? = null
    var db: SQLiteDatabase? = null
    override fun onCreate(): Boolean {
        Log.d(LOG_TAG, "onCreate")
        dbHelper = DBHelper(context)
        return true
    }

    // чтение
    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        var selection = selection
        var sortOrder = sortOrder
        Log.d(LOG_TAG, "query, $uri")
        when (uriMatcher!!.match(uri)) {
            URI_CONTACTS -> {
                Log.d(LOG_TAG, "URI_CONTACTS")
                // если сортировка не указана, ставим свою - по имени
                if (TextUtils.isEmpty(sortOrder)) {
                    sortOrder = "$CONTACT_NAME ASC"
                }
            }
            URI_CONTACTS_ID -> {
                val id = uri.lastPathSegment
                Log.d(LOG_TAG, "URI_CONTACTS_ID, $id")
                // добавляем ID к условию выборки
                selection = if (TextUtils.isEmpty(selection)) {
                    "$CONTACT_ID = $id"
                } else {
                    "$selection AND $CONTACT_ID = $id"
                }
            }
            else -> throw IllegalArgumentException("Wrong URI: $uri")
        }
        db = dbHelper!!.writableDatabase
        val cursor = db.query(
            CONTACT_TABLE, projection, selection,
            selectionArgs, null, null, sortOrder
        )
        // просим ContentResolver уведомлять этот курсор
        // об изменениях данных в CONTACT_CONTENT_URI
        cursor.setNotificationUri(
            context!!.contentResolver,
            CONTACT_CONTENT_URI
        )
        return cursor
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        Log.d(LOG_TAG, "insert, $uri")
        require(uriMatcher!!.match(uri) == URI_CONTACTS) { "Wrong URI: $uri" }
        db = dbHelper!!.writableDatabase
        val rowID = db.insert(CONTACT_TABLE, null, values)
        val resultUri = ContentUris.withAppendedId(CONTACT_CONTENT_URI, rowID)
        // уведомляем ContentResolver, что данные по адресу resultUri изменились
        context!!.contentResolver.notifyChange(resultUri, null)
        return resultUri
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        var selection = selection
        Log.d(LOG_TAG, "delete, $uri")
        when (uriMatcher!!.match(uri)) {
            URI_CONTACTS -> Log.d(LOG_TAG, "URI_CONTACTS")
            URI_CONTACTS_ID -> {
                val id = uri.lastPathSegment
                Log.d(LOG_TAG, "URI_CONTACTS_ID, $id")
                selection = if (TextUtils.isEmpty(selection)) {
                    "$CONTACT_ID = $id"
                } else {
                    "$selection AND $CONTACT_ID = $id"
                }
            }
            else -> throw IllegalArgumentException("Wrong URI: $uri")
        }
        db = dbHelper!!.writableDatabase
        val cnt = db.delete(CONTACT_TABLE, selection, selectionArgs)
        context!!.contentResolver.notifyChange(uri, null)
        return cnt
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        var selection = selection
        Log.d(LOG_TAG, "update, $uri")
        when (uriMatcher!!.match(uri)) {
            URI_CONTACTS -> Log.d(LOG_TAG, "URI_CONTACTS")
            URI_CONTACTS_ID -> {
                val id = uri.lastPathSegment
                Log.d(LOG_TAG, "URI_CONTACTS_ID, $id")
                selection = if (TextUtils.isEmpty(selection)) {
                    "$CONTACT_ID = $id"
                } else {
                    "$selection AND $CONTACT_ID = $id"
                }
            }
            else -> throw IllegalArgumentException("Wrong URI: $uri")
        }
        db = dbHelper!!.writableDatabase
        val cnt = db.update(CONTACT_TABLE, values, selection, selectionArgs)
        context!!.contentResolver.notifyChange(uri, null)
        return cnt
    }

    override fun getType(uri: Uri): String? {
        Log.d(LOG_TAG, "getType, $uri")
        when (uriMatcher!!.match(uri)) {
            URI_CONTACTS -> return CONTACT_CONTENT_TYPE
            URI_CONTACTS_ID -> return CONTACT_CONTENT_ITEM_TYPE
        }
        return null
    }

    inner class DBHelper(context: Context?) :
        SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
        override fun onCreate(db: SQLiteDatabase) {
            db.execSQL(DB_CREATE)
            val cv = ContentValues()
            for (i in 1..3) {
                cv.put(CONTACT_NAME, "name $i")
                cv.put(CONTACT_EMAIL, "email $i")
                db.insert(CONTACT_TABLE, null, cv)
            }
        }

        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}
    }
}