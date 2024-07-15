package com.example.todolistapp

data class Task(
    val id: Long = 0,
    val name: String,
    var isCompleted: Boolean = false
)