package ru.skillbox.contentprovider.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.provider.ContactsContract
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.regex.Pattern

class ContactRepository(
    private val context: Context
) {

    private val phonePattern = Pattern.compile("^\\+?[0-9]{3}-?[0-9]{6,12}\$")

    suspend fun saveContact(name: String, phone: String) = withContext(Dispatchers.IO) {
        if (phonePattern.matcher(phone).matches().not() || name.isBlank()) {
            throw IncorrectFormException()
        }
        val contactId = saveRawContact()
        saveContactName(contactId, name)
        saveContactPhone(contactId, phone)
    }

    private fun saveRawContact(): Long {
        val uri = context.contentResolver.insert(ContactsContract.RawContacts.CONTENT_URI, ContentValues())
        Log.d("saveRawContact", "uri = $uri")
        return uri?.lastPathSegment?.toLongOrNull() ?: error("cannot save raw contact")
    }

    private fun saveContactName(contactId: Long, name: String) {
        val contentValues = ContentValues().apply {
            put(ContactsContract.Data.RAW_CONTACT_ID, contactId)
            put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
            put(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, name)
        }
        context.contentResolver.insert(ContactsContract.Data.CONTENT_URI, contentValues)
    }

    private fun saveContactPhone(contactId: Long, phone: String) {
        val contentValues = ContentValues().apply {
            put(ContactsContract.Data.RAW_CONTACT_ID, contactId)
            put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
            put(ContactsContract.CommonDataKinds.Phone.NUMBER, phone)
        }
        context.contentResolver.insert(ContactsContract.Data.CONTENT_URI, contentValues)
    }

    suspend fun getAllContacts(): List<Contact> = withContext(Dispatchers.IO) {
        context.contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            null,
            null,
            null,
            null
        )?.use {
            getContactsFromCursor(it)
        }.orEmpty()
    }

    private fun getContactsFromCursor(cursor: Cursor): List<Contact> {
        if(cursor.moveToFirst().not()) return emptyList()
        val list = mutableListOf<Contact>()
        do {
            val nameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY)
            val name = cursor.getString(nameIndex).orEmpty()

            val idIndex = cursor.getColumnIndex(ContactsContract.Contacts._ID)
            val id = cursor.getLong(idIndex)

            list.add(Contact(id = id, name = name, phones = getPhonesForContact(id)))
        } while (cursor.moveToNext())

        return list
    }

    private fun getPhonesForContact(contactId: Long): List<String> {
        return context.contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            "${ContactsContract.CommonDataKinds.Phone.CONTACT_ID} = ?",
            arrayOf(contactId.toString()),
            null
        )?.use {
            getPhonesFromCursor(it)
        }.orEmpty()
    }

    private fun getPhonesFromCursor(cursor: Cursor): List<String> {
        if(cursor.moveToFirst().not()) return emptyList()
        val list = mutableListOf<String>()
        do {
            val numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
            val number = cursor.getString(numberIndex)
            list.add(number)
        } while (cursor.moveToNext())

        return list
    }
}