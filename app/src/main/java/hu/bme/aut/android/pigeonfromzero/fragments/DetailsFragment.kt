package hu.bme.aut.android.pigeonfromzero.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import hu.bme.aut.android.pigeonfromzero.databinding.FragmentDetailsBinding
import hu.bme.aut.android.pigeonfromzero.model.Pigeon
import hu.bme.aut.android.pigeonfromzero.viewmodel.SinglePigeonViewModel

class DetailsFragment : Fragment(){

    lateinit var epigeon : Pigeon
    companion object {
        const val TAG="DetailsFragment"
    }

    private lateinit var binding : FragmentDetailsBinding
    private lateinit var singlePigeonViewModel : SinglePigeonViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentDetailsBinding.inflate(layoutInflater)

        val pId = DetailsFragmentArgs.fromBundle(requireArguments()).pigeonId
        singlePigeonViewModel = ViewModelProvider(this).get(SinglePigeonViewModel::class.java)
        singlePigeonViewModel.getPigeonById(pId).observe(viewLifecycleOwner, Observer { pigeon ->
            binding.tvNumber.text = pigeon.number
            binding.tvName.text = pigeon.name
            binding.tvBirth.text = pigeon.birth.toString()
            binding.tvSex.text = pigeon.sex.name
            epigeon = pigeon
        })

        binding.bEdit.setOnClickListener{
            val action = DetailsFragmentDirections.actionPigeonEdit(epigeon.pigeonId)
            findNavController().navigate(action)
        }

        binding.bCancel.setOnClickListener{
            requireActivity().onBackPressed()
        }

        binding.bPDF.setOnClickListener{
            //TODO
        }
        return binding.root
    }
}