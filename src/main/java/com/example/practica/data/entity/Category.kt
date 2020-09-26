package com.example.practica.data.entity

import android.os.Parcelable
import androidx.room.*
import com.example.practica.data.db.TaskSchema.CategoryTable.Companion.ROW_DESC
import com.example.practica.data.db.TaskSchema.CategoryTable.Companion.ROW_ID
import com.example.practica.data.db.TaskSchema.CategoryTable.Companion.ROW_NAME
import com.example.practica.data.db.TaskSchema.CategoryTable.Companion.TABLE_NAME
import kotlinx.android.parcel.Parcelize

@Entity(tableName = TABLE_NAME)
@Parcelize
data class Category(
    @ColumnInfo(name = ROW_NAME)
    var name: String,

    @ColumnInfo(name = ROW_DESC)
    var desc: String,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ROW_ID, index = true)
    var id: Int = 0
) : Parcelable {
    @Dao
    interface Store{}
}