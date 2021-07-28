package nl.sajansen.timedscreenclicker.gui.mainFrame

import nl.sajansen.timedscreenclicker.events.EventsDispatcher
import nl.sajansen.timedscreenclicker.events.GuiEventListener
import nl.sajansen.timedscreenclicker.gui.actions.ActionsPanel
import nl.sajansen.timedscreenclicker.gui.clock.ClockPanel
import org.slf4j.LoggerFactory
import java.awt.GridLayout
import javax.swing.JPanel


class MainFramePanel : JPanel(), GuiEventListener {
    private val logger = LoggerFactory.getLogger(MainFramePanel::class.java.name)

    init {
        EventsDispatcher.register(this)

        createGui()

        refreshNotifications()
    }

    private fun createGui() {
        border = null
        layout = GridLayout(1, 2)

        add(ClockPanel())
        add(ActionsPanel())
    }

    override fun removeNotify() {
        super.removeNotify()
        EventsDispatcher.unregister(this)
    }

}