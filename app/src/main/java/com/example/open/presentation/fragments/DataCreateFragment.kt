package com.example.open.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.open.R
import com.example.open.database.entity.Animals
import com.example.open.databinding.DataCreateFragmentBinding
import com.example.open.model.AnimalsViewModel

class DataCreateFragment : Fragment() {

    private var binding: DataCreateFragmentBinding? = null
    private var noteViewModel: AnimalsViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.data_create_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DataCreateFragmentBinding.bind(view)
        noteViewModel = ViewModelProviders.of(this).get(AnimalsViewModel::class.java)

        // If the Bundle have an UPDATE_ID fill the cells with values
        updateCheck()

        // Create NavigationBar for this Fragment
        createNavigationBar()

        view.findViewById<Button>(R.id.save_button).setOnClickListener {
            saveAnimal()
        }


    }

    private fun createNavigationBar() {
        binding?.createFragmentToolbar?.setNavigationIcon(R.drawable.ic_baseline_keyboard_backspace_24)
        binding?.createFragmentToolbar?.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_DataCreateFragment_to_DataDisplayFragment)
        }
    }

    // Save information about animal
    private fun saveAnimal() {
        val name: String = binding?.editName?.text.toString()
        val age: String = binding?.editAge?.text.toString()
        val color: String = binding?.editColor?.text.toString()

        if (name.trim().isEmpty() || age.trim().isEmpty() || color.trim().isEmpty()) {
            Toast.makeText(context, "Please insert all the data", Toast.LENGTH_SHORT).show()
            return
        }

        val animal = Animals(name, age, color)

        if (arguments?.getInt("extraName") == DataDisplayFragment.UPDATE_ANIMAL) {
            animal.id = arguments?.getLong("id")
            noteViewModel?.update(animal)
        } else {
            noteViewModel?.insert(animal)
        }

        findNavController().navigate(R.id.action_DataCreateFragment_to_DataDisplayFragment)
    }

    private fun updateCheck() {
        if (arguments?.getInt("extraName") == DataDisplayFragment.UPDATE_ANIMAL) {
            binding?.editName?.setText(arguments?.getString("name"))
            binding?.editAge?.setText(arguments?.getString("age"))
            binding?.editColor?.setText(arguments?.getString("color"))
            binding?.createFragmentToolbar?.title = "Edit beast"
        } else {
            binding?.createFragmentToolbar?.title = "Create beast"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}