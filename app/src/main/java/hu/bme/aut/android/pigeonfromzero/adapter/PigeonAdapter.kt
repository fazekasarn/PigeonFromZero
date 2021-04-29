package hu.bme.aut.android.pigeonfromzero.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.pigeonfromzero.databinding.RowItemBinding
import hu.bme.aut.android.pigeonfromzero.model.Pigeon

class PigeonAdapter : ListAdapter<Pigeon, PigeonAdapter.ViewHolder>(itemCallback) {

    companion object{
        object itemCallback : DiffUtil.ItemCallback<Pigeon>(){
            override fun areItemsTheSame(oldItem: Pigeon, newItem: Pigeon): Boolean {
                return oldItem.pigeonId == newItem.pigeonId
            }
            override fun areContentsTheSame(oldItem: Pigeon, newItem: Pigeon): Boolean {
                return oldItem == newItem
            }
        }
    }

    var pigeonClickListener : PigeonClickListener? = null

    inner class ViewHolder(val binding: RowItemBinding) : RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(RowItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentPigeon = getItem(position)

        val remainder = currentPigeon.birth%100
        val short = when(currentPigeon.sex){
            Pigeon.Sex.MALE -> "H"
            Pigeon.Sex.FEMALE -> "T"
            Pigeon.Sex.UNKNOWN -> "?"
        }
        holder.binding.tvLongNumber.text = "HU-$remainder-${currentPigeon.pigeonId}-$short"
        holder.binding.tvName.text = currentPigeon.name

        holder.binding.bDelete.setOnClickListener {
            pigeonClickListener?.onPigeonDeleteClick(currentPigeon)
        }
        holder.itemView.setOnClickListener{
            pigeonClickListener?.onPigeonClick(currentPigeon)
        }
    }

    interface PigeonClickListener {
        fun onPigeonClick(pigeon: Pigeon)
        fun onPigeonDeleteClick(pigeon: Pigeon)
    }
}