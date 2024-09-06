package com.altatec.myapplication.data.local.entity

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contactTable")
data class Contact(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "contactId")
    val id: Long,
    @ColumnInfo(name = "contactPhoto")
    val photo: Bitmap,
    @ColumnInfo(name = "contactName")
    val name: String,
    @ColumnInfo(name = "contactBirthday")
    val birthday: String,
    @ColumnInfo(name = "contactAddress")
    val address: String,
    @ColumnInfo(name = "contactPhone")
    val phone: String,
    @ColumnInfo(name = "contactHobby")
    val hobby: String
)

object DummyContact {
    val contact = Contact(
        id = 2,
        photo = Bitmap.createBitmap(100,100,Bitmap.Config.ARGB_8888),
        name = "Alberto",
        birthday = "05-11-1994",
        address = "Angel #123",
        phone = "1234567890",
        hobby = "None"
    )
}

object DummyContactList {
    val contactList = listOf(
        Contact(
            id = 0,
            photo = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888),
            name = "Alberto",
            birthday = "05/11/94",
            address = "Male",
            phone = "1234567890",
            hobby = "None"
        ),
        Contact(
            id = 1,
            photo = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888),
            name = "Alberto",
            birthday = "05/11/94",
            address = "Male",
            phone = "1234567890",
            hobby = "None"
        ),
        Contact(
            id = 2,
            photo = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888),
            name = "Alberto",
            birthday = "05/11/94",
            address = "Male",
            phone = "1234567890",
            hobby = "None"
        )
    )
}
