package com.crudmehra.sampleroomdb.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.crudmehra.sampleroomdb.model.StudentModel

// UserDao contains the methods used for accessing the database, including queries.
@Dao
interface StudentDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE) // <- Annotate the 'addUser' function below. Set the onConflict strategy to IGNORE so if exactly the same user exists, it will just ignore it.
    suspend fun addUser(studentModel: StudentModel)

    @Update
    suspend fun updateUser(studentModel: StudentModel)

    @Delete
    suspend fun deleteUser(studentModel: StudentModel)

    @Query("DELETE FROM student_table")
    suspend fun deleteAllUsers()

    @Query("SELECT * from student_table ORDER BY id ASC") // <- Add a query to fetch all users (in user_table) in ascending order by their IDs.
    fun readAllData(): LiveData<List<StudentModel>> // <- This means function return type is List. Specifically, a List of Users.
}