package hu.bme.aut.android.pigeonfromzero.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import hu.bme.aut.android.pigeonfromzero.adapter.PigeonAdapter
import hu.bme.aut.android.pigeonfromzero.databinding.FragmentHomeBinding
import hu.bme.aut.android.pigeonfromzero.model.Pigeon
import hu.bme.aut.android.pigeonfromzero.viewmodel.PigeonListViewModel

class HomeFragment : Fragment(), PigeonAdapter.PigeonClickListener//, PigeonDialog.OnPigeonDialogAnswer
{

    private lateinit var binding : FragmentHomeBinding

    lateinit var pigeonAdapter: PigeonAdapter
    private lateinit var pigeonListViewModel: PigeonListViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        initRecyclerView()

        pigeonListViewModel = ViewModelProvider(this).get(PigeonListViewModel::class.java)
        pigeonListViewModel.allPigeons.observe(viewLifecycleOwner, Observer { pigeons ->
            pigeonAdapter.submitList(pigeons)
        })
        binding.bFloating.setOnClickListener {
            val action = HomeFragmentDirections.actionPigeonCreate()
            findNavController().navigate(action)
        }
        return binding.root
    }

    private fun initRecyclerView() {
        pigeonAdapter = PigeonAdapter()
        pigeonAdapter.pigeonClickListener = this
        binding.rwPigeon.adapter = pigeonAdapter
    }

    override fun onPigeonClick(pigeon: Pigeon) {
        val action = HomeFragmentDirections.actionPigeonSelected(pigeon.pigeonId)
        findNavController().navigate(action)
    }

    override fun onPigeonDeleteClick(pigeon: Pigeon) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage("Biztosan törlöd a galambot?")
        builder.setPositiveButton("Igen") {
                _, _ -> pigeonListViewModel.delete(pigeon)
        }
        builder.setNegativeButton("Nem"){
                dialog, _ -> dialog.cancel()
        }
        val alert = builder.create()
        alert.show()
    }

}