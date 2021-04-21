 package hu.bme.aut.android.pigeonfromzero.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import hu.bme.aut.android.pigeonfromzero.R
import hu.bme.aut.android.pigeonfromzero.databinding.FragmentEditBinding
import hu.bme.aut.android.pigeonfromzero.model.Pigeon
import hu.bme.aut.android.pigeonfromzero.viewmodel.SinglePigeonViewModel

class EditFragment : Fragment() {

    lateinit var choosenPigeon : Pigeon
    private lateinit var binding : FragmentEditBinding
    private lateinit var singlePigeonViewModel : SinglePigeonViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentEditBinding.inflate(layoutInflater)

        val pId =EditFragmentArgs.fromBundle(requireArguments()).pigeonSelectedId
        singlePigeonViewModel = ViewModelProvider(this).get(SinglePigeonViewModel::class.java)
        singlePigeonViewModel.getPigeonById(pId).observe(viewLifecycleOwner, Observer { pigeon ->
            binding.etNumber.setText(pigeon.pigeonId)
            binding.etName.setText(pigeon.name)
            binding.etBirth.setText(pigeon.birth.toString())
            binding.spnrSex.adapter = ArrayAdapter(
                requireContext(), android.R.layout.simple_spinner_item, listOf("HÍM", "TOJÓ", "FIATAL")
            )
            binding.spnrSex.setSelection(pigeon.sex.ordinal)
            binding.etScore.setText(pigeon.scores)
            singlePigeonViewModel.getIdBySex(Pigeon.Sex.MALE).observe(viewLifecycleOwner, Observer { spinnerData ->
                val adapt = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, spinnerData)
                adapt.add("-");
                binding.spnrDad.adapter = adapt
                if (pigeon.dadId!=null){
                    if (spinnerData.indexOf(pigeon.dadId)!=-1)
                    binding.spnrDad.setSelection(spinnerData.indexOf(pigeon.dadId))
                } else
                    binding.spnrDad.setSelection(spinnerData.size-1)
            })
            singlePigeonViewModel.getIdBySex(Pigeon.Sex.FEMALE).observe(viewLifecycleOwner, Observer { spinnerData ->
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
            val newPigeon = Pigeon(choosenPigeon.pigeonId, binding.etName.text.toString(), binding.etBirth.text.toString().toInt(), selectedSex, binding.etScore.text.toString(), selectedDad, selectedMom)
            singlePigeonViewModel.update(newPigeon)
            requireActivity().onBackPressed()
        }

        return binding.root
    }
}