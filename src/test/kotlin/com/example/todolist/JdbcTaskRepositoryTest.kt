package com.example.todolist

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class JdbcTaskRepositoryTest {

    @Autowired
    private lateinit var sut: JdbcTaskRepository

    @Test
    fun 何も作成しなければfindAllは空のリストを作成すること() {
        val got = sut.findAll()
        assertThat(got).isEmpty()
    }
}