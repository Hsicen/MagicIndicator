package com.hsicen.magicindicator.indicators

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.RectF
import android.graphics.drawable.Drawable
import android.util.TypedValue
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.hsicen.magicindicator.FragmentContainerHelper
import com.hsicen.magicindicator.R
import com.hsicen.magicindicator.abs.IPagerIndicator
import com.hsicen.magicindicator.model.PositionData

/**
 * 作者：hsicen  5/3/21 09:33
 * 邮箱：codinghuang@163.com
 * 功能：
 * 描述：Drawable Indicator
 */
class DrawableIndicator(context: Context) : View(context),
  IPagerIndicator {
  private var mPositionDataList: List<PositionData> = listOf()
  private val mRect = RectF()

  private var mLeftPad = 26.dp2px
  private var mRightPad = 26.dp2px
  private var mTopPad = 12.dp2px
  private var mBottomPad = 19.dp2px
  private var mDrawable = drawableRes(R.drawable.icon_type)
  private var mStartInterpolator = LinearInterpolator()
  private var mEndInterpolator = LinearInterpolator()

  override fun onDraw(canvas: Canvas?) {
    canvas ?: return
    mDrawable?.setBounds(
      mRect.left.toInt(),
      mRect.top.toInt(),
      mRect.right.toInt(),
      mRect.bottom.toInt()
    )
    mDrawable?.draw(canvas)
  }

  override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    if (mPositionDataList.isEmpty()) return

    // 计算锚点位置
    val current = FragmentContainerHelper.getImitativePositionData(mPositionDataList, position)
    val next = FragmentContainerHelper.getImitativePositionData(mPositionDataList, position + 1)

    mRect.left = current.mContentLeft - mLeftPad +
      (next.mContentLeft - current.mContentLeft) *
      mEndInterpolator.getInterpolation(positionOffset)
    mRect.top = (current.mContentTop - mTopPad).toFloat()
    mRect.right = current.mContentRight + mRightPad +
      (next.mContentRight - current.mContentRight) *
      mStartInterpolator.getInterpolation(positionOffset)
    mRect.bottom = (current.mContentBottom + mBottomPad).toFloat()

    invalidate()
  }

  override fun onPositionDataProvide(dataList: MutableList<PositionData>?) {
    dataList ?: return
    mPositionDataList = dataList
  }

  override fun onPageScrollStateChanged(state: Int) {}
  override fun onPageSelected(position: Int) {}

  fun updatePadding(
    left: Int = mLeftPad,
    top: Int = mTopPad,
    right: Int = mRightPad,
    bottom: Int = mBottomPad
  ) {
    mLeftPad = left
    mTopPad = top
    mRightPad = right
    mBottomPad = bottom
  }

  fun updateDrawable(@DrawableRes resId: Int) {
    mDrawable = drawableRes(resId)
  }

  fun updateDrawable(drawable: Drawable) {
    mDrawable = drawable
  }

  private fun Context.drawableRes(@DrawableRes resId: Int) = ContextCompat.getDrawable(this, resId)

  private fun View.drawableRes(@DrawableRes resId: Int) = context.drawableRes(resId)

  private val Int.dp2px: Int
    get() = TypedValue.applyDimension(
      TypedValue.COMPLEX_UNIT_DIP, this * 1.0f, Resources.getSystem().displayMetrics
    ).toInt()
}