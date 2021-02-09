package com.shiromoku.newbeeMall.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter

class HomeFragmentViewPagerAdapter(imageViewList: MutableList<ImageView>) : PagerAdapter() {
    var imageViewList = imageViewList

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val realPosition = position % imageViewList.size
        val img = imageViewList[realPosition]
        container.addView(img)
        return img
    }

    override fun getCount(): Int {
//        return Int.MAX_VALUE
        return imageViewList.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}