package com.example.open.presentation.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.open.R
import com.example.open.callback.SimpleItemTouchHelperCallback
import com.example.open.database.entity.Animals
import com.example.open.databinding.DataDisplayFragmentBinding
import com.example.open.model.AnimalsViewModel
import com.example.open.presentation.adapter.RecyclerAdapter
import com.example.open.settings.SettingsActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.data_display_fragment.*


class DataDisplayFragment : Fragment() {

    private val adapter = RecyclerAdapter()
    private var binding: DataDisplayFragmentBinding? = null
    private var animalsViewModel: AnimalsViewModel? = null

    companion object {
        const val ADD_ANIMAL: Int = 1
        const val UPDATE_ANIMAL: Int = 2
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.data_display_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DataDisplayFragmentBinding.bind(view)

        // Include RecycleView
        includeRecycleView(adapter)

        // Include ItemTouchHelper
        includeItemTouchHelper(adapter)

        // I don't know how else put data into ViewModel
        // Only one method i know is deprecated :(
        animalsViewModel = ViewModelProviders.of(this).get(AnimalsViewModel::class.java)
        animalsViewModel?.getAllAnimals()?.observe(viewLifecycleOwner,
            Observer { animals -> adapter.setAnimal(animals) })

        // Handling a click on a list item
        adapterSetOnItemClickListener(adapter)

        view.findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("extraName", ADD_ANIMAL)
            findNavController().navigate(
                R.id.action_DataDisplayFragment_to_DataCreateFragment,
                bundle
            )
        }
        binding?.displayFragmentToolbar?.inflateMenu(R.menu.sort_menu)
        binding?.displayFragmentToolbar?.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.sort_object_action -> {
                    startActivity(Intent(activity, SettingsActivity::class.java))
                    true
                }
                else -> super.onOptionsItemSelected(it)
            }
        }

    }

    // Function for include RecycleView
    private fun includeRecycleView(adapter: RecyclerAdapter) {
        binding?.recyclerView?.layoutManager = LinearLayoutManager(context)
        binding?.recyclerView?.setHasFixedSize(true)
        binding?.recyclerView?.adapter = adapter
    }

    private fun includeItemTouchHelper(adapter: RecyclerAdapter) {
        ItemTouchHelper(object : SimpleItemTouchHelperCallback() {
            override fun onMove(
                recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                //  adapter.onItemMove(viewHolder.adapterPosition, target.adapterPosition)
                //  I don't manage to implement correctly)
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                animalsViewModel?.delete(adapter.getAnimalAt(viewHolder.adapterPosition))
            }
        }).attachToRecyclerView(recyclerView)
    }

    // Function to handle clicking on an element of RecyclerAdapter
    private fun adapterSetOnItemClickListener(adapter: RecyclerAdapter) {
        adapter.setOnItemClickListener(object : RecyclerAdapter.onAnimalClickListener {
            override fun onItemClick(animal: Animals) {
                val bundle = Bundle()
                bundle.putInt("extraName", UPDATE_ANIMAL)
                bundle.putString("name", animal.name)
                bundle.putString("age", animal.age)
                bundle.putString("color", animal.color)
                animal.id?.let { bundle.putLong("id", it) }
                findNavController().navigate(
                    R.id.action_DataDisplayFragment_to_DataCreateFragment,
                    bundle
                )
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}