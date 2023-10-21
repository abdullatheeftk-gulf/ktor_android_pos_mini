package com.gulftechinnovations.db.database_models


import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption

object MultiProductTable:IntIdTable() {
    val parentProductId = integer("product_id_ref").references(ref = ProductTable.id, onDelete = ReferenceOption.CASCADE)
    val multiProductName = varchar(name = "multiProductName", length = 1024).uniqueIndex()
    val multiProductImage = varchar(name = "multiProductImage", length = 1024).nullable()
    val noOfTimesOrdered = integer(name = "noOfTimeOrdered").default(defaultValue = 0)
    val info = varchar(name = "info", length = 1024).nullable()
}