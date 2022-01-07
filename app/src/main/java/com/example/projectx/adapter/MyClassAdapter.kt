package com.example.projectx.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projectx.R
import com.example.projectx.models.MyClass
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class MyClassAdapter(options: FirestoreRecyclerOptions<MyClass>) :
    FirestoreRecyclerAdapter<MyClass, MyClassAdapter.ClassViewHolder>(options) {

    class ClassViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val courselabel: TextView = itemView.findViewById(R.id.course_label)
        val subjectlabel: TextView = itemView.findViewById(R.id.subject_label)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClassViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.myclass_item, parent, false)
        return ClassViewHolder(view)

    }


    override fun onBindViewHolder(holder: ClassViewHolder, position: Int, model: MyClass) {
        holder.courselabel.text = "${model.course}  Sem-${model.semester}"
        holder.subjectlabel.text = "${model.subject} : Sec-${model.section}"
    }


}
