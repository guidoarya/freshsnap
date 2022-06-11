package com.daniel.android_freshsnap.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.ThumbnailUtils
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.daniel.android_freshsnap.adapter.ListHowToAdapter
import com.daniel.android_freshsnap.adapter.ListRecipeAdapter
import com.daniel.android_freshsnap.api.ApiConfig
import com.daniel.android_freshsnap.api.response.ReviewResponse
import com.daniel.android_freshsnap.databinding.FragmentSecondBinding
import com.daniel.android_freshsnap.databinding.PopupChooseImageSourceBinding
import com.daniel.android_freshsnap.ml.ModelFreshsnap
import com.daniel.android_freshsnap.utils.Utils.createTempFile
import com.daniel.android_freshsnap.utils.Utils.reduceFileImage
import com.daniel.android_freshsnap.utils.Utils.rotateBitmap
import com.daniel.android_freshsnap.utils.Utils.uriToFile
import com.daniel.android_freshsnap.viewmodel.DetailViewModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.nio.ByteBuffer
import java.nio.ByteOrder


class IdentifyFragment : Fragment() {

    private lateinit var binding : FragmentSecondBinding
    private lateinit var rvRecipe: RecyclerView
    private lateinit var listRecipeAdapter: ListRecipeAdapter
    private lateinit var userPreferences: SharedPreferences
    private lateinit var rvHowTo: RecyclerView
    private lateinit var listHowToAdapter: ListHowToAdapter

    private lateinit var detailViewModel: DetailViewModel

