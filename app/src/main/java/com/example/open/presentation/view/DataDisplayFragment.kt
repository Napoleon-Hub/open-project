package com.example.open.presentation.view

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.open.R
import com.example.open.callback.SimpleItemTouchHelperCallback
import com.example.open.database.entity.Animal
import com.example.open.databinding.DataDisplayFragmentBinding
import com.example.open.presentation.adapter.RecyclerAdapter
import com.example.open.settings.SettingsActivity
import com.example.open.viewmodel.AnimalsViewModel
import kotlinx.android.synthetic.main.data_display_fragment.*


class DataDisplayFragment : Fragment() {

    private val adapter = RecyclerAdapter()
    private var binding: DataDisplayFragmentBinding? = null
    private var animalsViewModel: AnimalsViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.data_display_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DataDisplayFragmentBinding.bind(view)

        binding?.recyclerView?.layoutManager = LinearLayoutManager(context)
        binding?.recyclerView?.adapter = adapter

        animalsViewModel = ViewModelProviders.of(this).get(AnimalsViewModel::class.java)

        ItemTouchHelper(object : SimpleItemTouchHelperCallback() {
            override fun onMove(
                recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                animalsViewModel?.delete(adapter.getAnimalAt(viewHolder.adapterPosition))
            }
        }).attachToRecyclerView(recyclerView)

        adapter.setOnItemClickListener(object : RecyclerAdapter.onAnimalClickListener {
            override fun onItemClick(animal: Animal) {
                val bundle = Bundle()
                bundle.putInt(
                    UPDATE_OR_ADD_EXTRA_NAME,
                    UPDATE_ANIMAL
                )
                bundle.putString(NAME_KEY, animal.name)
                bundle.putString(AGE_KEY, animal.age)
                bundle.putString(COLOR_KEY, animal.color)
                animal.id?.let { bundle.putLong(ID_KEY, it) }
                findNavController().navigate(
                    R.id.action_DataDisplayFragment_to_DataCreateFragment,
                    bundle
                )
            }
        })

        binding?.fab?.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt(
                UPDATE_OR_ADD_EXTRA_NAME,
                ADD_ANIMAL
            )

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

    override fun onResume() {

        animalsViewModel?.getAllAnimals()?.observe(viewLifecycleOwner,
            Observer { animals -> adapter.setAnimal(animals) })

        val preference: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity)
        if (preference.getBoolean(NAME_KEY, true)) {
           val listOfNameSortedAnimal =  animalsViewModel?.getAllAnimals()?.value?.sortedBy { it.name }
            if (listOfNameSortedAnimal != null) {
                adapter.setAnimal(listOfNameSortedAnimal)
            }
        }
        if (preference.getBoolean(AGE_KEY, true)) {
            val listOfAgeSortedAnimal = animalsViewModel?.getAllAnimals()?.value?.sortedBy { it.age.toInt() }
            if (listOfAgeSortedAnimal != null) {
                adapter.setAnimal(listOfAgeSortedAnimal)
            }
        }
        if (preference.getBoolean(COLOR_KEY, true)) {
            val listOfColorSortedAnimal = animalsViewModel?.getAllAnimals()?.value?.sortedBy { it.color }
            if (listOfColorSortedAnimal != null) {
                adapter.setAnimal(listOfColorSortedAnimal)
            }
        }
        super.onResume()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        private const val ADD_ANIMAL: Int = 1
        const val UPDATE_OR_ADD_EXTRA_NAME: String = "updateOrAddExtra"
        const val NAME_KEY: String = "name"
        const val AGE_KEY: String = "age"
        const val COLOR_KEY: String = "color"
        const val ID_KEY: String = "id"
        const val UPDATE_ANIMAL: Int = 2
    }


}


