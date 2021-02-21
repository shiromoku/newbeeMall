package com.shiromoku.newbeeMall.fragment

import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.shiromoku.newbeeMall.R
import com.shiromoku.newbeeMall.adapter.HomeFragmentViewPagerAdapter
import com.shiromoku.newbeeMall.database.Database
import com.shiromoku.newbeeMall.tools.NetworkTools
import com.shiromoku.shiromokulearn.tool.PathTool
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Thread.sleep
import java.net.HttpURLConnection
import java.net.URL


class HomeFragment : Fragment() {
    lateinit var viewPager: ViewPager
    lateinit var textView: TextView
    lateinit var dotLinear: LinearLayout

    lateinit var homeHandler: Handler

    lateinit var imageDatabase: Database
    lateinit var keyValueDatabase: Database

    lateinit var carousels: JSONArray
    lateinit var hotGoodses: JSONArray
    lateinit var newGoodses: JSONArray

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = View.inflate(activity, R.layout.fragment_home, null)
        viewPager = view.findViewById(R.id.home_fragment_view_pager)
        textView = view.findViewById(R.id.home_fragment_text)
        dotLinear = view.findViewById(R.id.home_fragment_linear_dot)

        imageDatabase = Database(context!!, "goodsImage")
        keyValueDatabase = Database(context!!, "keyValue")




        homeHandler = object : Handler(Looper.getMainLooper()) {
            override fun handleMessage(msg: Message) {
                //100 UI
                //200 Network
                when (msg.what) {
                    100 -> {
                        when (msg.arg1) {
                            101 -> {
                                //change page view
                                var position = viewPager.currentItem
                                position++
                                position %= viewPager.adapter?.count ?: position
                                viewPager.currentItem = position
                            }
                            102 -> {
                                initViewPager()
                            }
                        }
                    }
                    200 -> {
                        when (msg.arg1) {
                            416 -> {
                                Toast.makeText(context, "您尚未登陆", Toast.LENGTH_LONG).show()
                            }
                            500 -> {
                                Toast.makeText(context, "发生了不可名状的错误", Toast.LENGTH_LONG).show()
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
        getDataFromServer()
    }

    private fun getDataFromServer() {
        Thread {
            val url = URL(getString(R.string.root) + getString(R.string.index))
            val connect = url.openConnection() as HttpURLConnection
            val data = connect.inputStream.readBytes()
            val resultJson = JSONObject(data.decodeToString())
            if (resultJson.getString("resultCode") != "200") {
                val msg = Message()
                msg.what = 200
                msg.arg1 = resultJson.getString("resultCode").toInt()
                homeHandler.sendMessage(msg)
                return@Thread
            }
            val resultData = resultJson.getJSONObject("data")
            carousels = resultData.getJSONArray("carousels")
            hotGoodses = resultData.getJSONArray("hotGoodses")
            newGoodses = resultData.getJSONArray("newGoodses")

            var flag = 0
            var databaseValue = ""
            for (i in 0 until carousels.length()) {
                val imageUrl = carousels.getJSONObject(i).getString("carouselUrl")
                val fileName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1)
                databaseValue += if(i==0) {
                    fileName.substring(0,fileName.lastIndexOf("."))
                } else {
                    "," + fileName.substring(0,fileName.lastIndexOf("."))
                }
                if (NetworkTools.downloadFile(imageUrl, "/banner", context) == 200) {
                    val contentValues = ContentValues()
                    contentValues.put("key", fileName.substring(0,fileName.lastIndexOf(".")))
                    contentValues.put("value", "/banner/$fileName")
                    keyValueDatabase.save(contentValues,null)
                } else {
                    flag = -1
                }
            }
            if (flag == 0) {
                val contentValues = ContentValues()
                contentValues.put("key", "banner")
                contentValues.put("value", databaseValue)
                keyValueDatabase.save(contentValues, arrayOf("key"))
            }

            val msg = Message()
            msg.what = 100
            msg.arg1 = 102
            homeHandler.sendMessage(msg)
        }.start()
    }

    private fun initViewPager() {
//        val mImg = arrayListOf(R.drawable.banner1, R.drawable.banner2)
//        val mDec = arrayListOf("banner1", "banner2")
        var imageBitmapArray = emptyArray<Bitmap>()

        val hasBanner = keyValueDatabase.find(null, arrayOf("key"), arrayOf("banner"))
        if(hasBanner.isNotEmpty()){
            Log.e("homeFragment", "initViewPager: " + hasBanner.contentToString(), )
            val bannerFileName = hasBanner[0].getAsString("value").split(",")

            for(i in bannerFileName) {
                val bannerFilePath = keyValueDatabase.find(null, arrayOf("key"), arrayOf(i))
                for(k in bannerFilePath){
                    val bitmap = BitmapFactory.decodeFile(
                        PathTool.getExternalStorageDataAbsolutePath(
                            context,
                            k.getAsString("value")
                        )
                    )
                    imageBitmapArray += bitmap
                }

            }
        }


        var previousSelectedPosition = 0
        val mImgList = mutableListOf<ImageView>()
        var imageView: ImageView?
        var linearParams: LinearLayout.LayoutParams?
        for (i in imageBitmapArray.indices) {
            imageView = ImageView(this.context)
//            imageView.setImageResource(mImg[i])
            imageView.setImageBitmap(imageBitmapArray[i])
            mImgList.add(imageView)

            val dotView = View(this.context)
            dotView.setBackgroundResource(R.drawable.home_fragment_dot_selector)
            linearParams = LinearLayout.LayoutParams(30, 30)
            dotView.isEnabled = false
            dotLinear.addView(dotView, linearParams)
        }

        dotLinear.getChildAt(0).isEnabled = true
//        textView.text = mDec[0]

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
//                textView.text = mDec[position]
                dotLinear.getChildAt(previousSelectedPosition).isEnabled = false
                dotLinear.getChildAt(position).isEnabled = true
                previousSelectedPosition = position
            }

            override fun onPageScrollStateChanged(state: Int) {

            }

        })

        Thread {
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