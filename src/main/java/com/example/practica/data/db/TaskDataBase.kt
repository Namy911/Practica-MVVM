package com.example.practica.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.practica.data.db.TaskSchema.UserTable.Companion.ROW_AGE
import com.example.practica.data.db.TaskSchema.UserTable.Companion.ROW_NAME
import com.example.practica.data.db.TaskSchema.UserTable.Companion.TABLE_NAME
import com.example.practica.data.entity.Article
import com.example.practica.data.entity.Category
import com.example.practica.data.entity.User
import com.example.practica.data.entity.UserAndArticle


@Database(entities = [User::class, Article::class, Category::class], version = 1, exportSchema = false)
abstract class TaskDataBase: RoomDatabase(){

    abstract fun userSore(): User.Store
    abstract fun userAndArticleSore(): UserAndArticle.Store
}

class PrepopulateDatabase: RoomDatabase.Callback(){
    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        // Prepopulate Users Table
        db.execSQL("""
            INSERT INTO $TABLE_NAME($ROW_NAME, $ROW_AGE) VALUES ("User 1", 18), ("User 2", 19), ("User 3", 17);
        """.trimIndent())

        // Prepopulate Categories Table
        db.execSQL("""INSERT INTO ${TaskSchema.CategoryTable.TABLE_NAME}(${TaskSchema.CategoryTable.ROW_DESC}, ${TaskSchema.CategoryTable.ROW_NAME} )
                VALUES ("Category1 DESC", "Category NAME1"),
                       ("Category2 DESC", "Category NAME2"),
                       ("Category3 DESC", "Category NAME3");""".trimIndent())

        // Prepopulate Articles Table
        db.execSQL("""INSERT INTO ${TaskSchema.ArticlesTable.TABLE_NAME}(
            ${TaskSchema.ArticlesTable.ROW_DESC}, ${TaskSchema.ArticlesTable.ROW_CONTENT}, ${TaskSchema.ArticlesTable.ROW_TITLE},
            ${TaskSchema.ArticlesTable.ROW_USER_ID}, ${TaskSchema.ArticlesTable.ROW_CATEGORY_ID} )
            VALUES ("Articles1 DESC", "Articles1 Content 1", "Articles1 Title", 1, 1),
                   ("Articles2 DESC", "Articles2 Content 12", "Articles2 Title", 1, 2),
                   ("Articles3 DESC", "Articles3 Content 13", "Articles3 Title", 2, 3),
                   ("Articles4 DESC", "Articles4 Content 14", "Articles4 Title", 3, 1);""".trimIndent())
    }

}
