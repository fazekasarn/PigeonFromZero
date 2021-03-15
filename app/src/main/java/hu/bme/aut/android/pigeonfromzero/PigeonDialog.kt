package hu.bme.aut.android.pigeonfromzero

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import hu.bme.aut.android.pigeonfromzero.data.Pigeon
import hu.bme.aut.android.pigeonfromzero.databinding.DialogPigeonBinding

class PigeonDialog : DialogFragment() {

    private lateinit var onPigeonDialogAnswer: OnPigeonDialogAnswer
    private lateinit var binding : DialogPigeonBinding

    private lateinit var etNumber: EditText
    private lateinit var etName: EditText
    private lateinit var etBirth: EditText
    private lateinit var etSex: EditText

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is OnPigeonDialogAnswer) {
            onPigeonDialogAnswer = context
        } else {
            //miért áll le egy exception dobás miatt?
            //throw RuntimeException("The Activity does not implement the OnPigeonDialogAnswer interface")
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Új galamb")

        binding = DialogPigeonBinding.inflate(LayoutInflater.from(context))
        builder.setView(binding.root)
        //val inflater = LayoutInflater.from(context)
        //val dialogLayout = inflater.inflate(R.layout.dialog_pigeon, null)
        etNumber = binding.etNumber
        etName = binding.etName
        etBirth = binding.etBirth
        etSex = binding.etSex
        //builder.setView(dialogLayout)

        builder.setPositiveButton("OK") { dialog, which ->
            if (etNumber.text.isNotEmpty()) {
                val arguments = this.arguments
                if (arguments != null && arguments.containsKey(MainActivity.KEY_ITEM_TO_EDIT)) {
                    handleItemEdit()
                } else {
                    handlePigeonCreate()
                }
                //dialog.dismiss()
            } else {
                etNumber.error = "Nem lehet üres"
            }
        }
        builder.setNegativeButton("Vissza") { dialog, which ->
            dialog.cancel()
        }
        return builder.create()
    }

    private fun handlePigeonCreate() {
        onPigeonDialogAnswer.pigeonCreated(
            Pigeon(null, etNumber.text.toString(), etName.text.toString(), etBirth.text.toString().toInt(), etSex.text.toString())
        )
    }

    private fun handleItemEdit() {
        TODO("Not yet implemented")
    }
}