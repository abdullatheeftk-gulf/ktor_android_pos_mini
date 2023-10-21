package com.gulftechinnovations

import com.gulftechinnovations.model.AdminUser
import com.gulftechinnovations.data.admin.AdminUserDao
import com.gulftechinnovations.data.admin.AdminUserDaoImpl
import com.gulftechinnovations.data.category.CategoryDao
import com.gulftechinnovations.data.category.CategoryDaoImpl
import com.gulftechinnovations.data.multi_product.MultiProductDaoImpl
import com.gulftechinnovations.data.product.ProductDaoImpl
import com.gulftechinnovations.data.sub_category.SubCategoryDaoImpl
import com.gulftechinnovations.data.user.UserDao
import com.gulftechinnovations.data.user.UserDaoImpl
import com.gulftechinnovations.db.DatabaseFactory
import com.gulftechinnovations.model.Category
import com.gulftechinnovations.plugins.*
import io.ktor.server.application.*
import kotlinx.coroutines.runBlocking

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    DatabaseFactory.init(config = environment.config)

    val userDao: UserDao by lazy {
        UserDaoImpl()
    }
    val adminUserDao: AdminUserDao by lazy {
        AdminUserDaoImpl()
    }

    val categoryDao: CategoryDao by lazy {
        CategoryDaoImpl()
    }

    val subCategoryDao by lazy {
        SubCategoryDaoImpl()
    }

    val productDao by lazy {
        ProductDaoImpl()
    }

    val multiProductDao by lazy {
        MultiProductDaoImpl()
    }

    registerAdminUser(adminUserDao = adminUserDao)

    insertAllAndFavouriteToCategoryDatabase(categoryDao = categoryDao)


    configureSerialization()
    //configureDatabases()
    configureHTTP()
    configureSecurity()
    configureRouting(
        userDao = userDao,
        adminUserDao = adminUserDao,
        categoryDao = categoryDao,
        subCategoryDao = subCategoryDao,
        productDao = productDao,
        multiProductDao = multiProductDao
    )
}

fun registerAdminUser(adminUserDao: AdminUserDao) {
    runBlocking {
        val adminUser = adminUserDao.getOneAdminUser(adminId = 1)
        if (adminUser == null) {
            adminUserDao.insertAdminUser(
                AdminUser(
                    adminName = "admin",
                    adminPassword = "741",
                    licenseKey = "key"
                )
            )
        }
    }
}

fun insertAllAndFavouriteToCategoryDatabase(categoryDao: CategoryDao) {
    runBlocking {
        try {
            val categoryAll = categoryDao.getOneCategoryByName("All")
            if (categoryAll == null) {
                categoryDao.insertCategory(
                    Category(
                        categoryName = "All"
                    )
                )
            }
            val categoryFavourite = categoryDao.getOneCategoryByName("Favourite")
            if (categoryFavourite == null) {
                categoryDao.insertCategory(
                    Category(
                        categoryName = "Favourite"
                    )
                )
            }

        } catch (_: Exception) {

        }
    }
}
