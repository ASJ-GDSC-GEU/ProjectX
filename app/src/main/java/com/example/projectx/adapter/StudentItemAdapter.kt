package com.example.projectx.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.projectx.R
import com.example.projectx.models.StudentItem

class StudentItemAdapter(
    private val dataList: ArrayList<StudentItem>,
    private val context: Context
) :
    RecyclerView.Adapter<StudentItemAdapter.StudentItemViewHolder>() {
    class StudentItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val studentName: TextView = itemView.findViewById(R.id.student_name)
        val studentDescription: TextView = itemView.findViewById(R.id.student_descp)
        val studentImage: ImageView = itemView.findViewById(R.id.student_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentItemViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.students_item, parent, false)
        return StudentItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: StudentItemViewHolder, position: Int) {
        var model = dataList[position]
        holder.studentName.text = model.name
        holder.studentDescription.text = model.description
        Glide.with(context).load(model.imageUrl).circleCrop().error(R.drawable.user_error)
            .into(holder.studentImage)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

}