package com.shiromoku.newbeeMall.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.shiromoku.newbeeMall.R
import com.shiromoku.newbeeMall.adapter.HomeFragmentViewPagerAdapter
import java.lang.Thread.sleep


class HomeFragment : Fragment() {
    lateinit var viewPager: ViewPager
    lateinit var textView: TextView
    lateinit var dotLinear: LinearLayout

    lateinit var homeHandler: Handler




    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = View.inflate(activity, R.layout.fragment_home, null)
        viewPager = view.findViewById(R.id.home_fragment_view_pager)
        textView = view.findViewById(R.id.home_fragment_text)
        dotLinear = view.findViewById(R.id.home_fragment_linear_dot)

        homeHandler = object : Handler(Looper.getMainLooper()){
            override fun handleMessage(msg: Message) {
                when(msg.what){
                    100 -> {
                        when (msg.arg1) {
                            101 -> {
                                //change page view
                                var position = viewPager.currentItem
                                position++
                                position %= viewPager.adapter?.count ?: position
                                viewPager.currentItem = position
                            }
                        }
                    }
                }
            }
        }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        loadData()
    }

    private fun loadData() {
        initViewPager()
    }

    private fun initViewPager() {
        val mImg = arrayListOf(R.drawable.banner1, R.drawable.banner2)
        val mDec = arrayListOf("banner1", "banner2")

        var previousSelectedPosition = 0
        val mImgList = mutableListOf<ImageView>()
        var imageView: ImageView?
        var linearParams: LinearLayout.LayoutParams?
        for(i in mImg.indices){
            imageView = ImageView(this.context)
            imageView.setImageResource(mImg[i])
            mImgList.add(imageView)

            val dotView = View(this.context)
            dotView.setBackgroundResource(R.drawable.home_fragment_dot_selector)
            linearParams = LinearLayout.LayoutParams(30, 30)
            dotView.isEnabled = false
            dotLinear.addView(dotView, linearParams)
        }

        dotLinear.getChildAt(0).isEnabled = true
        textView.text = mDec[0]

        viewPager.adapter = HomeFragmentViewPagerAdapter(mImgList)

        viewPager.currentItem = 0
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                textView.text = mDec[position]
                dotLinear.getChildAt(previousSelectedPosition).isEnabled = false
                dotLinear.getChildAt(position).isEnabled = true
                previousSelectedPosition = position
            }

            override fun onPageScrollStateChanged(state: Int) {

            }

        })

        Thread{
            while (true) {
                try {
                    sleep(7000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
                val msg = Message()
                msg.what = 100
                msg.arg1 = 101
                homeHandler.sendMessage(msg)
            }
        }.start()
    }


}