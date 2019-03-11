package com.zf.mart.mvp.bean

data class FoodBean(
        val category: List<Category>
)

data class Category(
        val id: String,
        val name: String,
        val food: List<Food>
)

data class Food(
        val id: String,
        val catId: String,
        val name: String,
        val price: String
)
