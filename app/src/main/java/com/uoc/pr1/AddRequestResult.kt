package com.uoc.pr1

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize


const val PARAM_ADDREQUESTRESULT_CLASS = "com.uoc.pr1.parcel_addrequestresult"

@Parcelize
data class AddSeminarResult(val title: String,val duration: String,val level: String,val uri: String): Parcelable
