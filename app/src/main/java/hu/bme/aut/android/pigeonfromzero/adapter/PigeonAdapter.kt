package hu.bme.aut.android.pigeonfromzero.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.pigeonfromzero.MainActivity
import hu.bme.aut.android.pigeonfromzero.R
import hu.bme.aut.android.pigeonfromzero.data.AppDatabase
import hu.bme.aut.android.pigeonfromzero.data.Pigeon
import hu.bme.aut.android.pigeonfromzero.databinding.ActivityMainBinding
import hu.bme.aut.android.pigeonfromzero.databinding.RowItemBinding

class PigeonAdapter : RecyclerView.Adapter<PigeonAdapter.ViewHolder> {

    private lateinit var binding : RowItemBinding

    var pigeons = mutableListOf<Pigeon>(

    )
    val context :Context
    constructor(context: Context){
        this.context=context
    }

    inner class ViewHolder(val binding: RowItemBinding) : RecyclerView.ViewHolder(binding.root) {
        //val tvNumber = binding.tvNumber
        //val tvBirth = binding.tvBirth
        //val tvSex = binding.tvSex
        //val bDelete = binding.bDelete
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //val view = LayoutInflater.from(context).inflate(
        //    R.layout.row_item, parent, false
        //)

        return ViewHolder(RowItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return pigeons.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentPigeon = pigeons[position]

        val remainder = currentPigeon.birth%100
        holder.binding.tvNumber.text = "$remainder-${currentPigeon.number} ${currentPigeon.sex}"
        holder.binding.tvName.text = currentPigeon.name

        holder.binding.bDelete.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setMessage("Biztosan törlöd a galambot?")
            builder.setPositiveButton("Igen") {
                dialog, which ->  deletePigeon(holder.adapterPosition)
            }
            builder.setNegativeButton("Nem"){
                dialog, which -> dialog.cancel()
            }
            val alert = builder.create()
            alert.show()
        }
    }

    private fun deletePigeon(position: Int) {
        val dbThread = Thread {
            AppDatabase.getInstance(context).pigeonDao().deletePigeon(pigeons[position])
            (context as MainActivity).runOnUiThread {
                pigeons.removeAt(position)
                notifyItemRemoved(position)
            }
        }
        dbThread.start()
    }

    fun addPigeon(pigeon :Pigeon){
        pigeons.add(pigeon)
        //miért megy enélkül is?
        //notifyItemInserted(pigeons.lastIndex)
    }
}