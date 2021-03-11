package hu.bme.aut.android.pigeonfromzero

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import hu.bme.aut.android.pigeonfromzero.adapter.PigeonAdapter
import hu.bme.aut.android.pigeonfromzero.data.AppDatabase
import hu.bme.aut.android.pigeonfromzero.data.Pigeon
import hu.bme.aut.android.pigeonfromzero.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), OnPigeonDialogAnswer {

    companion object {
        val KEY_ITEM_TO_EDIT = "KEY_ITEM_TO_EDIT"
    }
    private lateinit var binding : ActivityMainBinding
    lateinit var pigeonAdapter: PigeonAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bFloating.setOnClickListener { view ->
            PigeonDialog().show(supportFragmentManager, "TAG_ITEM")
        }

        initRecyclerView()

    }

    private fun initRecyclerView() {
        val dbThread = Thread {
            val pigeons = AppDatabase.getInstance(this).pigeonDao().findAllPigeons()

            runOnUiThread {
                pigeonAdapter = PigeonAdapter(this)
                for (item: Pigeon in pigeons) {
                    pigeonAdapter.addPigeon(item)
                }
                binding.rwPigeon.adapter = pigeonAdapter
            }
        }
        dbThread.start()
    }

    override fun pigeonCreated(pigeon: Pigeon) {
        val dbThread = Thread {
            AppDatabase.getInstance(this@MainActivity).pigeonDao().insertPigeon(pigeon)
            runOnUiThread {
                pigeonAdapter.addPigeon(pigeon)
            }
        }
        dbThread.start()
    }
}