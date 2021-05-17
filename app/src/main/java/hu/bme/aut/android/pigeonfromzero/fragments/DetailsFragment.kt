package hu.bme.aut.android.pigeonfromzero.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
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
import com.google.android.play.core.internal.ch
import hu.bme.aut.android.pigeonfromzero.data.DadWithChildrenNoRoom
import hu.bme.aut.android.pigeonfromzero.data.MomWithChildrenNoRoom
import hu.bme.aut.android.pigeonfromzero.databinding.FragmentDetailsBinding
import hu.bme.aut.android.pigeonfromzero.databinding.PedigreeBinding
import hu.bme.aut.android.pigeonfromzero.databinding.PedigreeDynamicBinding
import hu.bme.aut.android.pigeonfromzero.model.Pigeon
import hu.bme.aut.android.pigeonfromzero.viewmodel.DetailsViewModel
import hu.bme.aut.android.pigeonfromzero.viewmodel.MyViewModelFactory


class DetailsFragment : Fragment() {

    val PREF_NAME: String = "MySettings"
    lateinit var choosenPigeon: Pigeon
    var allMomsWithChildren : List<MomWithChildrenNoRoom> = emptyList()
    var allDadsWithChildren : List<DadWithChildrenNoRoom> = emptyList()
    private lateinit var binding: FragmentDetailsBinding
    private lateinit var detailsViewModel: DetailsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentDetailsBinding.inflate(layoutInflater)

        val pId = DetailsFragmentArgs.fromBundle(requireArguments()).pigeonId
        detailsViewModel = ViewModelProvider(this, MyViewModelFactory(pId))[DetailsViewModel::class.java]

        detailsViewModel.choosenPigeon.observe(viewLifecycleOwner, Observer { pigeon ->
            /*val remainder = pigeon.birth % 100
            val short = when (pigeon.sex) {
                Pigeon.Sex.MALE -> "H"
                Pigeon.Sex.FEMALE -> "T"
                Pigeon.Sex.UNKNOWN -> "?"
            }*/
            binding.tvLongNumber.text = pigeon.toIdentifier() //"${pigeon.country}-$remainder-${pigeon.pigeonId}-$short"
            binding.tvName.text = pigeon.name
            binding.tvBirth.text = pigeon.birth.toString()
            binding.tvSex.text = pigeon.sex.name
            binding.tvScore.text = pigeon.scores
            binding.tvDad.text = pigeon.dadId
            binding.tvMom.text = pigeon.momId
            //choosenPigeon = pigeon
        })

        detailsViewModel.allDadsWithChildren.observe(viewLifecycleOwner, Observer { dads ->
            allDadsWithChildren = dads
            for (item in dads){
                if (item.pigeon.pigeonId==pId){
                    choosenPigeon=item.pigeon
                }
            }
        })

        detailsViewModel.allMomsWithChildren.observe(viewLifecycleOwner, Observer { moms ->
            allMomsWithChildren = moms
            for (item in moms){
                if (item.pigeon.pigeonId==pId){
                    choosenPigeon=item.pigeon
                }
            }
        })

        binding.bEdit.setOnClickListener {
            val action = DetailsFragmentDirections.actionPigeonEdit(choosenPigeon.pigeonId)
            findNavController().navigate(action)
        }

        binding.bCancel.setOnClickListener {
            val toast =
                Toast.makeText(activity, allDadsWithChildren.toString(), Toast.LENGTH_LONG)
            toast.show()
        }

