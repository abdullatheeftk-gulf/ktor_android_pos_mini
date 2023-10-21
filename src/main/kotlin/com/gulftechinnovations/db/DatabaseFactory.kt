package com.gulftechinnovations.db

import com.gulftechinnovations.db.database_models.*
import io.ktor.server.config.*
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import java.io.File

object DatabaseFactory {

    fun init(config: ApplicationConfig) {
        val driverClassName = config.property("storage.driverClassName").getString()
        val jdbcURL = config.property("storage.jdbcURL").getString() +
                (config.propertyOrNull("storage.dbFilePath")?.getString()?.let {
                    File(it).canonicalFile.absolutePath
                } ?: "")
        //val database = Database.connect(createHikariDataSource(url = jdbcURL, driver = driverClassName))
        val database = Database.connect(url = jdbcURL, driver = driverClassName)
        transaction(database) {
            SchemaUtils.create(
                UserTable,
                AdminUserTable,
                CategoryTable,
                SubCategoryTable,
                ProductTable,
                MultiProductTable
            )
            //  SchemaUtils.drop(UserTable,AdminUserTable,CategoryTable,SubCategoryTable)
            // SchemaUtils.dropDatabase()
        }
    }

    /*private fun createHikariDataSource(
        url: String,
        driver: String
    ) = HikariDataSource(HikariConfig().apply {
        driverClassName = driver
        jdbcUrl = url
        maximumPoolSize = 3
        isAutoCommit = false
        transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        validate()
    })*/

}