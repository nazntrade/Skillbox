package com.example.hw_contentprovider.contacts.data

import android.content.ContentProviderOperation
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.provider.ContactsContract
import android.util.Log
import com.example.hw_contentprovider.contacts.detailInfo.DetailContactInfoFragmentArgs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.regex.Pattern

class ContactsRepository(
    private val context: Context
) {
    private val phonePattern = Pattern.compile("^\\+?[0-9]{3}-?[0-9]{6,12}\$")
    private val emailPattern = Pattern.compile("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")

    suspend fun saveContact(firstName: String, secondName: String, phone: String, email: String) {
        withContext(Dispatchers.IO) {
            if (phonePattern.matcher(phone).matches().not()
                || firstName.isBlank()
            ) {
                throw IncorrectFormException()
            }
            val contactId = saveRowContact()
            saveContactFirstName(contactId, firstName, secondName)
            saveContactPhone(contactId, phone)
            saveContactEmail(contactId, email)
        }
    }

    private fun saveRowContact(): Long {
        val uri = context.contentResolver.insert(
            ContactsContract.RawContacts.CONTENT_URI,
            ContentValues()
        )
        Log.d("saveRawContact", "uri = $uri")
        return uri?.lastPathSegment?.toLongOrNull() ?: error("cannot save raw contact")
    }

    private fun saveContactFirstName(contactId: Long, firstName: String, secondName: String) {
        val contentValues = ContentValues().apply {
            put(ContactsContract.Data.RAW_CONTACT_ID, contactId)
            put(
                ContactsContract.Data.MIMETYPE,
                ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE
            )
            put(
                ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
                "$firstName $secondName"
            )
        }
        context.contentResolver.insert(ContactsContract.Data.CONTENT_URI, contentValues)
    }

    private fun saveContactPhone(contactId: Long, phone: String) {
        val contentValues = ContentValues().apply {
            put(ContactsContract.Data.RAW_CONTACT_ID, contactId)
            put(
                ContactsContract.Data.MIMETYPE,
                ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE
            )
            put(ContactsContract.CommonDataKinds.Phone.NUMBER, phone)
        }
        context.contentResolver.insert(ContactsContract.Data.CONTENT_URI, contentValues)
    }

    private fun saveContactEmail(contactId: Long, email: String) {
        if (email.isBlank() || emailPattern.matcher(email).matches()) {
            val contentValues = ContentValues().apply {
                put(ContactsContract.Data.RAW_CONTACT_ID, contactId)
                put(
                    ContactsContract.Data.MIMETYPE,
                    ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE
                )
                put(ContactsContract.CommonDataKinds.Email.ADDRESS, email)
            }
            context.contentResolver.insert(ContactsContract.Data.CONTENT_URI, contentValues)
        } else {
            throw IncorrectFormException()
        }
    }

    suspend fun deleteContact(args: DetailContactInfoFragmentArgs) {
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
        if (cursor.moveToFirst().not()) return emptyList()
        val list = mutableListOf<Contact>()
        do {
            val nameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY)
            val name = cursor.getString(nameIndex).orEmpty()

            val idIndex = cursor.getColumnIndex(ContactsContract.Contacts._ID)
            val id = cursor.getLong(idIndex)

            list.add(Contact(id = id, name = name, getPhonesContacts(id), getEmailContact(id)))
        } while (cursor.moveToNext())
        return list
    }

    private fun getEmailContact(contactId: Long): List<String> {
        return context.contentResolver.query(
            ContactsContract.CommonDataKinds.Email.CONTENT_URI,
            null,
            "${ContactsContract.CommonDataKinds.Email.CONTACT_ID} = ?",
            arrayOf(contactId.toString()),
            null
        )?.use {
            getEmailFromCursor(it)
        }.orEmpty()
    }

    private fun getEmailFromCursor(cursor: Cursor): List<String> {
        if (cursor.moveToFirst().not()) return emptyList()
        val list = mutableListOf<String>()
        do {
            val emailIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS)
            val email = cursor.getString(emailIndex)
            list.add(email)
        } while (cursor.moveToNext())
        return list
    }

    private fun getPhonesContacts(contactId: Long): List<String> {
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
        if (cursor.moveToFirst().not()) return emptyList()
        val list = mutableListOf<String>()
        do {
            val numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
            val number = cursor.getString(numberIndex)
            list.add(number)
        } while (cursor.moveToNext())
        return list
    }
}