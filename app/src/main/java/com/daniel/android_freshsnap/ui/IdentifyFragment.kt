package com.daniel.android_freshsnap.ui

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.PermissionChecker.checkSelfPermission
import com.daniel.android_freshsnap.ui.MainActivity.Companion.CAMERA_X_RESULT
import com.daniel.android_freshsnap.R
import com.daniel.android_freshsnap.databinding.FragmentSecondBinding
import com.daniel.android_freshsnap.ml.ModelFreshsnap
import com.daniel.android_freshsnap.utils.*
import com.daniel.android_freshsnap.utils.Utils.rotateBitmap
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.File


class IdentifyFragment : Fragment() {

    private lateinit var binding : FragmentSecondBinding
    lateinit var bitmap : Bitmap
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

        binding.cameraButton.setOnClickListener { startCameraX() }
        binding.galleryButton.setOnClickListener { startGallery() }
        binding.uploadButton.setOnClickListener { uploadImage() }
        binding.buttonPredict.setOnClickListener{ predictImage()}
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

    private fun predictImage(){
        val fileName = "label_ml_freshsnap.txt"
        val app = requireActivity().application
        val inputString = app.assets.open(fileName).bufferedReader().use { it.readText() }
        val townList = inputString.split("\n")

        val resized: Bitmap = Bitmap.createScaledBitmap(bitmap, 150, 150, true)
        val model = ModelFreshsnap.newInstance(requireContext())

        // Creates inputs for reference.
        val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 150, 150, 3), DataType.FLOAT32)

        val tensorImage = TensorImage(DataType.FLOAT32)
        tensorImage.load(resized)
        val byteBuffer = tensorImage.buffer
        inputFeature0.loadBuffer(byteBuffer)


        // Runs model inference and gets result.
        val outputs = model.process(inputFeature0)
        val outputFeature0 = outputs.outputFeature0AsTensorBuffer

        val max = getMax(outputFeature0.floatArray)

        binding.textViewResult.setText(townList[max])
        binding.textViewResult2.setText(outputFeature0.floatArray[max].toString())


        // Releases model resources if no longer used.
        model.close()
    }

    private fun startGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"

        startActivityForResult(intent, 250)
    }

    private fun startCameraX() {
        //launcherIntentCameraX.launch(Intent(requireContext(), CameraActivity::class.java))
        var camera : Intent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(camera, 200)
    }

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERA_X_RESULT) {
            val myFile = it.data?.getSerializableExtra("picture") as File
            val isBackCamera = it.data?.getBooleanExtra("isBackCamera", true) as Boolean
            val result = rotateBitmap(
                BitmapFactory.decodeFile(myFile.path),
                isBackCamera
            )
            binding.previewImageView.setImageBitmap(result)
        }
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == AppCompatActivity.RESULT_OK) {
            currentFile = Utils.uriToFile(it.data?.data as Uri, requireContext())
            binding.previewImageView.setImageURI(Uri.fromFile(currentFile))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        //binding.previewImageView.setImageURI(data?.data)

        //val resolver = requireActivity().contentResolver

        val uri:Uri ?= data?.data


        //bitmap = MediaStore.Images.Media.getBitmap(resolver, uri)

        if(requestCode == 250){
            binding.previewImageView.setImageURI(data?.data)

            var uri : Uri ?= data?.data

            val resolver = requireActivity().contentResolver

            bitmap = MediaStore.Images.Media.getBitmap(resolver, uri)
        }
        else if(requestCode == 200 && resultCode == Activity.RESULT_OK){
            bitmap = data?.extras?.get("data") as Bitmap
            binding.previewImageView.setImageBitmap(bitmap)
        }
    }

    fun getMax(arr:FloatArray) : Int{

        var ind = 0
        var min = 0.0f

        for(i in 0..19)
        {
            if(arr[i]>min)
            {
                ind = i
                min = arr[i]
            }
        }

        return ind
    }
}