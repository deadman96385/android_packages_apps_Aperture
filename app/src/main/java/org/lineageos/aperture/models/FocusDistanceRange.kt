/*
 * SPDX-FileCopyrightText: The LineageOS Project
 * SPDX-License-Identifier: Apache-2.0
 */

package org.lineageos.aperture.models

/**
 * Manual focus range for a logical camera or one of its physical cameras.
 *
 * Camera2 reports focus distance in diopters. 0 means infinity, and larger values move focus
 * closer to the camera.
 */
data class FocusDistanceRange(
    val cameraId: String,
    val maximumDistance: Float,
)
