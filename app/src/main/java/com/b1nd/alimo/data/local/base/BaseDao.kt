package com.b1nd.alimo.data.local.base

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update


@Dao
interface BaseDao<ENTITY> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: ENTITY)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: List<ENTITY>)

    @Update
    suspend fun update(entity: ENTITY)

    @Delete
    suspend fun delete(entity: ENTITY)
}
