package nl.sajansen.timedscreenclicker.objects

import java.awt.Point
import java.util.*

data class Action(
    var timestamp: Date,
    var point: Point,
)