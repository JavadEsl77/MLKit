package com.javadesl.mlkit.helpers

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.label.ImageLabeler
import com.google.mlkit.vision.label.ImageLabeling
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions
import com.javadesl.mlkit.databinding.ActivityImageHelperBinding
import java.io.IOException


class ImageHelperActivity : AppCompatActivity() {

    private var _imageHelperBinding: ActivityImageHelperBinding? = null
    private val imageHelperBinding get() = _imageHelperBinding
    var labeler: ImageLabeler? = null
    private val permissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {

            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val options = ImageLabelerOptions.Builder()
            .setConfidenceThreshold(0.7f)
            .build()
        labeler = ImageLabeling.getClient(options)


        _imageHelperBinding = ActivityImageHelperBinding.inflate(layoutInflater)
        setContentView(_imageHelperBinding!!.root)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                permissionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }


        imageHelperBinding?.apply {
            btnPickImage.setOnClickListener {

                val intentPickImage = Intent()
                intentPickImage.type = "image/*"
                intentPickImage.action = Intent.ACTION_GET_CONTENT
                startActivityForResult(intentPickImage, 100);
            }
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == 100) {
                val uri: Uri? = data?.data
                val bitmap: Bitmap? = uri?.let { loadFromUrl(it) }
                imageHelperBinding?.imgImage?.setImageBitmap(bitmap)
                bitmap?.let { runClassification(it) }
            }
        }
    }

    private fun loadFromUrl(uri: Uri): Bitmap? {
        var bitmap: Bitmap? = null
        try {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O_MR1) {
                val source: ImageDecoder.Source = ImageDecoder.createSource(contentResolver, uri)
                bitmap = ImageDecoder.decodeBitmap(source)
            } else {
                bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return bitmap
    }

    private fun runClassification(bitmap: Bitmap) {
        val inputImage: InputImage = InputImage.fromBitmap(bitmap, 0)
        labeler?.process(inputImage)
            ?.addOnSuccessListener { labels ->
                if (labels.size > 0) {
                    val stringBuilder = StringBuilder()
                    for (label in labels) {
                        val text = label.text
                        stringBuilder.append(text)
                        stringBuilder.append("\n")
                    }
                    imageHelperBinding?.textViewOutput?.text=stringBuilder

                } else {
                    imageHelperBinding?.textViewOutput?.text = "could not classify"
                }
            }
            ?.addOnFailureListener { e ->
                e.printStackTrace()
            }

    }

}