package hu.bme.aut.android.pigeonfromzero.fragments

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.DialogFragment
import hu.bme.aut.android.pigeonfromzero.databinding.FragmentDialogContactsBinding

class ContactDialogFragment : DialogFragment() {

    val PREF_NAME: String = "MySettings"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var binding = FragmentDialogContactsBinding.inflate(LayoutInflater.from(context))

        val sp = requireContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        binding.etName.setText(sp.getString("name", ""))
        binding.etAddress.setText(sp.getString("address", ""))
        binding.etContact.setText(sp.getString("contact", ""))

        binding.Button1.setOnClickListener {
            hideKeyboard()
            dismiss()
        }

        binding.Button2.setOnClickListener {
            val sp = requireContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = sp.edit()
            editor.putString("name", binding.etName.text.toString())
            editor.putString("address", binding.etAddress.text.toString())
            editor.putString("contact", binding.etContact.text.toString())
            editor.apply()
            hideKeyboard()
            dismiss()
        }
        return binding.root
    }

    fun hideKeyboard(){
        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)
    }
}