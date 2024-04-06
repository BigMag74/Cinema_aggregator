package com.example.cinemaaggregator.common

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cinemaaggregator.databinding.ActivityRootBinding

class RootActivity : AppCompatActivity() {

    private val binding: ActivityRootBinding by lazy { ActivityRootBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}