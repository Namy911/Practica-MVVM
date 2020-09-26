package com.example.practica.data.entity

import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.practica.data.db.TaskSchema
import com.example.practica.data.db.TaskSchema.ArticlesTable.Companion.ROW_CATEGORY_ID
import com.example.practica.data.db.TaskSchema.ArticlesTable.Companion.ROW_CONTENT
import com.example.practica.data.db.TaskSchema.ArticlesTable.Companion.ROW_DESC
import com.example.practica.data.db.TaskSchema.ArticlesTable.Companion.ROW_ID
import com.example.practica.data.db.TaskSchema.ArticlesTable.Companion.ROW_TITLE
import com.example.practica.data.db.TaskSchema.ArticlesTable.Companion.ROW_USER_ID
import com.example.practica.data.db.TaskSchema.ArticlesTable.Companion.TABLE_NAME
import kotlinx.android.parcel.Parcelize
import kotlinx.coroutines.flow.Flow

@Entity(
    tableName = TABLE_NAME,
    foreignKeys = [
        ForeignKey(
            entity = Category::class,
            parentColumns = [TaskSchema.CategoryTable.ROW_ID],
            childColumns = [ROW_CATEGORY_ID],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = User::class,
            parentColumns = [TaskSchema.UserTable.ROW_ID],
            childColumns = [ROW_USER_ID],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )]
)
@Parcelize
data class Article(
    @ColumnInfo(name = ROW_TITLE)
    var title: String,

    @ColumnInfo(name = ROW_DESC)
    var desc: String,

    @ColumnInfo(name = ROW_CONTENT)
    var content: String,

    @ColumnInfo(name = ROW_USER_ID, index = true)
    var userId: Int,

    @ColumnInfo(name = ROW_CATEGORY_ID, index = true)
    var categoryId: Int,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ROW_ID)
    var id: Int = 0
) : Parcelable {
    @Dao
    interface Store{

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun save(article: Article)

        @Update(onConflict = OnConflictStrategy.REPLACE)
        suspend fun update(article: Article)

        @Delete
        suspend fun delete(article: Article)

        @Query("SELECT * FROM `articles` WHERE `art_id` = :id")
        fun loadArticle(id: Int ): LiveData<Article>


    }
}