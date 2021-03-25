package hu.bme.aut.android.pigeonfromzero

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import hu.bme.aut.android.pigeonfromzero.adapter.PigeonAdapter
import hu.bme.aut.android.pigeonfromzero.databinding.ActivityMainBinding
import hu.bme.aut.android.pigeonfromzero.model.Pigeon
import hu.bme.aut.android.pigeonfromzero.viewmodel.PigeonViewModel

class MainActivity : AppCompatActivity(), PigeonDialog.OnPigeonDialogAnswer, PigeonAdapter.PigeonClickListener {

    companion object {
        val KEY_ITEM_TO_EDIT = "KEY_ITEM_TO_EDIT"
    }
    private lateinit var binding : ActivityMainBinding
    lateinit var pigeonAdapter: PigeonAdapter
    private lateinit var pigeonViewModel: PigeonViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bFloating.setOnClickListener { _ ->
            PigeonDialog().show(supportFragmentManager, "TAG_ITEM")
        }

        initRecyclerView()

        pigeonViewModel = ViewModelProvider(this).get(PigeonViewModel::class.java)
        pigeonViewModel.allPigeons.observe(this, { pigeons ->
            pigeonAdapter.submitList(pigeons)
        })
    }

    private fun initRecyclerView() {
        pigeonAdapter = PigeonAdapter()
        pigeonAdapter.pigeonClickListener = this
        binding.rwPigeon.adapter = pigeonAdapter
    }

    override fun pigeonCreated(pigeon: Pigeon) {
        pigeonViewModel.insert(pigeon)
    }

    override fun onPigeonClick(pigeon: Pigeon) {
        val pigeonIntent = Intent(this, DetailsActivity::class.java)
        pigeonIntent.putExtra("PIGEON_KEY", pigeon)
        startActivity(pigeonIntent)
    }

    override fun onPigeonDeleteClick(pigeon: Pigeon) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Biztosan törlöd a galambot?")
        builder.setPositiveButton("Igen") {
                _, _ -> pigeonViewModel.delete(pigeon)
        }
        builder.setNegativeButton("Nem"){
                dialog, _ -> dialog.cancel()
        }
        val alert = builder.create()
        alert.show()
    }
}