package com.crudmehra.sampleroomdb.repository

import androidx.lifecycle.LiveData
import com.crudmehra.sampleroomdb.data.StudentDao
import com.crudmehra.sampleroomdb.model.StudentModel

// User Repository abstracts access to multiple data sources. However this is not the part of the Architecture Component libraries.

class StudentRepository(private val userDao: StudentDao) {
    val readAllData: LiveData<List<StudentModel>> = userDao.readAllData()

    suspend fun addUser(studentModel: StudentModel) {
        userDao.addUser(studentModel)
    }

    suspend fun updateUser(studentModel: StudentModel) {
        userDao.updateUser(studentModel)
    }

    suspend fun deleteUser(studentModel: StudentModel) {
        userDao.deleteUser(studentModel)
    }

    suspend fun deleteAllUsers() {
        userDao.deleteAllUsers()
    }
}