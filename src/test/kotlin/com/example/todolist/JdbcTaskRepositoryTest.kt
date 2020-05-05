package com.example.todolist

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class JdbcTaskRepositoryTest {

    @Autowired
    private lateinit var sut: JdbcTaskRepository
}