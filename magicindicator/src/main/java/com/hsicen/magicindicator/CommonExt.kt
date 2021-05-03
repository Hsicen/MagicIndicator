package com.hsicen.magicindicator

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue
import android.view.View
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat

/**
 * 作者：hsicen  5/3/21 09:11
 * 邮箱：codinghuang@163.com
 * 作用：
 * 描述：MagicIndicator
 */

fun Context.drawableRes(@DrawableRes resId: Int) = ContextCompat.getDrawable(this, resId)

fun View.drawableRes(@DrawableRes resId: Int) = context.drawableRes(resId)

val Int.dp2px: Int
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, this * 1.0f, Resources.getSystem().displayMetrics
    ).toInt()

val Float.dp2px: Float
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, this, Resources.getSystem().displayMetrics
    )