    lateinit var bitmap : Bitmap
    private var progr = 0
    private var imageSize = 150
    private var currentFile: File? = null
    lateinit var itemName: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as AppCompatActivity).supportActionBar?.hide()


        rvRecipe = binding.rvRecipe
        binding.rvRecipe.setHasFixedSize(true)

        showRecyclerListRecipe()
        showRecyclerListHowTo()

        detailViewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        detailViewModel.listDetail.observe(viewLifecycleOwner) {
            if (it !=null){
                listHowToAdapter.setHowTo(it)
            }
        }

        detailViewModel.listRecipe.observe(viewLifecycleOwner){
            if (it !=null){
                listRecipeAdapter.setDetail(it)
                showLoading(false)
            }
        }

        detailViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
        binding.shareBtn.setOnClickListener {
            uploadImage()
            //showLoading(true)
        }
        setupPopupMenu()
    }

    private fun uploadImage() {

        if (currentFile != null){

            val file = reduceFileImage(currentFile as File)
            userPreferences = this.requireActivity().getSharedPreferences(LoginActivity.PREFS_USER, Context.MODE_PRIVATE)
            val location = binding.edtLoc.text.toString().toRequestBody()
            val item_name = itemName.toRequestBody()
            val user_name = userPreferences.getString(LoginActivity.NAME, "")!!.toRequestBody()
            val token = userPreferences.getString(LoginActivity.TOKEN, "").toString()
            val requestImageFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val image: MultipartBody.Part = MultipartBody.Part.createFormData(
                "image",
                file.name,
                requestImageFile
            )

            val client = ApiConfig.getApiService().upload(image, location, item_name, user_name,"Bearer $token")
            client.enqueue(object : Callback<ReviewResponse> {
                override fun onResponse(
                    call: Call<ReviewResponse>,
                    response: Response<ReviewResponse>
                ) {
                    if (response.isSuccessful && response.code() == 201){
                        val responseBody =  response.body()
                        if (responseBody != null){
                            Log.d(TAG, response.body().toString())
                            Toast.makeText(requireActivity(), "Success", Toast.LENGTH_SHORT).show()
                        }
                    }else{
                        Log.e(TAG, "onFailure : ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<ReviewResponse>, t: Throwable) {
                    Log.e(TAG, "onFailure: ${t.message}")
                    Toast.makeText(requireActivity(), "New list has been created!", Toast.LENGTH_SHORT).show()
                }

            })

        }else{
            Toast.makeText(requireActivity(), "Please input image first!", Toast.LENGTH_SHORT).show()
        }
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

    private fun showRecyclerListRecipe() {
        listRecipeAdapter = ListRecipeAdapter(arrayListOf())
        rvRecipe = binding.rvRecipe
        rvRecipe.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = listRecipeAdapter
        }
    }

    private fun showRecyclerListHowTo() {
        listHowToAdapter = ListHowToAdapter(arrayListOf())
        rvHowTo = binding.rvHowToKeep
        rvHowTo.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            adapter = listHowToAdapter
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

        getItemName(output)

        setUser(output)

        progr = (confidences[maxPos] * 100).toInt()
        updateProgressBar()
        binding.result.text = output

        // Releases model resources if no longer used.
        model.close()
    }

    private fun getItemName(output: String){
        itemName = output
    }

    private fun setUser(output: String) {
        when {("Fresh Apples" in output) || ("Rotten Apples" in output)
        ->
            setIdentify(output = 43)
        }
        when {("Fresh Banana" in output) || ("Rotten Banana" in output)
        ->
            setIdentify(output = 44)
        }
        when {("Fresh Cucumber" in output) || ("Rotten Cucumber" in output)
        ->
            setIdentify(output = 45)
        }
        when {("Fresh Guava" in output) || ("Rotten Guava" in output)
        ->
            setIdentify(output = 46)
        }
        when {("Fresh Lime" in output) || ("Rotten Lime" in output)
        ->
            setIdentify(output = 47)
        }
        when {("Fresh Okra" in output) || ("Rotten Okra" in output)
        ->
            setIdentify(output = 48)
        }
        when {("Fresh Orange" in output) || ("Rotten Orange" in output)
        ->
            setIdentify(output = 49)
        }
        when {("Fresh Pomegranate" in output) || ("Rotten Pomegranate" in output)
        ->
            setIdentify(output = 50)
        }
        when {("Fresh Potato" in output) || ("Rotten Potato" in output)
        ->
            setIdentify(output = 51)
        }
        when {("Fresh Tomato" in output) || ("Rotten Tomato" in output)
        ->
            setIdentify(output = 52)
        }
    }

    private fun setIdentify(output: Int?){
        detailViewModel.findDetail(output)
    }

    private fun updateProgressBar(){
        binding.progressPercentage.progress = progr
        binding.textViewResult.text = "$progr%"
    }

    private fun show(isLoad: Boolean) {
        binding.percent.visibility = if (isLoad) View.VISIBLE else View.GONE
        binding.vector3.visibility = if (isLoad) View.VISIBLE else View.GONE
        binding.progressPercentage.visibility = if (isLoad) View.VISIBLE else View.GONE
        binding.textViewResult.visibility = if (isLoad) View.VISIBLE else View.GONE
        binding.result.visibility = if (isLoad) View.VISIBLE else View.GONE
        binding.tips.visibility = if (isLoad) View.VISIBLE else View.GONE
        binding.vector2.visibility = if (isLoad) View.VISIBLE else View.GONE
        binding.rvHowToKeep.visibility = if (isLoad) View.VISIBLE else View.GONE
        binding.recipe.visibility = if (isLoad) View.VISIBLE else View.GONE
        binding.vector1.visibility = if (isLoad) View.VISIBLE else View.GONE
        binding.rvRecipe.visibility = if (isLoad) View.VISIBLE else View.GONE
    }

    private fun startGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"

        startActivityForResult(intent, 250)
    }

    private fun startCameraX() {
        val camera = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        camera.resolveActivity(requireActivity().packageManager)
        val app = requireActivity().application
        createTempFile(app).also {
            val photoURI: Uri = FileProvider.getUriForFile(
                requireContext(),
                "com.daniel.android_freshsnap",
                it
            )
            currentPhotoPath = it.absolutePath
            camera.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            startActivityForResult(camera, 200)

        }

    }

    lateinit var currentPhotoPath: String
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 250 && data != null){
            binding.previewImageView.setImageURI(data.data)

            val uri : Uri ?= data.data

            val myFile = uriToFile(data.data as Uri, requireContext())

            currentFile = myFile

            val resolver = requireActivity().contentResolver

            bitmap = MediaStore.Images.Media.getBitmap(resolver, uri)

            val image = Bitmap.createScaledBitmap(bitmap, imageSize, imageSize, true)
            binding.process.setOnClickListener{
                if(image != null) {
                    predictImage(image)
                    show(true)
                }else{
                    Toast.makeText(requireActivity(), "Please input image first!", Toast.LENGTH_SHORT).show()
                }
            }
        }
        else if(requestCode == 200 && resultCode == Activity.RESULT_OK){
            val myFile = File(currentPhotoPath)
            currentFile = myFile
            var result = rotateBitmap(BitmapFactory.decodeFile(myFile.path), true)

            val dimension = result.width.coerceAtMost(result.height)
            result = ThumbnailUtils.extractThumbnail(result, dimension, dimension)

            binding.previewImageView.setImageBitmap(result)
            result = Bitmap.createScaledBitmap(result, imageSize, imageSize, true)
            binding.process.setOnClickListener{
                if(result != null) {
                    predictImage(result)
                    show(true)
                }else{
                    Toast.makeText(requireActivity(), "Please input image first!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    companion object{
        var TAG = "IdentifyFragment"
    }

}