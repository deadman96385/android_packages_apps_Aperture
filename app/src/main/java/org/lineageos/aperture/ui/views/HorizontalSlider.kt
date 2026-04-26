/*
 * SPDX-FileCopyrightText: The LineageOS Project
 * SPDX-License-Identifier: Apache-2.0
 */

package org.lineageos.aperture.ui.views

import android.content.Context
import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent
import org.lineageos.aperture.ext.mapToRange

class HorizontalSlider @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : Slider(context, attrs) {
    override fun track(): RectF {
        val trackHeight = height / 5

        val left = height / 2f
        val right = width - left

        val top = (height - trackHeight) / 2f
        val bottom = height - top

        return RectF(left, top, right, bottom)
    }

    override fun disabledTrackSegments(track: RectF): List<RectF> = buildList {
        val min = allowedProgressRange.start
        val max = allowedProgressRange.endInclusive
        val minX = progressToX(track, min)
        val maxX = progressToX(track, max)

        if (min > 0f) {
            add(RectF(track.left, track.top, minX, track.bottom))
        }
        if (max < 1f) {
            add(RectF(maxX, track.top, track.right, track.bottom))
        }
    }


    override fun thumb(): Triple<Float, Float, Float> {
        val track = track()
        val trackWidth = track.width()

        val cx = if (steps > 0) {
            val progress = Int.mapToRange(0..steps, progress).toFloat() / steps
            (trackWidth * progress) + track.left
        } else {
            (trackWidth * progress) + track.left
        }
        val cy = height / 2f

        return Triple(cx, cy, height / 2.15f)
    }

    @Suppress("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        super.onTouchEvent(event)

        if (!isEnabled) {
            return false
        }

        when (event?.action) {
            MotionEvent.ACTION_DOWN,
            MotionEvent.ACTION_MOVE,
            MotionEvent.ACTION_UP -> {
                progress = clampProgress(event.x.coerceIn(0f, width.toFloat()) / width)
                onProgressChangedByUser?.invoke(progress)
            }
        }

        return true
    }

    private fun progressToX(track: RectF, progress: Float): Float {
        return (track.width() * progress) + track.left
    }
}
