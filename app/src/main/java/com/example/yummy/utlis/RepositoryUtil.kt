package com.example.yummy.utlis

import android.content.Context
import com.example.yummy.data.repository.*
import com.example.yummy.data.source.local.MealLocalDataSource
import com.example.yummy.data.source.local.NoteLocalDataSource
import com.example.yummy.data.source.local.dao.FavoriteDaoImpl
import com.example.yummy.data.source.local.dao.NoteDaoImp
import com.example.yummy.data.source.local.db.AppDatabase
import com.example.yummy.data.source.remote.*
import com.example.yummy.ui.meallist.MealListFragment

object RepositoryUtils {
    fun getMealRepository(context: Context): MealRepository {
        val database = AppDatabase.getInstance(context)
        val local = MealLocalDataSource.getInstance(FavoriteDaoImpl.getInstance(database))
        val remote = MealRemoteDataSource.getInstance()
        return MealRepository.getInstance(local, remote)
    }

    fun getNoteRepository(context: Context): NoteRepository {
        val database = AppDatabase.getInstance(context)
        val local = NoteLocalDataSource.getInstance(NoteDaoImp.getInstance(database))
        return NoteRepository.getInstance(local)
    }

    fun getCategoryRepository(): CategoryRepository {
        val remote = CategoryRemoteDataSource.getInstance()
        return CategoryRepository.getInstance(remote)
    }

    fun getAreaRepository(): AreaRepository {
        val remote = AreaRemoteDataSource.getInstance()
        return AreaRepository.getInstance(remote)
    }

    fun getIngredientRepository(): IngredientRepository {
        val remote = IngredientRemoteDataSource.getInstance()
        return IngredientRepository.getInstance(remote)
    }

    fun getNewsRepository(): NewsRepository {
        val remote = NewsRemoteDataSource.getInstance()
        return NewsRepository.getInstance(remote)
    }
}
