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

    lateinit var ePigeon : Pigeon
    companion object {
        const val TAG="EditFragment"
    }
    private lateinit var binding : FragmentEditBinding
    private lateinit var singlePigeonViewModel : SinglePigeonViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentEditBinding.inflate(layoutInflater)

        val pId =EditFragmentArgs.fromBundle(requireArguments()).pigeonSelectedId
        singlePigeonViewModel = ViewModelProvider(this).get(SinglePigeonViewModel::class.java)
        singlePigeonViewModel.getPigeonById(pId).observe(viewLifecycleOwner, Observer { pigeon ->
            //binding = FragmentEditBinding.inflate(layoutInflater)
            binding.etNumber.setText(pigeon.number)
            binding.etName.setText(pigeon.name)
            binding.etBirth.setText(pigeon.birth.toString())
            binding.spnrSex.adapter = ArrayAdapter(
                requireContext(), android.R.layout.simple_spinner_item, listOf("HÍM", "TOJÓ", "FIATAL")
            )
            binding.spnrSex.setSelection(pigeon.sex.ordinal)
            ePigeon = pigeon
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
            val newPigeon = Pigeon(ePigeon.pigeonId, binding.etNumber.text.toString(), binding.etName.text.toString(), binding.etBirth.text.toString().toInt(), selectedSex)
            singlePigeonViewModel.update(newPigeon)
            Log.d("TAG", "FRAGMENTVISSZA")
            requireActivity().onBackPressed()
        }

        return binding.root
    }
}