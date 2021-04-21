package hu.bme.aut.android.pigeonfromzero.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.gkemon.XMLtoPDF.PdfGenerator
import com.gkemon.XMLtoPDF.PdfGeneratorListener
import com.gkemon.XMLtoPDF.model.FailureResponse
import com.gkemon.XMLtoPDF.model.SuccessResponse
import hu.bme.aut.android.pigeonfromzero.databinding.FragmentDetailsBinding
import hu.bme.aut.android.pigeonfromzero.databinding.PedigreeBinding
import hu.bme.aut.android.pigeonfromzero.model.Pigeon
import hu.bme.aut.android.pigeonfromzero.viewmodel.SinglePigeonViewModel

class DetailsFragment : Fragment(){

    lateinit var choosenPigeon : Pigeon
    companion object {
        const val TAG="DetailsFragment"
    }

    private lateinit var binding : FragmentDetailsBinding
    private lateinit var singlePigeonViewModel : SinglePigeonViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentDetailsBinding.inflate(layoutInflater)

        val pId = DetailsFragmentArgs.fromBundle(requireArguments()).pigeonId
        singlePigeonViewModel = ViewModelProvider(this).get(SinglePigeonViewModel::class.java)
        singlePigeonViewModel.getPigeonById(pId).observe(viewLifecycleOwner, Observer { pigeon ->
            binding.tvNumber.text = pigeon.pigeonId
            binding.tvName.text = pigeon.name
            binding.tvBirth.text = pigeon.birth.toString()
            binding.tvSex.text = pigeon.sex.name
            binding.tvScore.text = pigeon.scores
            binding.tvDad.text = pigeon.dadId
            binding.tvMom.text = pigeon.momId
            choosenPigeon = pigeon
        })

        binding.bEdit.setOnClickListener{
            val action = DetailsFragmentDirections.actionPigeonEdit(choosenPigeon.pigeonId)
            findNavController().navigate(action)
        }

        binding.bCancel.setOnClickListener{
            requireActivity().onBackPressed()
        }

        binding.bPDF.setOnClickListener{
            val pdfbinding = PedigreeBinding.inflate(layoutInflater)
            val pdfgl = object : PdfGeneratorListener() {
                override fun onFailure(failureResponse: FailureResponse) {
                    super.onFailure(failureResponse)
                }

                override fun showLog(log: String) {
                    super.showLog(log)
                }

                override fun onStartPDFGeneration() {
                    /*When PDF generation begins to start*/
                }

                override fun onFinishPDFGeneration() {
                    /*When PDF generation is finished*/
                }

                override fun onSuccess(response: SuccessResponse) {
                    super.onSuccess(response)
                }
            }
            val pdfgen = PdfGenerator.getBuilder()
                .setContext(context)
                .fromViewSource()
                .fromView(pdfbinding.root)
                .setPageSize(PdfGenerator.PageSize.A4)
                .setFileName(choosenPigeon.pigeonId)
                .setFolderName("Test-PDF-folder")
                .openPDFafterGeneration(true)
                .build(pdfgl)
        }
        return binding.root
    }
}