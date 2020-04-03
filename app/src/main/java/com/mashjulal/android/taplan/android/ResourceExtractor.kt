package com.mashjulal.android.taplan.android

import android.content.Context
import androidx.annotation.StringRes

class ResourceExtractor(private val context: Context) {

    fun getString(@StringRes strRes: Int): String = context.getString(strRes)

    fun getString(@StringRes strRes: Int, vararg formatArgs: Any): String = context.getString(strRes, *formatArgs)
}