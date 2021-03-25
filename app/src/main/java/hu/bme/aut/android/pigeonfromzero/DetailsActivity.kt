package hu.bme.aut.android.pigeonfromzero

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hu.bme.aut.android.pigeonfromzero.databinding.ActivityDetailsBinding
import hu.bme.aut.android.pigeonfromzero.fragments.DetailsFragment
import hu.bme.aut.android.pigeonfromzero.fragments.EditFragment
import hu.bme.aut.android.pigeonfromzero.model.Pigeon

class DetailsActivity : AppCompatActivity() {

    lateinit var pigeon : Pigeon
    private lateinit var binding : ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pigeon = intent.getSerializableExtra("PIGEON_KEY") as Pigeon

        showDetailsFragment()
    }

    private fun showDetailsFragment(){
        val detailsFragment=DetailsFragment()
        val bundle = Bundle()
        bundle.putSerializable("PIGEON_KEY", pigeon)
        detailsFragment.arguments=bundle

        val ft = supportFragmentManager.beginTransaction()
        ft.add(binding.fragmentContainer.id, detailsFragment, DetailsFragment.TAG)
        ft.commit()
    }

    fun showEditFragment(){
        val editFragment=EditFragment()
        val bundle = Bundle()
        bundle.putSerializable("EDIT_KEY", pigeon)
        editFragment.arguments=bundle

        val ft = supportFragmentManager.beginTransaction()
        ft.replace(binding.fragmentContainer.id, editFragment, EditFragment.TAG)
        ft.addToBackStack(EditFragment.TAG)
        ft.commit()
    }
}