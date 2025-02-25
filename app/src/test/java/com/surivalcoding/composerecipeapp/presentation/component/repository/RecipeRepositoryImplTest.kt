package com.surivalcoding.composerecipeapp.presentation.component.repository

import com.surivalcoding.composerecipeapp.presentation.component.datasource.RecipeDataSourceImpl
import com.surivalcoding.composerecipeapp.presentation.component.model.Recipe
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Test

class RecipeRepositoryImplTest {

    val recipeRepositoryImpl = RecipeRepositoryImpl(RecipeDataSourceImpl())

    @Test
    fun getRecipeTitles() = runBlocking {
        val recipeTitles = recipeRepositoryImpl.getRecipeTitles()

        assertTrue(recipeTitles.size == 10)
    }

    @Test
    fun getChefNames() {
    }

    @Test
    fun getCookingTimes() {
    }

    @Test
    fun getRatings() = runBlocking {
        val recipeRates = recipeRepositoryImpl.getRatings()

        assertTrue(recipeRates.size == 10)

        recipeRates.forEachIndexed { index, value ->
            println("index: $index, value: $value")
        }
    }

    @Test
    fun getDataCount() {
        val recipeDataCount = recipeRepositoryImpl.getDataCount()

        assertEquals(10, recipeDataCount)
    }

    @Test
    fun getSearchedRecipes() {
        val recipeDataSearched = recipeRepositoryImpl.getSearchedRecipes("spi")


        //assertEquals(3, recipeDataSearched.size)
    }
}