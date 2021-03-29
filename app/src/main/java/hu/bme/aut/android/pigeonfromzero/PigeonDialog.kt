package hu.bme.aut.android.pigeonfromzero

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import hu.bme.aut.android.pigeonfromzero.databinding.DialogPigeonBinding
import hu.bme.aut.android.pigeonfromzero.model.Pigeon

class PigeonDialog(private var onPigeonDialogAnswer: OnPigeonDialogAnswer) : DialogFragment() {

    private lateinit var binding : DialogPigeonBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogPigeonBinding.inflate(LayoutInflater.from(context))

        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Új galamb")
        builder.setView(binding.root)

        binding.spnrSex.adapter = ArrayAdapter(
            requireContext(), android.R.layout.simple_spinner_item, listOf("HÍM", "TOJÓ", "FIATAL")
        )

        builder.setPositiveButton("OK") { _, _ ->
            if (binding.etNumber.text.isNotEmpty()) {
                    pigeonCreate()
            }
        }
        builder.setNegativeButton("Vissza") { dialog, _ ->
            dialog.cancel()
        }
        return builder.create()
    }

    private fun pigeonCreate() {
        val selectedSex = when (binding.spnrSex.selectedItemPosition){
            0 -> Pigeon.Sex.MALE
            1 -> Pigeon.Sex.FEMALE
            2 -> Pigeon.Sex.UNKNOWN
            else -> Pigeon.Sex.UNKNOWN
        }
        onPigeonDialogAnswer.pigeonCreated(
            Pigeon(0, binding.etNumber.text.toString(), binding.etName.text.toString(), binding.etBirth.text.toString().toInt(), selectedSex)
        )
    }

    interface OnPigeonDialogAnswer {
        fun pigeonCreated(pigeon : Pigeon)
    }
}