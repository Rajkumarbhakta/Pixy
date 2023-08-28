package com.rkbapps.pixy.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ImageEntity::class], version = 1)
abstract class ImageDatabase : RoomDatabase() {
    abstract fun imageDao(): ImageDao
}