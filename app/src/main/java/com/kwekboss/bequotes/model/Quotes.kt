package com.kwekboss.bequotes.model


import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Quotes(
    val author:String? = null,
    val quote:String? = null
) : Parcelable
