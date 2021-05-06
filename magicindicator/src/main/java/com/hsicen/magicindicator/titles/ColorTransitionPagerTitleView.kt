package com.hsicen.magicindicator.titles

import android.content.Context
import com.hsicen.magicindicator.ArgbEvaluatorHolder

/**
 * 作者：黄思程  5/6/21 6:02 PM
 * 邮箱：huangsicheng@camera360.com
 * 功能：
 * 描述：两种颜色过渡的指示器标题
 */
open class ColorTransitionPagerTitleView(context: Context) : SimplePagerTitleView(context) {
    var onSelect: ((Int, Int) -> Unit)? = null
    var onDeselect: ((Int, Int) -> Unit)? = null

    override fun onLeave(index: Int, totalCount: Int, leavePercent: Float, leftToRight: Boolean) {
        val color = ArgbEvaluatorHolder.eval(leavePercent, selectedColor, normalColor)
        setTextColor(color)
    }

    override fun onEnter(index: Int, totalCount: Int, enterPercent: Float, leftToRight: Boolean) {
        val color = ArgbEvaluatorHolder.eval(enterPercent, normalColor, selectedColor)
        setTextColor(color)
    }

    override fun onSelected(index: Int, totalCount: Int) {
        onSelect?.invoke(index, totalCount)
    }

    override fun onDeselected(index: Int, totalCount: Int) {
        onDeselect?.invoke(index, totalCount)
    }

    fun onSelectChange(
        onSelect: ((Int, Int) -> Unit)? = null,
        onDeselect: ((Int, Int) -> Unit)? = null
    ) {
        this.onSelect = onSelect
        this.onDeselect = onDeselect
    }
}