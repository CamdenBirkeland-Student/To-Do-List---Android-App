package com.example.todolistapp

import android.content.ContentValues
import android.content.Context

class TaskRepository(context: Context) {

    private val dbHelper = TaskDatabaseHelper(context)

    fun addTask(task: Task): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(TaskDatabaseHelper.COLUMN_NAME, task.name)
            put(TaskDatabaseHelper.COLUMN_IS_COMPLETED, task.isCompleted)
        }
        return db.insert(TaskDatabaseHelper.TABLE_NAME, null, values)
    }

    fun getTasks(): List<Task> {
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            TaskDatabaseHelper.TABLE_NAME,
            null, null, null, null, null, null
        )
        val tasks = mutableListOf<Task>()
        with(cursor) {
            while (moveToNext()) {
                val id = getLong(getColumnIndexOrThrow(TaskDatabaseHelper.COLUMN_ID))
                val name = getString(getColumnIndexOrThrow(TaskDatabaseHelper.COLUMN_NAME))
                val isCompleted = getInt(getColumnIndexOrThrow(TaskDatabaseHelper.COLUMN_IS_COMPLETED)) > 0
                tasks.add(Task(id, name, isCompleted))
            }
            close()
        }
        return tasks
    }

    fun updateTask(task: Task): Int {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(TaskDatabaseHelper.COLUMN_NAME, task.name)
            put(TaskDatabaseHelper.COLUMN_IS_COMPLETED, task.isCompleted)
        }
        return db.update(
            TaskDatabaseHelper.TABLE_NAME,
            values,
            "${TaskDatabaseHelper.COLUMN_ID} = ?",
            arrayOf(task.id.toString())
        )
    }

    fun deleteTask(id: Long): Int {
        val db = dbHelper.writableDatabase
        return db.delete(
            TaskDatabaseHelper.TABLE_NAME,
            "${TaskDatabaseHelper.COLUMN_ID} = ?",
            arrayOf(id.toString())
        )
    }
}