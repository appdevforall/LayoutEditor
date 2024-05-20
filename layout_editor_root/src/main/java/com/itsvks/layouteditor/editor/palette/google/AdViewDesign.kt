package com.itsvks.layouteditor.editor.palette.google

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import com.google.android.gms.ads.BaseAdView
import com.itsvks.layouteditor.utils.Constants
import com.itsvks.layouteditor.utils.Utils

class AdViewDesign : BaseAdView {

  private var drawStrokeEnabled = false
  private var isBlueprint = false

  constructor(context: Context) : super(context, 0)
  constructor(context: Context, attrs: AttributeSet) : super(context, attrs, 0)
  constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
    context,
    attrs,
    defStyle,
    0
  )

  override fun dispatchDraw(canvas: Canvas) {
    super.dispatchDraw(canvas)
    if (drawStrokeEnabled) Utils.drawDashPathStroke(
      this, canvas, if (isBlueprint) Constants.BLUEPRINT_DASH_COLOR else Constants.DESIGN_DASH_COLOR
    )
  }

  override fun draw(canvas: Canvas) {
    if (isBlueprint) Utils.drawDashPathStroke(this, canvas, Constants.BLUEPRINT_DASH_COLOR)
    else super.draw(canvas)
  }

  fun setStrokeEnabled(enabled: Boolean) {
    drawStrokeEnabled = enabled
    invalidate()
  }

  fun setBlueprint(isBlueprint: Boolean) {
    this.isBlueprint = isBlueprint
    invalidate()
  }
}