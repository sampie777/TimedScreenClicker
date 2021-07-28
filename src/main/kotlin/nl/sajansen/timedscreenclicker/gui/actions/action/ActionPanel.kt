package nl.sajansen.timedscreenclicker.gui.actions.action


import nl.sajansen.timedscreenclicker.events.EventsDispatcher
import nl.sajansen.timedscreenclicker.objects.Action
import nl.sajansen.timedscreenclicker.objects.State
import org.slf4j.LoggerFactory
import java.awt.BorderLayout
import java.awt.Color
import java.awt.Font
import javax.swing.BorderFactory
import javax.swing.JButton
import javax.swing.JPanel
import javax.swing.border.CompoundBorder
import javax.swing.border.EmptyBorder

class ActionPanel(private val action: Action) : JPanel() {
    private val logger = LoggerFactory.getLogger(ActionPanel::class.java.name)

    init {
        createGui()
    }

    private fun createGui() {
        layout = BorderLayout(10, 10)

        border = CompoundBorder(
            CompoundBorder(
                EmptyBorder(5, 0, 5, 0),
                BorderFactory.createLineBorder(Color(180, 180, 180))
            ),
            EmptyBorder(10, 15, 10, 15)
        )

        val deleteButton = JButton("X")
        deleteButton.font = Font("Dialog", Font.BOLD, 16)
        deleteButton.addActionListener { onDeleteClick() }

        add(deleteButton, BorderLayout.LINE_END)
        add(TimeEditPanel(action), BorderLayout.LINE_START)
    }

    private fun onDeleteClick() {
        State.actions.remove(action)
        EventsDispatcher.onActionsUpdated()
    }
}