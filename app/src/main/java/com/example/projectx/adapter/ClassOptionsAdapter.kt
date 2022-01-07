
package com.example.projectx.adapter

import android.graphics.Color
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.projectx.R
import com.example.projectx.models.ClassOptions

class ClassOptionsAdapter(private val optionsList : List<ClassOptions>) : RecyclerView.Adapter<ClassOptionsAdapter.ViewHolder>() {
    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val label : TextView = itemView.findViewById(R.id.c_option_label)
        val icon : ImageView = itemView.findViewById(R.id.c_option_icon)
        val classOptionItem : CardView = itemView.findViewById(R.id.class_otpionItem_card)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.class_option_item,parent , false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = optionsList[position]
        when(position){
            0 -> {
                holder.classOptionItem.setBackgroundResource(R.drawable.round_filledblue)
                holder.label.setTextColor(Color.parseColor("#B2C2FF"))
                holder.classOptionItem.cardElevation = 12F

            }
        }

        holder.label.text = current.label
        holder.icon.setImageResource(current.imageView)
    }

    override fun getItemCount(): Int {
        return optionsList.size
    }
}
