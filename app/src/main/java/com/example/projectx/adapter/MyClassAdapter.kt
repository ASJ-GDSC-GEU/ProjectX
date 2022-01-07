package com.example.projectx.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.projectx.R
import com.example.projectx.models.MyClass
import com.example.projectx.screens.DetailsFragmentDirections
import com.example.projectx.screens.TeachersFragment
import com.example.projectx.screens.TeachersFragmentDirections
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import android.os.Bundle




class MyClassAdapter(options: FirestoreRecyclerOptions<MyClass>) :
    FirestoreRecyclerAdapter<MyClass, MyClassAdapter.ClassViewHolder>(options) {

    class ClassViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val courselabel: TextView = itemView.findViewById(R.id.course_label)
        val subjectlabel: TextView = itemView.findViewById(R.id.subject_label)
        val course_block: ConstraintLayout = itemView.findViewById(R.id.class_block)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClassViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.myclass_item, parent, false)
        return ClassViewHolder(view)

    }


    override fun onBindViewHolder(holder: ClassViewHolder, position: Int, model: MyClass) {
        holder.courselabel.text = "${model.course}  Sem-${model.semester}"
        holder.subjectlabel.text = "${model.subject} : Sec-${model.section}"
        holder.course_block.setOnClickListener {
            var navController: NavController? = null
            navController = Navigation.findNavController(it)
            val bundle = Bundle()
            val snapshot : String = snapshots.getSnapshot(position).id
            bundle.putString("test", snapshot)
            navController!!.navigate(R.id.action_teachersFragment_to_classGroupFragment,bundle)
        }

    }


}
