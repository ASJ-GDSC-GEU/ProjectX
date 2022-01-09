package com.example.projectx.screens.Teachers

import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectx.R
import com.example.projectx.adapter.ClassOptionsAdapter
import com.example.projectx.adapter.StudentItemAdapter
import com.example.projectx.daos.TopDao
import com.example.projectx.databinding.FragmentClassGroupBinding
import com.example.projectx.models.ClassOptions
import com.example.projectx.models.MyClass
import com.example.projectx.models.StudentItem
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class ClassGroupFragment : Fragment() {
    private var _binding: FragmentClassGroupBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: StudentItemAdapter
    private var studentArray = ArrayList<StudentItem>()

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
        val user_type: Int = arguments?.getInt("user_type")!!.toInt()
        when(user_type){
            0 -> {
                binding.classOptions.visibility = View.GONE
            }
            1 -> {
                binding.classOptions.visibility = View.VISIBLE
                addOptions(class_id)
            }
        }
        val db = FirebaseFirestore.getInstance()

        GlobalScope.launch(Dispatchers.IO) {
            var data_main = TopDao().dbRef().collection("classes")
                .document(class_id)
                .get()
                .addOnSuccessListener { documents ->
                    var data = documents.toObject<MyClass>()!!
                    var array: List<String> = data.students_id
                    binding.studentCount.text = array.size.toString() + " Students"
                    binding.courseName.text = data.subject
                    binding.courseSem.text = "${data!!.course} Sem-${data.semester}"
                    if(array.size >0){
                        setRecyclerView(array)
                    }
                    else{
                        binding.progressBar2.visibility = View.GONE
                    }
                }

        }

    }

    private fun setRecyclerView(array: List<String>) {
        TopDao().dbRef().collection("student")
            .whereIn("uid", array)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    var studentItem = StudentItem(
                        name = document.get("name").toString(),
                        description = document.get("course").toString(),
                        imageUrl = document.get("imageUrl").toString()
                    )
                    studentArray.add(studentItem)
                }
                adapter = StudentItemAdapter(studentArray, requireView().context)
                binding.progressBar2.visibility = View.GONE
                binding.recyclerViewStudents.visibility = View.VISIBLE
                binding.recyclerViewStudents.adapter = adapter
                binding.recyclerViewStudents.layoutManager = LinearLayoutManager(view?.context)
            }
    }


    private fun addOptions(class_id : String) {
        binding.apply {
            classOptions.layoutManager =
                LinearLayoutManager(
                    requireView().context,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
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
            data.add(
                ClassOptions(
                    label = "Share Files",
                    imageView = R.drawable.share_file_icon
                )
            )
            val adapter = ClassOptionsAdapter(class_id, data, requireActivity().applicationContext)
            classOptions.adapter = adapter
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}

