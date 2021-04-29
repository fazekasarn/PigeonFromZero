package hu.bme.aut.android.pigeonfromzero.fragments

import android.R
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ListAdapter
import hu.bme.aut.android.pigeonfromzero.databinding.FragmentCreateBinding
import hu.bme.aut.android.pigeonfromzero.databinding.FragmentEditBinding
import hu.bme.aut.android.pigeonfromzero.model.Pigeon
import hu.bme.aut.android.pigeonfromzero.viewmodel.SinglePigeonViewModel

class CreateFragment : Fragment() {

    private lateinit var binding : FragmentCreateBinding
    private lateinit var singlePigeonViewModel : SinglePigeonViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCreateBinding.inflate(layoutInflater)
        singlePigeonViewModel = ViewModelProvider(this).get(SinglePigeonViewModel::class.java)

        singlePigeonViewModel.maleSpinnerData.observe(viewLifecycleOwner, Observer { spinnerData ->
            val adapt = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, spinnerData)
            adapt.add("-");
            binding.spnrDad.adapter = adapt
            binding.spnrDad.setSelection(spinnerData.size-1)
        })

        singlePigeonViewModel.femaleSpinnerData.observe(viewLifecycleOwner, Observer { spinnerData ->
            val adapt = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, spinnerData)
            adapt.add("-");
            binding.spnrMom.adapter = adapt
            binding.spnrMom.setSelection(spinnerData.size-1)
        })

        binding.spnrSex.adapter = ArrayAdapter(
            requireContext(), android.R.layout.simple_spinner_item, listOf("HÍM", "TOJÓ", "FIATAL")
        )

        binding.bCancel.setOnClickListener{
            requireActivity().onBackPressed()
        }

        binding.bCreate.setOnClickListener{
            if (binding.etNumber.text.isNotEmpty()) {
                pigeonCreate()
                requireActivity().onBackPressed()
            }
        }

        return binding.root
    }

    private fun pigeonCreate() {
        var selectedMom :String? = null
        if (binding.spnrMom.selectedItem != "-")
            selectedMom = binding.spnrMom.selectedItem.toString()
        var selectedDad :String? = null
        if (binding.spnrDad.selectedItem != "-")
            selectedDad = binding.spnrDad.selectedItem.toString()

        val selectedSex = when (binding.spnrSex.selectedItemPosition){
            0 -> Pigeon.Sex.MALE
            1 -> Pigeon.Sex.FEMALE
            2 -> Pigeon.Sex.UNKNOWN
            else -> Pigeon.Sex.UNKNOWN
        }
        singlePigeonViewModel.insert(
            Pigeon(binding.etNumber.text.toString(), binding.etName.text.toString(), binding.etBirth.text.toString().toInt(), selectedSex, binding.etScore.text.toString(), selectedDad, selectedMom)
        )
    }
}