        binding.bPDF.setOnClickListener {
            val tList =ArrayList<Pigeon?>()
            tList.add(choosenPigeon)
            detailsViewModel.firstGenParents = loadAncestors(tList)
            detailsViewModel.secondGenParents = loadAncestors(detailsViewModel.firstGenParents)
            detailsViewModel.thirdGenParents = loadAncestors(detailsViewModel.secondGenParents)
            detailsViewModel.fourthGenParents = loadAncestors(detailsViewModel.thirdGenParents)

            val pdfBinding = PedigreeBinding.inflate(layoutInflater)
            /*val remainder = choosenPigeon.birth % 100
            val short = when (choosenPigeon.sex) {
                Pigeon.Sex.MALE -> "H"
                Pigeon.Sex.FEMALE -> "T"
                Pigeon.Sex.UNKNOWN -> "?"
            }*/
            pdfBinding.tvHeader.text = choosenPigeon.toIdentifier() //"HU-$remainder-${choosenPigeon.pigeonId}-$short"
            pdfBinding.textView1.text = choosenPigeon.toString()

            pdfBinding.textView2.text = detailsViewModel.firstGenParents[0]?.toString()
            pdfBinding.textView3.text = detailsViewModel.firstGenParents[1]?.toString()

            pdfBinding.textView21.text = detailsViewModel.secondGenParents[0]?.toString()
            pdfBinding.textView22.text = detailsViewModel.secondGenParents[1]?.toString()
            pdfBinding.textView23.text = detailsViewModel.secondGenParents[2]?.toString()
            pdfBinding.textView24.text = detailsViewModel.secondGenParents[3]?.toString()

            pdfBinding.textView31.text = detailsViewModel.thirdGenParents[0]?.toString()
            pdfBinding.textView32.text = detailsViewModel.thirdGenParents[1]?.toString()
            pdfBinding.textView33.text = detailsViewModel.thirdGenParents[2]?.toString()
            pdfBinding.textView34.text = detailsViewModel.thirdGenParents[3]?.toString()
            pdfBinding.textView35.text = detailsViewModel.thirdGenParents[4]?.toString()
            pdfBinding.textView36.text = detailsViewModel.thirdGenParents[5]?.toString()
            pdfBinding.textView37.text = detailsViewModel.thirdGenParents[6]?.toString()
            pdfBinding.textView38.text = detailsViewModel.thirdGenParents[7]?.toString()

            pdfBinding.textView41.text = detailsViewModel.fourthGenParents[0]?.toString()
            pdfBinding.textView42.text = detailsViewModel.fourthGenParents[1]?.toString()
            pdfBinding.textView43.text = detailsViewModel.fourthGenParents[2]?.toString()
            pdfBinding.textView44.text = detailsViewModel.fourthGenParents[3]?.toString()
            pdfBinding.textView45.text = detailsViewModel.fourthGenParents[4]?.toString()
            pdfBinding.textView46.text = detailsViewModel.fourthGenParents[5]?.toString()
            pdfBinding.textView47.text = detailsViewModel.fourthGenParents[6]?.toString()
            pdfBinding.textView48.text = detailsViewModel.fourthGenParents[7]?.toString()
            pdfBinding.textView49.text = detailsViewModel.fourthGenParents[8]?.toString()
            pdfBinding.textView410.text = detailsViewModel.fourthGenParents[9]?.toString()
            pdfBinding.textView411.text = detailsViewModel.fourthGenParents[10]?.toString()
            pdfBinding.textView412.text = detailsViewModel.fourthGenParents[11]?.toString()
            pdfBinding.textView413.text = detailsViewModel.fourthGenParents[12]?.toString()
            pdfBinding.textView414.text = detailsViewModel.fourthGenParents[13]?.toString()
            pdfBinding.textView415.text = detailsViewModel.fourthGenParents[14]?.toString()
            pdfBinding.textView416.text = detailsViewModel.fourthGenParents[15]?.toString()

            val sp = requireContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            pdfBinding.tvFooter.text = "${sp.getString("name", "")}\n${sp.getString("contact", "")}\n${sp.getString("address", "")}"

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

    fun loadAncestors(youngOnes: ArrayList<Pigeon?>): ArrayList<Pigeon?> {
        val tempList = ArrayList<Pigeon?>()
        for (pigeon in youngOnes) {
            var dadFound = false
            var momFound = false
            for (item in allDadsWithChildren) {
                Log.d("TAG", "ELJUT1")
                if (item.children.contains(pigeon)) {
                    tempList.add(item.pigeon)
                    dadFound = true
                }
            }
            if (!dadFound) {
                tempList.add(null)
            }
            for (item in allMomsWithChildren) {
                if (item.children.contains(pigeon)) {
                    tempList.add(item.pigeon)
                    momFound = true
                }
            }
            if (!momFound) {
                tempList.add(null)
            }
        }
        return tempList
    }
}