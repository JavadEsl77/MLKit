package com.javadesl.mlkit

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.javadesl.mlkit.databinding.ActivityMainBinding
import com.javadesl.mlkit.helpers.ImageHelperActivity
//import com.google.mlkit.samples.nl.translate.R

class MainActivity : AppCompatActivity() {

    private var _bindingMainActivity: ActivityMainBinding? = null
    private val bindingMainActivity get() = _bindingMainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _bindingMainActivity = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_bindingMainActivity!!.root)

        bindingMainActivity?.apply {
            btnImageHelper.setOnClickListener {
                startActivity(Intent(this@MainActivity, ImageHelperActivity::class.java))
            }
        }
    }

}