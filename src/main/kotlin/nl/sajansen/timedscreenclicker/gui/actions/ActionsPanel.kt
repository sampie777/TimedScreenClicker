package nl.sajansen.timedscreenclicker.gui.actions


import org.slf4j.LoggerFactory
import java.awt.BorderLayout
import javax.swing.JPanel
import javax.swing.JScrollPane

class ActionsPanel : JPanel() {
    private val logger = LoggerFactory.getLogger(ActionsPanel::class.java.name)

    init {
        createGui()
    }

    private fun createGui() {
        layout = BorderLayout(10, 10)

        val actionListScrollPanel = JScrollPane(ActionListPanel())
        actionListScrollPanel.border = null

        add(actionListScrollPanel, BorderLayout.CENTER)
        add(ActionControlsPanel(), BorderLayout.PAGE_END)
    }
}