/*
 * SPDX-FileCopyrightText: 2022-2023 The LineageOS Project
 * SPDX-License-Identifier: Apache-2.0
 */

package org.lineageos.aperture.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.google.android.material.slider.Slider
import org.lineageos.aperture.R

class MaterialSlider @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val slider: Slider

    var progress: Float
        get() = slider.value
        set(value) {
            slider.value = value.coerceIn(slider.valueFrom, slider.valueTo)
        }

    var onProgressChangedByUser: ((value: Float) -> Unit)? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.material_slider_layout, this, true)
        slider = findViewById(R.id.material_slider)

        context.obtainStyledAttributes(attrs, R.styleable.MaterialSlider, 0, 0).apply {
            try {
                slider.valueFrom = getFloat(R.styleable.MaterialSlider_valueFrom, 0f)
                slider.valueTo = getFloat(R.styleable.MaterialSlider_valueTo, 1f)
                slider.stepSize = getFloat(R.styleable.MaterialSlider_stepSize, 0.1f)
                slider.trackColorActive = getColor(R.styleable.MaterialSlider_trackColorActive, ContextCompat.getColor(context, R.color.default_active_color))
                slider.trackColorInactive = getColor(R.styleable.MaterialSlider_trackColorInactive, ContextCompat.getColor(context, R.color.default_inactive_color))
                slider.thumbColor = getColor(R.styleable.MaterialSlider_thumbColor, ContextCompat.getColor(context, R.color.default_thumb_color))
            } finally {
                recycle()
            }
        }

        slider.addOnChangeListener { _, value, _ ->
            onProgressChangedByUser?.invoke(value)
        }
    }
}
