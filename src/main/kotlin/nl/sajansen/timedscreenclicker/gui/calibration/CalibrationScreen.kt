package nl.sajansen.timedscreenclicker.gui.calibration

import nl.sajansen.timedscreenclicker.events.EventsDispatcher
import nl.sajansen.timedscreenclicker.utils.gui.DefaultDialogKeyDispatcher
import nl.sajansen.timedscreenclicker.utils.notifications.Notifications
import org.slf4j.LoggerFactory
import java.awt.KeyboardFocusManager
import java.awt.Point
import java.awt.image.BufferedImage
import javax.swing.JFrame

class CalibrationScreen(
    private val onSubmitCallback: (selectedPoint: Point?) -> Unit = {},
    private val screenshot: BufferedImage? = null
) : JFrame() {
    private val logger = LoggerFactory.getLogger(CalibrationScreen::class.java.name)

    companion object {
        private var instance: CalibrationScreen? = null
        fun getInstance() = instance

        fun create(
            onSubmitCallback: (selectedPoint: Point?) -> Unit = {},
            screenshot: BufferedImage? = null
        ) = CalibrationScreen(
            onSubmitCallback = { onSubmitCallback(it) },
            screenshot = screenshot
        )

        fun createAndShow(
            onSubmitCallback: (selectedPoint: Point?) -> Unit = {},
            screenshot: BufferedImage? = null
        ): CalibrationScreen {
            val frame = create(
                onSubmitCallback = { onSubmitCallback(it) },
                screenshot = screenshot
            )
            frame.isVisible = true
            EventsDispatcher.currentFrame = frame
            return frame
        }
    }

    init {
        instance = this

        KeyboardFocusManager
            .getCurrentKeyboardFocusManager()
            .addKeyEventDispatcher(DefaultDialogKeyDispatcher(this))

        initGUI()
    }

    private fun initGUI() {
        add(
            CalibrationScreenPanel(
                onSubmitCallback = { onSubmit(it) },
                screenshot = screenshot
            )
        )

        setFullscreen(true)

        defaultCloseOperation = EXIT_ON_CLOSE
        title = "Calibration screen"
    }

    private fun setFullscreen(value: Boolean) {
        val graphicsDevice = graphicsConfiguration.device

        if (value) {
            logger.info("Enabling fullscreen")
            if (!graphicsDevice.isFullScreenSupported) {
                logger.info("Fullscreen not supported on this graphics device: $graphicsDevice")
                Notifications.popup("Fullscreen is not supported by your graphics device", "GUI")
                return
            }

            graphicsDevice.fullScreenWindow = this
        } else {
            logger.info("Disabling fullscreen")
            if (graphicsDevice.fullScreenWindow == this) {
                graphicsDevice.fullScreenWindow = null
            }
        }
    }

    private fun onSubmit(selectedPoint: Point?) {
        isVisible = false
        dispose()
        onSubmitCallback(selectedPoint)
    }
}