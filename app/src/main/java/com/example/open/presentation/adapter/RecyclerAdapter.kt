package com.example.open.presentation.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.open.database.entity.Animal
import java.util.*

class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.AnimalsHolder>() {
    private var animals: List<Animal> = ArrayList()
    private var listener: onAnimalClickListener? = null

    override fun getItemCount(): Int {
        return animals.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, parameter: Int): AnimalsHolder {
        val itemView: View =
            LayoutInflater.from(parent.context)
                .inflate(com.example.open.R.layout.animal_item, parent, false)
        return AnimalsHolder(itemView)
    }

    override fun onBindViewHolder(holder: AnimalsHolder, position: Int) {
        val currentAnimal: Animal = animals[position]
        holder.editName.text = currentAnimal.name
        holder.editAge.text = currentAnimal.age
        holder.editColor.text = currentAnimal.color
    }

    fun setAnimal(animal: List<Animal>) {
        this.animals = animal
        notifyDataSetChanged()
    }

    fun getAnimalAt(position: Int): Animal {
        return animals[position]
    }

    inner class AnimalsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val editName: TextView = itemView.findViewById(com.example.open.R.id.textDescriptionName)
        val editAge: TextView = itemView.findViewById(com.example.open.R.id.textDescriptionAge)
        var editColor: TextView = itemView.findViewById(com.example.open.R.id.textDescriptionColor)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener?.onItemClick(animals[position])
                }
            }
        }

        fun onItemClear() {
            itemView.setBackgroundColor(Color.WHITE)
        }
    }

    interface onAnimalClickListener {
        fun onItemClick(animal: Animal)
    }

    fun setOnItemClickListener(listener: onAnimalClickListener) {
        this.listener = listener
    }

}