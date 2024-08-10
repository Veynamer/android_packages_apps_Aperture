/*
 * SPDX-FileCopyrightText: 2022 The LineageOS Project
 * SPDX-License-Identifier: Apache-2.0
 */

package org.lineageos.aperture.ui

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import com.google.android.material.slider.Slider

class HorizontalSlider @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : Slider(context, attrs) {

    init {
        // Set Material You style attributes
        trackColorActive = context.getColor(R.color.your_active_color)
        trackColorInactive = context.getColor(R.color.your_inactive_color)
        thumbColor = context.getColor(R.color.your_thumb_color)

        // Optional: Set default values
        valueFrom = 0f
        valueTo = 100f
        value = 50f // Set an initial value if needed

        // Optional: Set a step size
        stepSize = 1f
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        super.onTouchEvent(event)

        if (!isEnabled) {
            return false
        }

        when (event?.action) {
            MotionEvent.ACTION_DOWN,
            MotionEvent.ACTION_MOVE,
            MotionEvent.ACTION_UP -> {
                // Update the progress based on touch
                value = event.x.coerceIn(0f, width.toFloat()) / width * (valueTo - valueFrom) + valueFrom
                onProgressChangedByUser?.invoke(value)
            }
        }

        return true
    }
}
