package com.example
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import javax.inject.Inject

@MicronautTest
open class TransactiontestgradleTest {

    @Inject
    lateinit var handler: TransactionHandler

    @Inject
    lateinit var repo: EntityRepository

    @Test
    fun test1() {
        val first = repo.findAll().map { it.id }
        try {
            handler.doTransaction {
                repo.save(Entity(null))
                throw Exception("not here")
            }
        } catch(e: Exception) {}
        assertEquals(first, repo.findAll().map { it.id })
    }

    @Test
    fun test2() {
        val first = repo.findAll().map { it.id }
        try { handler.doFixedTransaction() } catch(e: Exception) { }
        assertEquals(first, repo.findAll().map { it.id })
    }

    @Test
    fun test3() {
        val first = repo.findAll().map { it.id }
        try { handler.useManager() } catch(e: Exception) { }
        assertEquals(first, repo.findAll().map { it.id })
    }

    @Test
    fun test4() {
        val first = repo.findAll().map { it.id }
        try { handler.useManager2() } catch(e: Exception) { }
        assertEquals(first, repo.findAll().map { it.id })
    }
}
