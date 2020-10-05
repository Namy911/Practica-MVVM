package com.example.practica.data.entity

import android.os.Parcelable
import androidx.room.*
import com.example.practica.data.db.TaskSchema.UserTable.Companion.ROW_AGE
import com.example.practica.data.db.TaskSchema.UserTable.Companion.ROW_ID
import com.example.practica.data.db.TaskSchema.UserTable.Companion.ROW_NAME
import com.example.practica.data.db.TaskSchema.UserTable.Companion.TABLE_NAME
import kotlinx.android.parcel.Parcelize
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged


@Entity(tableName = TABLE_NAME)
@Parcelize
data class User(
    @ColumnInfo(name = ROW_NAME)
    val name: String,

    @ColumnInfo(name = ROW_AGE)
    val age: Int,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ROW_ID, index = true)
    var id: Int = 0
): Parcelable {
    @Dao
    interface Store {

        @Query("SELECT * FROM $TABLE_NAME")
        fun loadAll(): Flow<List<User>>

        fun loadAllDistinct() =
            loadAll().distinctUntilChanged()
    }
}