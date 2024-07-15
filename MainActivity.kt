package com.example.todolistapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var taskRepository: TaskRepository
    private lateinit var taskAdapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        taskRepository = TaskRepository(this)
        taskAdapter = TaskAdapter(mutableListOf(), taskRepository)

        findViewById<RecyclerView>(R.id.recyclerView).apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = taskAdapter
        }

        val buttonAdd = findViewById<Button>(R.id.buttonAdd)
        val editTextTask = findViewById<EditText>(R.id.editTextTask)

        buttonAdd.setOnClickListener {
            val taskName = editTextTask.text.toString()
            if (taskName.isNotEmpty()) {
                val task = Task(name = taskName)
                taskRepository.addTask(task)
                taskAdapter.addTask(task)
                editTextTask.text.clear()
            }
        }

        loadTasks()
    }

    private fun loadTasks() {
        val tasks = taskRepository.getTasks()
        taskAdapter.setTasks(tasks)
    }
}