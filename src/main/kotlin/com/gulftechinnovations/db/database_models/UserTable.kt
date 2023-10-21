package com.gulftechinnovations.db.database_models

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Table.Dual.uniqueIndex

object UserTable:IntIdTable(){
    val userName = varchar(name = "userName", length = 128).uniqueIndex()
    val userPassword = varchar(name = "userPassword", length = 32)
}
