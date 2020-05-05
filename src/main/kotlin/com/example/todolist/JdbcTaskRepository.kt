package com.example.todolist

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.simple.SimpleJdbcInsert
import org.springframework.stereotype.Repository


@Repository
class JdbcTaskRepository(private val jdbcTemplate: JdbcTemplate) : TaskRepository {
    private val rowMapper = RowMapper<Task> { rs, _ ->
        Task(rs.getLong("id"),
                rs.getString("content"),
                rs.getBoolean("done"))
    }

    override fun create(content: String): Task {
        val simpleJdbcInsert = jdbcTemplate.dataSource?.let(::SimpleJdbcInsert)
        simpleJdbcInsert?.withTableName("task")
                ?.usingGeneratedKeyColumns("id")

        val parameters = mapOf<String, Any>("content" to content, "done" to false)
        val id: Long? = simpleJdbcInsert?.executeAndReturnKey(parameters)?.toLong()

//        PreparedStatementCreatorFactory("INSERT INTO task(content) VALUES(?)", listOf(content))
//        val preparedStatementCreator = SimplePreparedStatementCreator()
//        val keyHolder = GeneratedKeyHolder()
//        jdbcTemplate.update({
//            val ps = it.prepareStatement("INSERT INTO task(content) VALUES(?)")
//            ps.setString(1, content)
//            ps
//        }, keyHolder)
//        jdbcTemplate.update("INSERT INTO task(content) VALUES(?)", content)
//        val id: Long? = jdbcTemplate.queryForObject("SELECT last_insert_id()")
//        val id: Long? = keyHolder.key?.toLong()
        return Task(id!!, content, false)
    }

    override fun update(task: Task) {
        jdbcTemplate.update("UPDATE task SET content = ?, done = ? WHERE id = ?",
                task.content,
                task.done,
                task.id)
    }

    override fun findAll(): List<Task> =
            jdbcTemplate.query("SELECT id, content, done FROM task", rowMapper)

    override fun findById(id: Long): Task? =
            jdbcTemplate.query("SELECT id, content, done FROM task WHERE id = ?", rowMapper, id)
                    .firstOrNull()
}