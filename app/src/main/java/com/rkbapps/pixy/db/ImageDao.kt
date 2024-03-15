package com.rkbapps.pixy.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ImageDao {

    @Insert
    suspend fun addNewImage(imageEntity: ImageEntity)

    @Query("select * from images where id=:id")
    suspend fun getImageById(id: String): ImageEntity

    @Query("select * from images where slug=:slug")
    suspend fun getImageBySlug(slug: String): ImageEntity

    @Delete
    suspend fun deleteImage(imageEntity: ImageEntity)

    @Query("delete from images where id=:id")
    suspend fun deleteImageById(id: String)

    @Query("delete from images where slug=:slug")
    suspend fun deleteImageBySlug(slug: String)

    @Query("select * from images")
    fun getAllImages(): Flow<List<ImageEntity>>

    @Query("select exists(select * from images where id=:id)")
    suspend fun isExists(id: String): Boolean
}