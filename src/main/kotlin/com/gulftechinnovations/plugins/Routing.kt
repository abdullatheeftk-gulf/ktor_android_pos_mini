package com.gulftechinnovations.plugins

import com.gulftechinnovations.data.admin.AdminUserDao
import com.gulftechinnovations.data.category.CategoryDao
import com.gulftechinnovations.data.multi_product.MultiProductDao
import com.gulftechinnovations.data.product.ProductDao
import com.gulftechinnovations.data.sub_category.SubCategoryDao
import com.gulftechinnovations.data.user.UserDao
import com.gulftechinnovations.routes.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.http.content.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting(
    userDao: UserDao,
    adminUserDao: AdminUserDao,
    categoryDao: CategoryDao,
    subCategoryDao: SubCategoryDao,
    productDao:ProductDao,
    multiProductDao: MultiProductDao
) {

    routing {
        get("/") {
            call.respondText("Unipos Android pos")
        }


        get("/getAllAdminUsers") {
            val users = adminUserDao.getAllAdminUsers()
            call.respond(users)
        }


        adminRoutes(application = application, adminUserDao = adminUserDao)

        userRoutes(userDao = userDao, application = application)

        categoryRoutes(categoryDao = categoryDao, subCategoryDao = subCategoryDao)

        subCategoryRoutes(subCategoryDao = subCategoryDao)

        productRoutes(productDao = productDao)

        multiProductRoutes(multiProductDao = multiProductDao)










        authenticate("user") {
            get("/sample") {
                call.respond("Sample")
            }
        }


        // Static plugin. Try to access `/static/index.html`
        static("/static") {
            resources("static")
        }
    }
}
