package com.daniel.android_freshsnap.ui

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.media.ThumbnailUtils
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.appcompat.app.AppCompatActivity
import com.daniel.android_freshsnap.R
import com.daniel.android_freshsnap.databinding.FragmentSecondBinding
import com.daniel.android_freshsnap.databinding.PopupChooseImageSourceBinding
import com.daniel.android_freshsnap.ml.ModelFreshsnap
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.File
import java.nio.ByteBuffer
import java.nio.ByteOrder


class IdentifyFragment : Fragment() {

    private lateinit var binding : FragmentSecondBinding
    lateinit var bitmap : Bitmap
    private var progr = 0
    private var imageSize = 150
    private var currentFile: File? = null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as AppCompatActivity).supportActionBar?.show()

        binding.uploadButton.setOnClickListener { uploadImage() }
        setupPopupMenu()
    }

    private fun setupPopupMenu() {
        val popupBinding = PopupChooseImageSourceBinding.inflate(layoutInflater)
        val popupWindow = PopupWindow(
            popupBinding.root,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        ).apply {
            isFocusable = true
            elevation = 10F
        }

        popupBinding.btnCamera.setOnClickListener {
            startCameraX()
            popupWindow.dismiss()
        }

        popupBinding.btnGallery.setOnClickListener {
            startGallery()
            popupWindow.dismiss()
        }

        binding.chooseButton.setOnClickListener { btn ->
            popupWindow.showAsDropDown(btn)
        }
    }

    private fun uploadImage() {
        val mDetailPercentFragment = DetailPercentFragment()
        val mFragmentManager = parentFragmentManager
        mFragmentManager.beginTransaction().apply {
            replace(R.id.fragment, mDetailPercentFragment, DetailPercentFragment::class.java.simpleName)
            setReorderingAllowed(true)
            addToBackStack(null)
            commit()
        }
    }

    private fun predictImage(image: Bitmap){
        val fileName = "label_ml_freshsnap.txt"
        val app = requireActivity().application
        val inputString = app.assets.open(fileName).bufferedReader().use { it.readText() }
        val itemList = inputString.split("\n")

        val model = ModelFreshsnap.newInstance(requireContext())

        // Creates inputs for reference.
        val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 150, 150, 3), DataType.FLOAT32)
        val byteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3)
        byteBuffer.order(ByteOrder.nativeOrder())

        // get 1D array of 224 * 224 pixels in image
        val intValues = IntArray(imageSize * imageSize)
        image.getPixels(intValues, 0, image.width, 0, 0, image.width, image.height)

        // iterate over pixels and extract R, G, and B values. Add to bytebuffer.
        var pixel = 0
        for (i in 0 until imageSize) {
            for (j in 0 until imageSize) {
                val `val` = intValues[pixel++] // RGB
                byteBuffer.putFloat((`val` shr 16 and 0xFF) * (1f / 255f))
                byteBuffer.putFloat((`val` shr 8 and 0xFF) * (1f / 255f))
                byteBuffer.putFloat((`val` and 0xFF) * (1f / 255f))
            }
        }
        inputFeature0.loadBuffer(byteBuffer)

        // Runs model inference and gets result.
        val outputs: ModelFreshsnap.Outputs = model.process(inputFeature0)
        val outputFeature0: TensorBuffer = outputs.outputFeature0AsTensorBuffer
        val confidences = outputFeature0.floatArray
        // find the index of the class with the biggest confidence.
        var maxPos = 0
        var maxConfidence = 0f
        for (i in confidences.indices) {
            if (confidences[i] > maxConfidence) {
                maxConfidence = confidences[i]
                maxPos = i
            }
        }

        val output = String.format("%s", itemList[maxPos])

        progr = (confidences[maxPos] * 100).toInt()
        updateProgressBar()
        binding.result.text = output

        // Releases model resources if no longer used.
        model.close()
    }

    private fun updateProgressBar(){
        binding.progressPercentage.progress = progr
        binding.textViewResult.text = "$progr%"
    }

    private fun startGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, 250)
    }

    private fun startCameraX() {
        val camera = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(camera, 200)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 250 && data != null){
            binding.previewImageView.setImageURI(data.data)

            val uri : Uri ?= data.data

            val resolver = requireActivity().contentResolver

            bitmap = MediaStore.Images.Media.getBitmap(resolver, uri)

            val image = Bitmap.createScaledBitmap(bitmap, imageSize, imageSize, true)
            binding.buttonPredict.setOnClickListener{
                predictImage(image)
            }
        }
        else if(requestCode == 200 && resultCode == Activity.RESULT_OK){
            var image = data!!.extras!!["data"] as Bitmap?
            val dimension = image!!.width.coerceAtMost(image.height)
            image = ThumbnailUtils.extractThumbnail(image, dimension, dimension)
            binding.previewImageView.setImageBitmap(image)
            image = Bitmap.createScaledBitmap(image, imageSize, imageSize, true)
            binding.buttonPredict.setOnClickListener{
                predictImage(image)
            }
        }
    }

}