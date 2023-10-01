package com.crudmehra.sampleroomdb.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.crudmehra.sampleroomdb.data.StudentDatabase
import com.crudmehra.sampleroomdb.repository.StudentRepository
import com.crudmehra.sampleroomdb.model.StudentModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// UserViewModel provides users data to the UI and survive configuration changes.
// A ViewModel acts as a communication center between the Repository and the UI.

class UserViewModel(application: Application): AndroidViewModel(application) {
    val readAllData: LiveData<List<StudentModel>>
    private val repository: StudentRepository

    init {
        val userDao = StudentDatabase.getDatabase(application).userDao()
        repository= StudentRepository(userDao)
        readAllData = repository.readAllData
    }

    fun addUser(studentModel: StudentModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUser(studentModel)
        }
    }

    fun updateUser(studentModel: StudentModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateUser(studentModel)
        }
    }

    fun deleteUser(studentModel: StudentModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteUser(studentModel)
        }
    }

    fun deleteAllUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllUsers()
        }
    }
}