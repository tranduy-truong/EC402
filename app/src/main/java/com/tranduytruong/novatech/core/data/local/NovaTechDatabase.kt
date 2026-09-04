package com.tranduytruong.novatech.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tranduytruong.novatech.core.data.local.dao.CartDao
import com.tranduytruong.novatech.core.data.local.entity.CartItemEntity

@Database(
    entities = [CartItemEntity::class],
    version = 1,
    exportSchema = false,
)
abstract class NovaTechDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao
}
