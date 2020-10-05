package com.example.practica.data.db.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.practica.data.db.TaskSchema
import com.example.practica.data.db.TaskSchema.ArticlesTable.Companion.ROW_DATE
import com.example.practica.data.db.TaskSchema.ArticlesTable.Companion.ROW_IMG
import com.example.practica.data.db.TaskSchema.ArticlesTable.Companion.TABLE_NAME

class Migration1To2: Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.run {
            execSQL("BEGIN TRANSACTION")
            execSQL("""
                    ALTER TABLE $TABLE_NAME ADD COLUMN $ROW_DATE 
            """.trimIndent())

            execSQL("""
                    ALTER TABLE $TABLE_NAME ADD COLUMN $ROW_IMG
            """.trimIndent())

//            execSQL("""
//                INSERT INTO $TABLE_NAME (${ROW_DATE})
//                VALUES (1601640082), (1601640082), (1601640082), (1601640082), (1601640082)
//            """.trimIndent())
            execSQL("COMMIT")
        }
    }

}