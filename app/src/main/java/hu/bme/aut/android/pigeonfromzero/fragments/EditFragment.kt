package hu.bme.aut.android.pigeonfromzero.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import hu.bme.aut.android.pigeonfromzero.databinding.FragmentEditBinding
import hu.bme.aut.android.pigeonfromzero.model.Pigeon

class EditFragment : Fragment() {

    lateinit var pigeon : Pigeon
    companion object {
        const val TAG="EditFragment"
    }
    private lateinit var binding : FragmentEditBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentEditBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = arguments
        if (args!=null){
            val galamb = args.getSerializable("EDIT_KEY") as? Pigeon
            if (galamb!=null){
                pigeon=galamb
                binding.etNumber.setText(pigeon.number)
                binding.etName.setText(pigeon.name)
                binding.etBirth.setText(pigeon.birth.toString())
                binding.etSex.setText(pigeon.sex.name)
            }
        }
        binding.bCancel.setOnClickListener{
            //requireActivity().supportFragmentManager.popBackStack()
            requireActivity().onBackPressed()
        }

        binding.bSave.setOnClickListener{
            //1 activity kéne csak, így felesleges megcsinálni
            //TODO
            val toast = Toast.makeText(context, "coming soon...", Toast.LENGTH_SHORT)
            toast.show()
        }
    }
}