package io.jackson.layoutplayground

import android.content.res.Resources

val Int.dp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()
val Int.px: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

/**
 * Converts a percentage to an alpha value (0 - 255) for use with Android Views.
 * @param percentage - percentage represented by range 0 - 1
 */
fun percentageToAlpha(percentage: Float) = percentage * 255
