package nl.sajansen.timedscreenclicker.objects

import nl.sajansen.timedscreenclicker.ApplicationRuntimeSettings
import nl.sajansen.timedscreenclicker.utils.clickMouse
import org.slf4j.LoggerFactory
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*
import kotlin.concurrent.fixedRateTimer

object Runner {
    private val logger = LoggerFactory.getLogger(Runner::class.java)
    private var timer: Timer? = null
    var nextCheckTime: Date? = null

    fun start() {
        timer = fixedRateTimer(
            name = "RunnerTimer",
            daemon = true,
            initialDelay = 0,
            period = 1000L * (if (ApplicationRuntimeSettings.useSeconds) 1 else 60),
        ) { step() }
    }

    private fun step() {
        val nowTime = Date().toInstant().atZone(ZoneId.systemDefault())
        nextCheckTime = Date.from(
            nowTime.plusSeconds((if (ApplicationRuntimeSettings.useSeconds) 1 else 60))
                .toInstant()
        )

        if (!State.isRunning) {
            return
        }
        logger.info("Checking")

        State.actions.filter { actionEqualToTime(it, nowTime) }
            .forEach { executeAction(it) }
    }

    private fun actionEqualToTime(it: Action, nowTime: ZonedDateTime): Boolean {
        val itTime = it.timestamp.toInstant().atZone(ZoneId.systemDefault())
        return (itTime.hour == nowTime.hour
                && itTime.minute == nowTime.minute
                && (!ApplicationRuntimeSettings.useSeconds || itTime.second == nowTime.second))
    }

    private fun executeAction(action: Action) {
        logger.info("Executing action: $action")
        clickMouse(action.point.x, action.point.y)
    }
}