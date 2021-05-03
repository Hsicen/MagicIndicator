package com.hsicen.magicindicator.titles

import android.content.Context
import android.graphics.Rect
import android.text.TextUtils
import android.view.Gravity
import android.widget.TextView
import com.hsicen.magicindicator.abs.IMeasurablePagerTitleView
import com.hsicen.magicindicator.dp2px

/**
 * 作者：hsicen  5/3/21 09:19
 * 邮箱：codinghuang@163.com
 * 功能：
 * 描述：文字标题
 */
open class SimplePagerTitleView(context: Context) :
    TextView(context, null),
    IMeasurablePagerTitleView {
    var selectedColor = 0
    var normalColor = 0

    var onSelect: ((Int, Int) -> Unit)? = null
    var onDeselect: ((Int, Int) -> Unit)? = null

    init {
        isSingleLine = true
        gravity = Gravity.CENTER
        ellipsize = TextUtils.TruncateAt.END
        setPadding(10.dp2px, 0, 10.dp2px, 0)
    }

    override fun onSelected(index: Int, totalCount: Int) {
        setTextColor(selectedColor)
        onSelect?.invoke(index, totalCount)
    }

    override fun onDeselected(index: Int, totalCount: Int) {
        setTextColor(normalColor)
        onDeselect?.invoke(index, totalCount)
    }

    override fun onLeave(index: Int, totalCount: Int, leavePercent: Float, leftToRight: Boolean) {}
    override fun onEnter(index: Int, totalCount: Int, enterPercent: Float, leftToRight: Boolean) {}
    override fun getContentLeft(): Int {
        val bound = Rect()
        var longestString = ""
        if (text.toString().contains("\n")) {
            val brokenStrings = text.toString().split("\\n").toTypedArray()
            for (each in brokenStrings) {
                if (each.length > longestString.length) longestString = each
            }
        } else {
            longestString = text.toString()
        }
        paint.getTextBounds(longestString, 0, longestString.length, bound)
        val contentWidth = bound.width()
        return left + width / 2 - contentWidth / 2
    }

    override fun getContentTop(): Int {
        val metrics = paint.fontMetrics
        val contentHeight = metrics.bottom - metrics.top
        return (height / 2 - contentHeight / 2).toInt()
    }

    override fun getContentRight(): Int {
        val bound = Rect()
        var longestString = ""
        if (text.toString().contains("\n")) {
            val brokenStrings = text.toString().split("\\n").toTypedArray()
            for (each in brokenStrings) {
                if (each.length > longestString.length) longestString = each
            }
        } else {
            longestString = text.toString()
        }
        paint.getTextBounds(longestString, 0, longestString.length, bound)
        val contentWidth = bound.width()
        return left + width / 2 + contentWidth / 2
    }

    override fun getContentBottom(): Int {
        val metrics = paint.fontMetrics
        val contentHeight = metrics.bottom - metrics.top
        return (height / 2 + contentHeight / 2).toInt()
    }

    fun onSelectChange(
        onSelect: ((Int, Int) -> Unit)? = null,
        onDeselect: ((Int, Int) -> Unit)? = null
    ) {
        this.onSelect = onSelect
        this.onDeselect = onDeselect
    }
}