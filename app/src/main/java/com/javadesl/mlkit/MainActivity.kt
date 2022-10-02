package com.javadesl.mlkit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.javadesl.mlkit.databinding.ActivityMainBinding
import com.javadesl.mlkit.helpers.ImageHelperActivity

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