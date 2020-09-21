package com.example.practica.data.db

sealed class TaskSchema {
    companion object{
        const val DB_NAME: String = "task.db"
    }
    class UserTable{
        companion object{
            const val TABLE_NAME = "users"
            const val ROW_NAME = "name"
            const val ROW_AGE = "age"
            const val ROW_DATE = "date"
            const val ROW_ID = "_id"
        }
    }
    class CategoryTable{
        companion object{
            const val TABLE_NAME = "categories"
            const val ROW_NAME = "name"
            const val ROW_DESC = "desc"
            const val ROW_ID = "_id"
        }
    }
    class ArticlesTable{
        companion object{
            const val TABLE_NAME = "articles"
            const val ROW_TITLE = "art_title"
            const val ROW_DESC = "art_desc"
            const val ROW_CONTENT = "content"
            const val ROW_CATEGORY_ID = "category_id"
            const val ROW_USER_ID = "user_id"
            const val ROW_ID = "art_id"
        }
    }
}