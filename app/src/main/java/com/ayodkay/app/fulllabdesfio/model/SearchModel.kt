package com.ayodkay.app.fulllabdesfio.model

import android.graphics.Bitmap

data class SearchModel(val productName:String, val productPrice:String, val productImage: Bitmap, val productPaymentOption:String? = null)