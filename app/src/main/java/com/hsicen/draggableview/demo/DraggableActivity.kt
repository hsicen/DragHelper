package com.hsicen.draggableview.demo

import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.hsicen.extension.extensions.viewBinding
import com.hsicen.draggableview.DraggableScrollView
import com.hsicen.draggableview.demo.databinding.ActivityDraggableBinding
import com.hsicen.draggableview.interfaces.DraggableListener

/**
 * 作者：hsicen  6/30/21 11:26
 * 邮箱：codinghuang@163.com
 * 功能：
 * 描述：利用ViewDragHelper实现（下拉拖拽关闭） 以及 （水平拖拽切换） 的功能
 */
class DraggableActivity : AppCompatActivity(), DraggableListener, DraggableScrollView.ScrollListener {
    private val binding by viewBinding(ActivityDraggableBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_draggable)
        initView()
    }

    private fun initView() {
        binding.dragView.setDraggableListener(this)
        binding.scrollView.setOnScrollListener(this)
    }

    override fun onClosedToBottom() {
        Toast.makeText(this, "关闭页面", Toast.LENGTH_SHORT).show()
        Handler().postDelayed({ finish() }, 500)
    }

    override fun onClosedToLeft() {
        Toast.makeText(this@DraggableActivity, "向左切换，加载下一个视频...", Toast.LENGTH_SHORT).show()
        Handler().postDelayed({
            binding.dragView.show()
            Toast.makeText(this@DraggableActivity, "视频数据加载完成", Toast.LENGTH_SHORT).show()
        }, 500)
    }

    override fun onClosedToRight() {
        Toast.makeText(this@DraggableActivity, "向右切换，加载上一个视频...", Toast.LENGTH_SHORT).show()
        Handler().postDelayed({
            binding.dragView.show()
            Toast.makeText(this@DraggableActivity, "视频数据加载完成", Toast.LENGTH_SHORT).show()
        }, 500)
    }

    override fun onBackgroundChanged(top: Int) {
        val newAlpha = 255 - (255 * (top.toFloat() / binding.dragView.rootView.height.toFloat())).toInt()
        if (newAlpha == 255) {
            binding.dragView.setBackgroundResource(R.mipmap.bg_gauss_blur)
        } else {
            binding.dragView.setBackgroundColor(ContextCompat.getColor(this, R.color.colorBackground))
        }
        binding.dragView.background.alpha = newAlpha
        if (newAlpha < 216) { //达到子控件缩放最小值，原大小的0.85倍
            //binding.scrollView.scaleX = 0.85f
            binding.scrollView.scaleY = 0.85f
        } else { // newAlpha >= 204 平滑缩放
            //binding.scrollView.scaleX = 1 - (255.0f - newAlpha.toFloat()) / 255
            binding.scrollView.scaleY = 1 - (255.0f - newAlpha.toFloat()) / 255
        }
    }

    override fun isOnTop(isTop: Boolean) {
        binding.dragView.setScrollToTop(isTop)
    }

    override fun onScrollChanged(tY: Int) {}

    override fun isForbidden(): Boolean = false
}