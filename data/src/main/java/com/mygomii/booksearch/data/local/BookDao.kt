package com.mygomii.booksearch.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {
    @Query("SELECT * FROM favorite_books ORDER BY title ASC")
    fun observeAll(): Flow<List<FavoriteBookEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(entity: FavoriteBookEntity)

    @Delete
    suspend fun delete(entity: FavoriteBookEntity)

    @Query("SELECT * FROM favorite_books WHERE isbn = :isbn LIMIT 1")
    suspend fun getByIsbn(isbn: String): FavoriteBookEntity?
}

