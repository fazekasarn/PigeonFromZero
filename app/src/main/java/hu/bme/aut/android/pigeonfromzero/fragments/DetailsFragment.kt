package hu.bme.aut.android.pigeonfromzero.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import hu.bme.aut.android.pigeonfromzero.DetailsActivity
import hu.bme.aut.android.pigeonfromzero.MainActivity
import hu.bme.aut.android.pigeonfromzero.data.Pigeon
import hu.bme.aut.android.pigeonfromzero.databinding.ActivityDetailsBinding
import hu.bme.aut.android.pigeonfromzero.databinding.ActivityMainBinding
import hu.bme.aut.android.pigeonfromzero.databinding.DetailsPigeonBinding

class DetailsFragment : Fragment(){

    lateinit var pigeon : Pigeon
    companion object {
        const val TAG="DetailsFragment"
    }

    private lateinit var binding : DetailsPigeonBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DetailsPigeonBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = arguments
        if (args!=null){
            pigeon = args.getSerializable("PIGEON_KEY") as Pigeon
        }
        binding.tvNumber.text = pigeon.number
        binding.tvName.text = pigeon.name
        binding.tvBirth.text = pigeon.birth.toString()
        binding.tvSex.text = pigeon.sex

        binding.bEdit.setOnClickListener{
            val detailsActivity=activity as DetailsActivity
            detailsActivity.showEditFragment()
        }

        binding.bCancel.setOnClickListener{
            requireActivity().onBackPressed()
        }

        binding.bPDF.setOnClickListener{
            //TODO
            val toast = Toast.makeText(context, "coming soon...", Toast.LENGTH_SHORT)
            toast.show()
        }
    }
}