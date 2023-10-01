package com.crudmehra.sampleroomdb.fragments.list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.crudmehra.sampleroomdb.R
import com.crudmehra.sampleroomdb.model.StudentModel

class ListAdapter : RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    private var studentModelList = emptyList<StudentModel>()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_view_row,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return studentModelList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = studentModelList[position]

        holder.itemView.findViewById<TextView>(R.id.id_txt).text = currentItem.id.toString()
        holder.itemView.findViewById<TextView>(R.id.firstName_txt).text =
            currentItem.firstName + " " + currentItem.lastName
        holder.itemView.findViewById<TextView>(R.id.age_txt).text = currentItem.age.toString()

        holder.itemView.findViewById<LinearLayout>(R.id.rowLayout).setOnClickListener {
            val action =
                ListFragmentDirections.actionListFragmentToUpdateFragment(currentItem) // <- Pass object to Update Fragment
            holder.itemView.findNavController().navigate(action)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(studentModel: List<StudentModel>) {
        this.studentModelList = studentModel
        notifyDataSetChanged()
    }
}