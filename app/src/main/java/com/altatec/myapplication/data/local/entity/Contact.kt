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
