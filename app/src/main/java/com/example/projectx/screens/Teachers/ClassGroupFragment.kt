package com.example.projectx.screens.Teachers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectx.R
import com.example.projectx.adapter.ClassOptionsAdapter
import com.example.projectx.daos.TopDao
import com.example.projectx.databinding.FragmentClassGroupBinding
import com.example.projectx.models.ClassOptions
import com.example.projectx.models.MyClass
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class ClassGroupFragment : Fragment() {
    private var _binding: FragmentClassGroupBinding? = null
    private val binding get() = _binding!!
    private var class_obj: MyClass? = null
    private lateinit var result: MyClass

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentClassGroupBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val class_id: String = arguments?.getString("test").toString()
        val db = FirebaseFirestore.getInstance()
        addOptions()
        GlobalScope.launch(Dispatchers.IO) {
            var data = TopDao().dbRef().collection("classes").document(class_id).get().await()
                .toObject<MyClass>()!!
            withContext(Dispatchers.Main) {
                binding.courseName.text = data.subject
                binding.courseSem.text = "${data!!.course} Sem-${data.semester}"
            }
        }

    }


    private fun addOptions() {
        binding.apply {
            classOptions.layoutManager =
                LinearLayoutManager(requireView().context, LinearLayoutManager.HORIZONTAL, false)
            val data = ArrayList<ClassOptions>()
            data.add(
                ClassOptions(
                    label = "Create meet link",
                    imageView = R.drawable.create_meetlink_icon
                )
            )
            data.add(
                ClassOptions(
                    label = "Invite Students",
                    imageView = R.drawable.invite_student_icon
                )
            )
            data.add(ClassOptions(label = "Share Files", imageView = R.drawable.share_file_icon))
            val adapter = ClassOptionsAdapter(data)
            classOptions.adapter = adapter
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}


// Previous Deleted Code
//TopDao().dbRef().collection("classes").document(class_id).get()
//.addOnSuccessListener { documentSnapshot ->
//    class_obj = documentSnapshot.toObject<MyClass>()
//    // Toast.makeText(requireView().context, "$class_id", Toast.LENGTH_SHORT).show()
//    adddata(class_obj)
//} fun adddata(classObj: MyClass?) {
//        binding.courseName.text = "${classObj!!.subject} : Sec-${classObj!!.section}"
//        binding.courseSem.text = "${classObj!!.course} Sem-${classObj.semester}"
//
//    }