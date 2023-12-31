package com.crudmehra.sampleroomdb.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.crudmehra.sampleroomdb.R
import com.crudmehra.sampleroomdb.databinding.FragmentUpdateDbBinding
import com.crudmehra.sampleroomdb.model.StudentModel
import com.crudmehra.sampleroomdb.viewModel.UserViewModel

class UpdateFragment : Fragment() , MenuProvider {

    private var _binding: FragmentUpdateDbBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<UpdateFragmentArgs>()

    private lateinit var mUserViewModel: UserViewModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentUpdateDbBinding.inflate(inflater, container, false)

        mUserViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        binding.updateFirstNameEt.setText(args.currentUser.firstName)
        binding.updateLastNameEt.setText(args.currentUser.lastName)
        binding.updateAgeEt.setText(args.currentUser.age.toString())

        binding.updateBtn.setOnClickListener {
            updateItem()
        }

        // Add menu
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        return binding.root
    }

    private fun updateItem() {
        val firstName = binding.updateFirstNameEt.text.toString()
        val lastName = binding.updateLastNameEt.text.toString()
        // val age = binding.updateAgeEt.text.toString() // <- Error : Type mismatch. Required: Int , Found: String.
        val age = Integer.parseInt(binding.updateAgeEt.text.toString()) // Parses a string returns an integer.

        if (inputCheck(firstName, lastName, binding.updateAgeEt.text)) {
            // Create User Object
            val updatedStudentModel = StudentModel(args.currentUser.id, firstName, lastName, age)

            // Update Current User
            mUserViewModel.updateUser(updatedStudentModel)
            Toast.makeText(requireContext(), "Updated Successfully !", Toast.LENGTH_SHORT).show()

            // Navigate back to List Fragment
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "Please fill all fields !", Toast.LENGTH_SHORT).show()
        }
    }

    private fun inputCheck(firstName: String, lastName: String, age: Editable): Boolean {
        return !(TextUtils.isEmpty(firstName) && TextUtils.isEmpty(lastName) && age.isEmpty())
    }


    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.main_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.itemId == R.id.menu_delete) {
            deleteUser()
        }
        return false
    }

    // Implement logic to delete a user
    private fun deleteUser() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->     // Make a "Yes" option and set action if the user selects "Yes"
            mUserViewModel.deleteUser(args.currentUser)    // Execute : delete user
            Toast.makeText(                                // Notification if a user is deleted successfully
                    requireContext(),
                    "Successfully removed ${args.currentUser.firstName}",
                    Toast.LENGTH_SHORT)
                    .show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment) // Navigate to List Fragment after deleting a user
        }
        builder.setNegativeButton("No") { _, _ -> }    // Make a "No" option and set action if the user selects "No"
        builder.setTitle("Delete ${args.currentUser.firstName} ?")  // Set the title of the prompt with a sentence saying the first name of the user inside the app (using template string)
        builder.setMessage("Are you sure to remove ${args.currentUser.firstName} ?")  // Set the message of the prompt with a sentence saying the first name of the user inside the app (using template string)
        builder.create().show()  // Create a prompt with the configuration above to ask the user (the real app user which is human)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // <- whenever we destroy our fragment, _binding is set to null. Hence it will avoid memory leaks.
    }
}