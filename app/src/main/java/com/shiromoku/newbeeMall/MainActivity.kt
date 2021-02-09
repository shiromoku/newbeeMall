package com.shiromoku.newbeeMall

import android.app.Activity
import android.graphics.BitmapFactory
import android.os.Bundle
import android.text.Layout
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.shiromoku.newbeeMall.fragment.CategoryFragment
import com.shiromoku.newbeeMall.fragment.HomeFragment
import com.shiromoku.newbeeMall.fragment.MineFragment
import com.shiromoku.newbeeMall.tools.BitmapTools

class MainActivity : FragmentActivity() {
    lateinit var frameLayout: FrameLayout
    lateinit var radioGroup: RadioGroup
    lateinit var radioButtonHome: RadioButton
    lateinit var radioButtonCategory: RadioButton
    lateinit var radioButtonMine: RadioButton
    var fromFragment: Fragment? = null
    var position = 0
    val fragmentList = mutableListOf<Fragment>()
    lateinit var userAvatar: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initPart()
        setListener()
//        Thread.sleep(5000)
        radioGroup.check(R.id.main_radioButton_home)
        initUserAvatar()
    }

    private fun initFragmentList() {
        fragmentList.add(HomeFragment())
        fragmentList.add(CategoryFragment())
        fragmentList.add(MineFragment())
    }

    private fun setListener() {
        radioGroup.setOnCheckedChangeListener(MainOnCheckedChangeListener())
    }

    inner class MainOnCheckedChangeListener: RadioGroup.OnCheckedChangeListener {
        override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
            val topBar:View = findViewById(R.id.main_include)
            when(checkedId){
                R.id.main_radioButton_home -> {
                    position = 0
                    topBar.visibility = View.VISIBLE
                }
                R.id.main_radioButton_category -> {
                    position = 1
                    topBar.visibility = View.VISIBLE
                }
                R.id.main_radioButton_mine -> {
                    position = 2
                    topBar.visibility = View.GONE
                }
                else -> position = 0
            }

            switchFragment(fromFragment,getFragment())
        }
    }

    private fun switchFragment(from:Fragment?,to:Fragment) {
        if(to != from){
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            from?.let { fragmentTransaction.hide(it) }


            try {
                if(!to.isAdded){
                    fragmentTransaction.add(R.id.main_FrameLayout,to)
                }else{
                    fragmentTransaction.show(to)
                }
            } catch (e: java.lang.IllegalStateException) {

            }

            fromFragment = to

            fragmentTransaction.commit()
        }
    }

    private fun getFragment(): Fragment {
        return fragmentList[position]
    }


    private fun initUserAvatar() {
        val bitmap = BitmapFactory.decodeResource(resources,R.drawable.test_avatar)
        val outBitmap = BitmapTools.getRoundBitmapByShader(bitmap,48,48,24,0)
        userAvatar.setImageBitmap(outBitmap)
    }

    private fun initPart() {
        frameLayout = findViewById(R.id.main_FrameLayout)
        radioGroup = findViewById(R.id.main_radioGroup)
        radioButtonHome = findViewById(R.id.main_radioButton_home)
        radioButtonCategory = findViewById(R.id.main_radioButton_category)
        radioButtonMine = findViewById(R.id.main_radioButton_mine)
        userAvatar = findViewById(R.id.top_bar_image_avatar)

        initFragmentList()
    }
}