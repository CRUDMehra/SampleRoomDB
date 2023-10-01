package com.crudmehra.sampleroomdb.fragments.add

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.crudmehra.sampleroomdb.R
import com.crudmehra.sampleroomdb.databinding.FragmentAddDbBinding
import com.crudmehra.sampleroomdb.model.StudentModel
import com.crudmehra.sampleroomdb.viewModel.UserViewModel

class AddFragment : Fragment() {

    private var _binding: FragmentAddDbBinding? = null
    private val binding get() = _binding!!

    private lateinit var mUserViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAddDbBinding.inflate(inflater, container, false)

        mUserViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        binding.addBtn.setOnClickListener {
            insertDataToDatabase()
        }

        return binding.root
    }

    private fun insertDataToDatabase() {
        // val firstName = addFirstName_et.text.toString() // viewBinding will automatically convert addFirstName_et to addFirstNameEt. Thus, addFirstName_et does not exist.
        // val firstName = addFirstNameEt.text.toString() // <- Error : Unresolved reference: addFirstNameEt
        val firstName = binding.addFirstNameEt.text.toString() // <- This is correct
        val lastName = binding.addLastNameEt.text.toString()
        val age = binding.addAgeEt.text

        // Check if the inputCheck function is true
        if(inputCheck(firstName, lastName, age)) {
            // Create User Object
            val studentModel = StudentModel(0, firstName, lastName, Integer.parseInt(age.toString())) // <- Pass id, firstName, lastName, and age. Although id will be auto-generated because it is a primary key, we need to pass a value or zero (Don't worry, the Room library knows it is the primary key and is auto-generated).

            // Add Data to database
            mUserViewModel.addUser(studentModel)
            Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_LONG).show()
            // Navigate back
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "Please fill out all fields!", Toast.LENGTH_LONG).show()
        }
    }

    private fun inputCheck(firstName: String, lastName: String, age: Editable): Boolean {
        return !(TextUtils.isEmpty(firstName) && TextUtils.isEmpty(lastName) && age.isEmpty())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // <- whenever we destroy our fragment, _binding is set to null. Hence it will avoid memory leaks.
    }

}