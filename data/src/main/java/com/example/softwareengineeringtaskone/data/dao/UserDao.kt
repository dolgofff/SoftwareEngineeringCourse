package com.example.softwareengineeringtaskone.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.softwareengineeringtaskone.data.entity.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User): Long

    @Delete
    suspend fun deleteUser(user: User): Int

    @Update
    suspend fun updateUser(user: User): Int

    @Query("SELECT * FROM users")
    fun getAllUsers(): Flow<List<User>>

    @Query("DELETE FROM users WHERE id = :userId")
    suspend fun deleteUserById(userId: Long)

}