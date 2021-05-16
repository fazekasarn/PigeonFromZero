package hu.bme.aut.android.pigeonfromzero.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import hu.bme.aut.android.pigeonfromzero.viewmodel.DetailsViewModel
import hu.bme.aut.android.pigeonfromzero.viewmodel.MyViewModelFactory


class DetailsFragment : Fragment() {

    lateinit var choosenPigeon: Pigeon
    var firstGenParents: List<Pigeon?> = emptyList()
    lateinit var secondGenParents: List<Pigeon?>
    lateinit var thirdGenParents: List<Pigeon?>
    lateinit var fourthGenParents: List<Pigeon?>

    private lateinit var binding: FragmentDetailsBinding
    private lateinit var detailsViewModel: DetailsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentDetailsBinding.inflate(layoutInflater)

        val pId = DetailsFragmentArgs.fromBundle(requireArguments()).pigeonId
        detailsViewModel = ViewModelProvider(this, MyViewModelFactory(pId))[DetailsViewModel::class.java]

        detailsViewModel.choosenPigeon.observe(viewLifecycleOwner, Observer { pigeon ->
            val remainder = pigeon.birth % 100
            val short = when (pigeon.sex) {
                Pigeon.Sex.MALE -> "H"
                Pigeon.Sex.FEMALE -> "T"
                Pigeon.Sex.UNKNOWN -> "?"
            }
            binding.tvLongNumber.text = "HU-$remainder-${pigeon.pigeonId}-$short"
            binding.tvName.text = pigeon.name
            binding.tvBirth.text = pigeon.birth.toString()
            binding.tvSex.text = pigeon.sex.name
            binding.tvScore.text = pigeon.scores
            binding.tvDad.text = pigeon.dadId
            binding.tvMom.text = pigeon.momId
            choosenPigeon = pigeon
        })

        detailsViewModel.allDadsWithChildren.observe(viewLifecycleOwner, Observer { _ ->
        })

        detailsViewModel.allMomsWithChildren.observe(viewLifecycleOwner, Observer { _ ->
        })

        detailsViewModel.allPigeons.observe(viewLifecycleOwner, Observer { _ ->
            detailsViewModel.refreshFirstGenFamily()
        })

        binding.bEdit.setOnClickListener {
            val action = DetailsFragmentDirections.actionPigeonEdit(choosenPigeon.pigeonId)
            findNavController().navigate(action)
        }

        binding.bCancel.setOnClickListener {
            val toast =
                Toast.makeText(activity, detailsViewModel.firstGenParents.size.toString(), Toast.LENGTH_LONG)
            toast.show()
        }

        binding.bPDF.setOnClickListener {

            val pdfBinding = PedigreeBinding.inflate(layoutInflater)
            pdfBinding.data = detailsViewModel
            pdfBinding.textView3.text="KEZZEL BEIRT"

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
                .fromView(pdfBinding.root)
                .setPageSize(PdfGenerator.PageSize.A4)
                .setFileName(choosenPigeon.pigeonId)
                .setFolderName("Test-PDF-folder")
                .openPDFafterGeneration(true)
                .build(pdfgl)
            val toast =
                Toast.makeText(activity, "LEFUT", Toast.LENGTH_LONG)
            toast.show()
        }
        return binding.root
    }

    fun addParentsToList(pigeon :Pigeon?, list :List<Pigeon?>) : List<Pigeon?>{
        var dadFound = false
        var momFound = false
        val tempList = list.toMutableList()
        for (item in detailsViewModel.allDadsWithChildren.value!!){
            if (item.children.contains(pigeon)){
                tempList.add(item.pigeon)
                dadFound = true
            }
        }
        if (!dadFound){
            tempList.add(null)
        }
        for (item in detailsViewModel.allMomsWithChildren.value!!){
            if (item.children.contains(pigeon)){
                tempList.add(item.pigeon)
                momFound = true
            }
        }
        if (!momFound) {
            tempList.add(null)
        }
        return tempList.toList()
    }
}