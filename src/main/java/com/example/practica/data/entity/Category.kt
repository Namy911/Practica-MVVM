package com.example.practica.data.entity

import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.practica.data.db.TaskSchema.CategoryTable.Companion.ROW_DESC
import com.example.practica.data.db.TaskSchema.CategoryTable.Companion.ROW_ID
import com.example.practica.data.db.TaskSchema.CategoryTable.Companion.ROW_NAME
import com.example.practica.data.db.TaskSchema.CategoryTable.Companion.TABLE_NAME
import kotlinx.android.parcel.Parcelize
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged

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
    interface Store{

      @Query("SELECT * FROM $TABLE_NAME")
      fun loadAll(): Flow<List<Category>>

        fun loadAllDistinct() =
            loadAll().distinctUntilChanged()

        @Query("SELECT * FROM $TABLE_NAME WHERE _id = :id LIMIT 1")
        fun loadCategory(id: Int): LiveData<Category>

    }
}