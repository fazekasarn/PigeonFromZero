 package hu.bme.aut.android.pigeonfromzero.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import hu.bme.aut.android.pigeonfromzero.R
import hu.bme.aut.android.pigeonfromzero.databinding.FragmentEditBinding
import hu.bme.aut.android.pigeonfromzero.model.Pigeon
import hu.bme.aut.android.pigeonfromzero.viewmodel.ChangeViewModel

class EditFragment : Fragment() {

    lateinit var choosenPigeon : Pigeon
    private lateinit var binding : FragmentEditBinding
    private lateinit var changeViewModel : ChangeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentEditBinding.inflate(layoutInflater)

        val pId =EditFragmentArgs.fromBundle(requireArguments()).pigeonSelectedId
        changeViewModel = ViewModelProvider(this).get(ChangeViewModel::class.java)
        changeViewModel.getPigeonById(pId).observe(viewLifecycleOwner, Observer { pigeon ->
            binding.etNumber.setText(pigeon.pigeonId)
            binding.etCountry.setText(pigeon.country)
            binding.etName.setText(pigeon.name)
            binding.etBirth.setText(pigeon.birth.toString())
            binding.spnrSex.adapter = ArrayAdapter(
                requireContext(), android.R.layout.simple_spinner_item, listOf("HÍM", "TOJÓ", "FIATAL")
            )
            binding.spnrSex.setSelection(pigeon.sex.ordinal)
            binding.etScore.setText(pigeon.scores)
            changeViewModel.getIdBySex(Pigeon.Sex.MALE).observe(viewLifecycleOwner, Observer { spinnerData ->
                val adapt = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, spinnerData)
                adapt.add("-");
                binding.spnrDad.adapter = adapt
                if (pigeon.dadId!=null){
                    if (spinnerData.indexOf(pigeon.dadId)!=-1)
                        binding.spnrDad.setSelection(spinnerData.indexOf(pigeon.dadId))
                } else
                    binding.spnrDad.setSelection(spinnerData.size-1)
            })
            changeViewModel.getIdBySex(Pigeon.Sex.FEMALE).observe(viewLifecycleOwner, Observer { spinnerData ->
                val adapt = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, spinnerData)
                adapt.add("-");
                binding.spnrMom.adapter = adapt
                if (pigeon.momId!=null){
                    binding.spnrMom.setSelection(spinnerData.indexOf(pigeon.momId))
                } else
                    binding.spnrMom.setSelection(spinnerData.size-1)
            })

            choosenPigeon = pigeon
        })

        binding.bCancel.setOnClickListener{
            hideKeyboard()
            requireActivity().onBackPressed()
        }

        binding.bSave.setOnClickListener{
            val selectedSex = when (binding.spnrSex.selectedItemPosition){
                0 -> Pigeon.Sex.MALE
                1 -> Pigeon.Sex.FEMALE
                2 -> Pigeon.Sex.UNKNOWN
                else -> Pigeon.Sex.UNKNOWN
            }
            var selectedMom :String? = null
            if (binding.spnrMom.selectedItem != "-")
                selectedMom = binding.spnrMom.selectedItem.toString()
            var selectedDad :String? = null
            if (binding.spnrDad.selectedItem != "-")
                selectedDad = binding.spnrDad.selectedItem.toString()
            val newPigeon = Pigeon(choosenPigeon.pigeonId, binding.etCountry.text.toString(), binding.etName.text.toString(), binding.etBirth.text.toString().toInt(), selectedSex, binding.etScore.text.toString(), selectedDad, selectedMom)
            changeViewModel.update(newPigeon)
            hideKeyboard()
            requireActivity().onBackPressed()
        }

        return binding.root
    }

    fun hideKeyboard(){
        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)
    }
}