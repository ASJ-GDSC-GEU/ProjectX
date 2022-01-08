package com.example.projectx.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.projectx.R
import com.example.projectx.models.MyClass
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class MyClassAdapter(options: FirestoreRecyclerOptions<MyClass>, private val user_type: Int) :
    FirestoreRecyclerAdapter<MyClass, MyClassAdapter.ClassViewHolder>(options) {

        class ClassViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val courselabel: TextView = itemView.findViewById(R.id.course_label)
        val subjectlabel: TextView = itemView.findViewById(R.id.subject_label)
        val classitem: ConstraintLayout = itemView.findViewById(R.id.class_groupBtn)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClassViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.myclass_item, parent, false)
            return ClassViewHolder(view)

    }


    override fun onBindViewHolder(holder: ClassViewHolder, position: Int, model: MyClass) {
        holder.courselabel.text = "${model.course}  Sem-${model.semester}"
        holder.subjectlabel.text = "${model.subject} : Sec-${model.section}"
        holder.classitem.setOnClickListener {
            var snapshot: String = snapshots.getSnapshot(position).id
            val bundle: Bundle = Bundle()
            bundle.putString("test", snapshot)
            when (user_type) {
                0 -> {
                    it.findNavController()
                        .navigate(R.id.action_studentFragment_to_classGroupFragment, bundle)
                }
                1 -> {
                    it.findNavController()
                        .navigate(R.id.action_teachersFragment_to_classGroupFragment, bundle)
                }

            }
        }
    }


}
