package com.javadesl.mlkit.helpers

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.javadesl.mlkit.databinding.ActivityImageHelperBinding
import com.javadesl.mlkit.databinding.ActivityMainBinding

class ImageHelperActivity : AppCompatActivity() {

    private var _imageHelperBinding: ActivityImageHelperBinding? = null
    private val imageHelperBinding get() = _imageHelperBinding

    private val permissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {

            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _imageHelperBinding = ActivityImageHelperBinding.inflate(layoutInflater)
        setContentView(_imageHelperBinding!!.root)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                permissionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }
    }
}