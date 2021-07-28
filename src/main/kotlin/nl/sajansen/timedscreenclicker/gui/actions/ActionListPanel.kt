package nl.sajansen.timedscreenclicker.gui.actions


import nl.sajansen.timedscreenclicker.events.DataEventListener
import nl.sajansen.timedscreenclicker.events.EventsDispatcher
import nl.sajansen.timedscreenclicker.gui.actions.action.ActionPanel
import nl.sajansen.timedscreenclicker.objects.State
import org.slf4j.LoggerFactory
import java.awt.BorderLayout
import javax.swing.BoxLayout
import javax.swing.JPanel

class ActionListPanel : JPanel(), DataEventListener {
    private val logger = LoggerFactory.getLogger(ActionListPanel::class.java.name)

    private val mainPanel = JPanel()

    init {
        EventsDispatcher.register(this)

        createGui()

        onActionsUpdated()
    }

    private fun createGui() {
        layout = BorderLayout()
        mainPanel.layout = BoxLayout(mainPanel, BoxLayout.PAGE_AXIS)
        add(mainPanel, BorderLayout.PAGE_START)
    }

    private fun updateList() {
        mainPanel.removeAll()
        State.actions.stream()
            .sorted { a, b -> a.timestamp.compareTo(b.timestamp) }
            .forEach { mainPanel.add(ActionPanel(it)) }
    }

    override fun onActionsUpdated() {
        updateList()

        repaint()
        revalidate()
    }
}