package nl.sajansen.timedscreenclicker

import nl.sajansen.timedscreenclicker.events.EventsDispatcher
import nl.sajansen.timedscreenclicker.gui.mainFrame.MainFrame
import nl.sajansen.timedscreenclicker.objects.Runner
import nl.sajansen.timedscreenclicker.utils.getCurrentJarDirectory
import org.slf4j.LoggerFactory
import java.awt.EventQueue
import kotlin.system.exitProcess

private val logger = LoggerFactory.getLogger("Application")

fun main(args: Array<String>) {
    ApplicationRuntimeSettings.testing = false

    logger.info("Starting application ${ApplicationInfo.artifactId}:${ApplicationInfo.version}")
    logger.info("Executing JAR directory: " + getCurrentJarDirectory(ApplicationInfo).absolutePath)

//    State.actions.add(Action(Date(), Point(100, 50)))
//    State.actions.add(Action(Date(), Point(100, 50)))

    EventQueue.invokeLater {
        MainFrame.createAndShow()
    }

    Runner.start()
}

fun exitApplication() {
    logger.info("Shutting down application")

    try {
        logger.info("Closing windows...")
        EventsDispatcher.windowClosing(MainFrame.getInstance())
    } catch (t: Throwable) {
        logger.warn("Failed to correctly close windows")
        t.printStackTrace()
    }

    logger.info("Shutdown finished")
    exitProcess(0)
}
