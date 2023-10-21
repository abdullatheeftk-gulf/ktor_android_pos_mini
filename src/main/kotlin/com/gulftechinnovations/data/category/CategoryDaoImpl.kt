package com.gulftechinnovations.data.category

import com.gulftechinnovations.db.database_models.CategoryTable
import com.gulftechinnovations.db.database_models.ProductTable
import com.gulftechinnovations.db.database_models.UserTable
import com.gulftechinnovations.db.dbQuery
import com.gulftechinnovations.db.resultRowToCategory
import com.gulftechinnovations.model.Category
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class CategoryDaoImpl:CategoryDao {

    override suspend fun insertCategory(category: Category):Int {
        return dbQuery {
            CategoryTable.insert {
                it[categoryName] = category.categoryName
                it[noOfTimesOrdered] = category.noOfTimesOrdered
            }[CategoryTable.id].value
        }
    }

    override suspend fun getOneCategoryById(categoryId: Int): Category? {
        return dbQuery {
            CategoryTable.select { CategoryTable.id eq categoryId }
        }.map {
            CategoryTable.resultRowToCategory(it)
        }.singleOrNull()
    }

    override suspend fun getOneCategoryByName(categoryName: String): Category? {
        return dbQuery {
            CategoryTable.select { CategoryTable.categoryName eq categoryName }
        }.map {
            CategoryTable.resultRowToCategory(it)
        }.singleOrNull()
    }

    override suspend fun getAllCategory(
        sortOrderById: Pair<Column<EntityID<Int>>, SortOrder>?,
        sortOrderByCategoryName: Pair<Column<String>, SortOrder>?,
        sortOrderByNoOfTimesOrdered: Pair<Column<Int>, SortOrder>?
    ): List<Category> {

        if (sortOrderById!=null){
            return dbQuery {
                CategoryTable.selectAll().orderBy(sortOrderById).map {
                    CategoryTable.resultRowToCategory(it)
                }
            }
        }
        else if (sortOrderByCategoryName!=null){
            return dbQuery {
                CategoryTable.selectAll().orderBy(sortOrderByCategoryName).map {
                    CategoryTable.resultRowToCategory(it)
                }
            }
        }
        else if(sortOrderByNoOfTimesOrdered!=null){
            return dbQuery {
                CategoryTable.selectAll().orderBy(sortOrderByNoOfTimesOrdered).map {
                    CategoryTable.resultRowToCategory(it)
                }
            }
        }else{
            return dbQuery {
                CategoryTable.selectAll().map {
                    CategoryTable.resultRowToCategory(it)
                }
            }
        }
    }


    override suspend fun updateACategory(category: Category):Int {
        return dbQuery {
             CategoryTable.update({
                UserTable.id eq category.categoryId
            }) {
                it[id] = category.categoryId
                it[categoryName] = category.categoryName
                it[noOfTimesOrdered] = category.noOfTimesOrdered
            }
        }
    }

    override suspend fun updateACategoryNoOfTimesOrdered(categoryId: Int) {
        dbQuery {
            val value = CategoryTable.select { CategoryTable.id eq categoryId }.map {
                CategoryTable.resultRowToCategory(it)
            }.singleOrNull()?.noOfTimesOrdered ?:0

            CategoryTable.update (
                { CategoryTable.id eq categoryId}
            ){
                it[noOfTimesOrdered] = value+1
            }

        }
    }

    override suspend fun deleteACategory(categoryId: Int) {
        dbQuery {
            CategoryTable.deleteWhere {
                id eq categoryId
            }
        }
    }
}