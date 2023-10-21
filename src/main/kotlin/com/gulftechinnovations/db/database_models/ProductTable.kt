package com.gulftechinnovations.db.database_models

import org.jetbrains.exposed.dao.id.IntIdTable

object ProductTable:IntIdTable() {
    val productName = varchar(name = "productName", length = 1024).uniqueIndex()
    val productPrice = float(name = "productPrice")
    val productTaxInPercentage = float(name="productTaxInPercentage")
    val productImage = varchar(name = "productImage", length = 1024).nullable()
    val noOfTimesOrdered = integer(name = "noOfTimeOrdered").default(defaultValue = 0)
    val info = varchar(name = "info", length = 1024).nullable()
    val subCategoryId = integer(name = "subCategory_id_ref").references(ref = SubCategoryTable.id).nullable()
}