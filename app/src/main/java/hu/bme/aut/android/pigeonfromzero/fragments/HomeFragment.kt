package hu.bme.aut.android.pigeonfromzero.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import hu.bme.aut.android.pigeonfromzero.adapter.PigeonAdapter
import hu.bme.aut.android.pigeonfromzero.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding : FragmentHomeBinding
    lateinit var pigeonAdapter: PigeonAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()

    }

    private fun initRecyclerView() {
        pigeonAdapter = PigeonAdapter()
        binding.rwPigeon.adapter = pigeonAdapter
    }
}