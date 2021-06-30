package com.hsicen.draggableview.demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hsicen.extension.extensions.clickThrottle
import com.hsicen.extension.extensions.startActivity
import com.hsicen.extension.extensions.viewBinding
import com.hsicen.draggableview.demo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by viewBinding(ActivityMainBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding.btnStartDraggable.clickThrottle {
            startActivity<DraggableActivity>()
        }

        binding.btnStartPlayer.clickThrottle {
            startActivity<VideoPlayerActivity>()
        }
    }
}