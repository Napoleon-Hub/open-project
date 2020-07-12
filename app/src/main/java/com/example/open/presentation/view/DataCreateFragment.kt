package com.example.open.presentation.view

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
import com.example.open.database.entity.Animal
import com.example.open.databinding.DataCreateFragmentBinding
import com.example.open.viewmodel.AnimalsViewModel

class DataCreateFragment : Fragment() {

    private var binding: DataCreateFragmentBinding? = null
    private var animalsViewModel: AnimalsViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.data_create_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DataCreateFragmentBinding.bind(view)
        animalsViewModel = ViewModelProviders.of(this).get(AnimalsViewModel::class.java)

        updateCheck()

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

    private fun saveAnimal() {
        val name: String = binding?.editName?.text.toString()
        val age: String = binding?.editAge?.text.toString()
        val color: String = binding?.editColor?.text.toString()

        if (name.trim().isEmpty() || age.trim().isEmpty() || color.trim().isEmpty()) {
            Toast.makeText(context,
                INSERT_TOAST_MASSAGE, Toast.LENGTH_SHORT).show()
            return
        }

        val animal = Animal(name, age, color)

        if (arguments?.getInt(DataDisplayFragment.UPDATE_OR_ADD_EXTRA_NAME) == DataDisplayFragment.UPDATE_ANIMAL) {
            animal.id = arguments?.getLong(DataDisplayFragment.ID_KEY)
            animalsViewModel?.update(animal)
        } else {
            animalsViewModel?.insert(animal)
        }

        findNavController().navigate(R.id.action_DataCreateFragment_to_DataDisplayFragment)
    }

    private fun updateCheck() {
        if (arguments?.getInt(DataDisplayFragment.UPDATE_OR_ADD_EXTRA_NAME) == DataDisplayFragment.UPDATE_ANIMAL) {
            binding?.editName?.setText(arguments?.getString(DataDisplayFragment.NAME_KEY))
            binding?.editAge?.setText(arguments?.getString(DataDisplayFragment.AGE_KEY))
            binding?.editColor?.setText(arguments?.getString(DataDisplayFragment.COLOR_KEY))
            binding?.createFragmentToolbar?.title =
                EDIT_TITLE
        } else {
            binding?.createFragmentToolbar?.title =
                CREATE_TITLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        private const val EDIT_TITLE: String = "Edit beast"
        private const val CREATE_TITLE: String = "Create beast"
        private const val INSERT_TOAST_MASSAGE: String = "Please insert all the data"
    }
}