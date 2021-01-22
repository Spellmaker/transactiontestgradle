package com.example

import io.micronaut.transaction.SynchronousTransactionManager
import java.sql.Connection
import javax.inject.Inject
import javax.inject.Singleton
import javax.transaction.Transactional

/** */
@Singleton
open class TransactionHandler(
  private val repo: EntityRepository
) {

  /** */
  @Inject
  lateinit var tm: SynchronousTransactionManager<Connection>

  /** */
  @Transactional
  open fun doTransaction(action: () -> Unit) {
    action()
  }

  /** */
  @Transactional
  open fun doFixedTransaction() {
    repo.save(Entity(null))
    throw Exception("not like this")
  }

  /** */
  open fun useManager() {
    tm.executeWrite {
      repo.save(Entity(null))
      throw Exception("stop it")
    }
  }

  /** */
  open fun useManager2() {
    tm.executeWrite {
      repo.save(Entity(null))
      it.setRollbackOnly()
    }
  }
}