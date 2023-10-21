package com.gulftechinnovations.routes

import com.gulftechinnovations.data.product.ProductDao
import com.gulftechinnovations.model.Product
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Routing.productRoutes(
    productDao: ProductDao
) {
    route("/product") {

        post("/addAProduct") {
            try {
                val product = call.receive<Product>()
                val value = productDao.insertProduct(product = product)
                call.respond(HttpStatusCode.Created, value)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, e.message ?: "There have some problem")
            }

        }

        get("/getAllProduct") {
            try {
                val products = productDao.getAllProducts()
                call.respond(products)
            }catch (e:Exception){
                call.respond(HttpStatusCode.ExpectationFailed, e.message ?: "There have some problem")
            }
        }

        get("/getProductsByCategory/{categoryId}") {
            try {
                val categoryId = call.parameters["categoryId"]?.toInt() ?: throw BadRequestException("Invalid categoryId")
                val products = productDao.getProductsByCategoryId(categoryId = categoryId)
                call.respond(products)
            }catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, e.message ?: "There have some problem")
            }
        }

        post("/updateAProduct") {
            try {
                val product = call.receive<Product>()
                productDao.updateAProduct(product = product)
                call.respond("updated")
            }catch (e:Exception){
                call.respond(HttpStatusCode.BadRequest, e.message ?: "There have some problem")

            }
        }

        delete("/deleteAProduct/{id}") {
            try {
                val productId = call.parameters["id"]?.toInt() ?: throw Exception("There have some problem")
                productDao.deleteAProduct(productId = productId)
                call.respond("deleted")
            }catch (e:Exception){
                call.respond(HttpStatusCode.BadRequest, e.message ?: "There have some problem")
            }
        }
    }

}