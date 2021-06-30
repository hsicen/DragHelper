package com.hsicen.draggableview.demo

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.hsicen.extension.extensions.clickThrottle
import com.hsicen.extension.extensions.viewBinding
import com.hsicen.extension.toast.info
import com.hsicen.draggableview.DraggableScrollView
import com.hsicen.draggableview.demo.databinding.ActivityVieoPlayerBinding
import com.hsicen.draggableview.interfaces.DraggableListener

/**
 * 作者：hsicen  6/30/21 11:27
 * 邮箱：codinghuang@163.com
 * 功能：
 * 描述：分开两个控件来处理事件
 * 垂直方向利用DragHelper，水平方向自行处理。
 * 用于解决用一个DraggableView不方便分发给子控件触摸事件的问题。
 */
class VideoPlayerActivity : AppCompatActivity(), DraggableListener, DraggableScrollView.ScrollListener {
    private val binding by viewBinding(ActivityVieoPlayerBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vieo_player)
        initView()
    }

    private fun initView() {
        binding.dragViewVertical.setDraggableListener(this)
        binding.dragViewHor.setDraggableListener(this)
        binding.scrollView.setOnScrollListener(this)
        //如果是第一项或者最后一项则不能左右切换了。设置false
        binding.dragViewHor.setFirst(false)
        binding.dragViewHor.setLast(false)

        binding.tvVideoContent.clickThrottle {
            info("点击视频内容", this)
        }
    }

    override fun onClosedToBottom() {
        info("关闭页面", this)

        Handler().postDelayed({
            finish()
        }, 500)
    }

    override fun onClosedToLeft() {
        info("向左滑动", this)
        Handler().postDelayed({ binding.dragViewHor.show() }, 500)
    }

    override fun onClosedToRight() {
        info("向右滑动", this)
        Handler().postDelayed({ binding.dragViewHor.show() }, 500)
    }

    override fun onBackgroundChanged(top: Int) {
        val newAlpha = 255 - (255 * (top.toFloat() / binding.lvVideo.rootView.height.toFloat())).toInt()
        if (newAlpha == 255) {
            binding.dragViewVertical.setBackgroundResource(R.mipmap.bg_gauss_blur)
        } else {
            binding.dragViewVertical.setBackgroundColor(ContextCompat.getColor(this, R.color.colorBackground))
        }
        binding.dragViewVertical.background.alpha = newAlpha
        if (newAlpha < 216) { //达到子控件缩放最小值，原大小的0.85倍
            binding.scrollView.scaleX = 0.85f
            binding.scrollView.scaleY = 0.85f
        } else { // newAlpha >= 204 平滑缩放
            binding.scrollView.scaleX = 1 - (255.0f - newAlpha.toFloat()) / 255
            binding.scrollView.scaleY = 1 - (255.0f - newAlpha.toFloat()) / 255
        }
    }

    override fun isForbidden(): Boolean = false

    override fun isOnTop(isTop: Boolean) {
        binding.dragViewVertical.setScrollToTop(isTop)
    }

    override fun onScrollChanged(tY: Int) {

    }
}