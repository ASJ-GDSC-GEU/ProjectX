package com.example.projectx.screens

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import com.example.projectx.R
import com.example.projectx.databinding.ActivityDetailsBinding

class DetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun onRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            // Is the button now checked?
            val checked = view.isChecked

            // Check which radio button was clicked
            when (view.getId()) {
                R.id.student ->
                    if (checked) {
                        binding.student.setTextColor(Color.WHITE)
                        binding.teacher.setTextColor(Color.GRAY)
                    }
                R.id.teacher ->
                    if (checked) {
                        binding.teacher.setTextColor(Color.WHITE)
                        binding.student.setTextColor(Color.GRAY)

                    }
            }
        }
    }
}