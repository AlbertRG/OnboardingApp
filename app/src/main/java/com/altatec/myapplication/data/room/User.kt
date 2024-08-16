package com.altatec.myapplication.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "userTable")
data class User(
    @PrimaryKey
    @ColumnInfo(name = "userEmail")
    val email: String = "",
    @ColumnInfo(name = "userName")
    val name: String = "",
    @ColumnInfo(name = "userPassword")
    val password: String = "",
)
