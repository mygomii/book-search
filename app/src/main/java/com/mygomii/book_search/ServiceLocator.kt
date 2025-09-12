package com.mygomii.book_search

import android.content.Context
import androidx.room.Room
import com.mygomii.booksearch.data.local.AppDatabase
import com.mygomii.booksearch.data.remote.KakaoBookApi
import com.mygomii.booksearch.data.repository.BookRepositoryImpl
import com.mygomii.booksearch.domain.repository.BookRepository
import com.mygomii.booksearch.domain.usecase.ObserveFavoritesUseCase
import com.mygomii.booksearch.domain.usecase.SearchBooksUseCase
import com.mygomii.booksearch.domain.usecase.ToggleFavoriteUseCase

object ServiceLocator {
    @Volatile private var repository: BookRepository? = null

    fun provideRepository(context: Context): BookRepository {
        return repository ?: synchronized(this) {
            val db = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "books.db"
            ).fallbackToDestructiveMigration().build()

            val api = KakaoBookApi(BuildConfig.KAKAO_API_KEY)
            val repo = BookRepositoryImpl(api, db.bookDao())
            repository = repo
            repo
        }
    }

    fun provideUseCases(context: Context) = com.mygomii.booksearch.presentation.AppDependencies(
        searchBooks = SearchBooksUseCase(provideRepository(context)),
        toggleFavorite = ToggleFavoriteUseCase(provideRepository(context)),
        observeFavorites = ObserveFavoritesUseCase(provideRepository(context))
    )
}
