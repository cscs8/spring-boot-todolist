package com.example.todolist

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository

@Repository
class InMemoryTaskRepository : TaskRepository {
    private val tasks: MutableList<Task> = mutableListOf()

    private val maxId: Long
        get() = tasks.map(Task::id).max() ?: 0

    override fun create(content: String): Task {
        val id = maxId + 1
        val task = Task(id, content, false)
        tasks += task
        return task
    }

    override fun update(task: Task) {
        tasks.replaceAll {
            if (it.id == task.id) task
            else it
        }
    }

    // 元のリストを破壊されないように、コピーして新しいリストを返す
    override fun findAll(): List<Task> = tasks.toList()


    override fun findById(id: Long): Task? =
            tasks.find { it.id == id }

}