package com.example.todolist

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Controller
@RequestMapping("tasks")
class TaskController {
    @GetMapping("")
    fun index(model: Model): String {
        val tasks = listOf(
                Task(1, "障子をはりかえる", false),
                Task(2, "定期健診にいく", true)
        )
        model.addAttribute("tasks", tasks)
        return "tasks/index"
    }

}