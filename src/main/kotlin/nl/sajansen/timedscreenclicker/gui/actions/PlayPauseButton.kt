package nl.sajansen.timedscreenclicker.gui.actions


import nl.sajansen.timedscreenclicker.events.DataEventListener
import nl.sajansen.timedscreenclicker.events.EventsDispatcher
import nl.sajansen.timedscreenclicker.objects.State
import nl.sajansen.timedscreenclicker.utils.gui.createImageIcon
import org.slf4j.LoggerFactory
import javax.swing.JButton

class PlayPauseButton : JButton(), DataEventListener {
    private val logger = LoggerFactory.getLogger(PlayPauseButton::class.java.name)

    init {
        EventsDispatcher.register(this)

        createGui()

        onRunningStateUpdated()
    }

    private fun createGui() {
        addActionListener { onClick() }
    }

    override fun onRunningStateUpdated() {
        if (State.isRunning) {
            icon = createImageIcon("/nl/sajansen/timedscreenclicker/icons/outline_pause_black_24dp.png")
            toolTipText = "Running..."
        } else {
            icon = createImageIcon("/nl/sajansen/timedscreenclicker/icons/outline_play_arrow_black_24dp.png")
            toolTipText = "Click to run"
        }
    }

    private fun onClick() {
        State.isRunning = !State.isRunning
        EventsDispatcher.onRunningStateUpdated()
    }
}