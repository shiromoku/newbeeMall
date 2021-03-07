package com.shiromoku.newbeeMall.entity

import org.json.JSONObject
import kotlin.contracts.contract

class Goods {
    private var goodsId:Int?
    private var goodsIntro:String?
    private var goodsName:String?
    private var sellingPrice:Int?
    constructor (goodsId:Int,goodsIntro:String,goodsName:String,sellingPrice:Int) {
        this.goodsId = goodsId
        this.goodsIntro = goodsIntro
        this.goodsName = goodsName
        this.sellingPrice = sellingPrice
    }

    constructor(jsonObject: JSONObject) {

        this.goodsId = jsonObject.getInt("goodsId")
        this.goodsIntro = jsonObject.getString("goodsName")
        this.goodsName = jsonObject.getString("goodsName")
        this.sellingPrice = jsonObject.getInt("sellingPrice")
    }


}