package nl.sajansen.timedscreenclicker.gui.actions


import nl.sajansen.timedscreenclicker.events.EventsDispatcher
import nl.sajansen.timedscreenclicker.gui.calibration.CalibrationScreen
import nl.sajansen.timedscreenclicker.gui.mainFrame.MainFrame
import nl.sajansen.timedscreenclicker.objects.Action
import nl.sajansen.timedscreenclicker.objects.State
import nl.sajansen.timedscreenclicker.utils.gui.createImageIcon
import nl.sajansen.timedscreenclicker.utils.screenshot
import org.slf4j.LoggerFactory
import java.awt.*
import java.util.*
import javax.swing.BorderFactory
import javax.swing.JButton
import javax.swing.JPanel
import javax.swing.border.CompoundBorder
import javax.swing.border.EmptyBorder

class ActionControlsPanel : JPanel() {
    private val logger = LoggerFactory.getLogger(ActionControlsPanel::class.java.name)

    init {
        createGui()
    }

    private fun createGui() {
        layout = BorderLayout(10, 10)

        border = CompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, Color(180, 180, 180)),
            EmptyBorder(10, 15, 10, 15)
        )

        val clearAllButton = JButton(createImageIcon("/nl/sajansen/timedscreenclicker/icons/outline_layers_clear_black_24dp.png"))
        clearAllButton.addActionListener { onClearAllClick() }

        val addButton = JButton(createImageIcon("/nl/sajansen/timedscreenclicker/icons/outline_add_black_24dp.png"))
        addButton.addActionListener { onAddClick() }

        add(clearAllButton, BorderLayout.LINE_START)
        add(addButton, BorderLayout.CENTER)
        add(PlayPauseButton(), BorderLayout.LINE_END)
    }

    private fun onClearAllClick() {
        State.actions.clear()
        EventsDispatcher.onActionsUpdated()
    }

    private fun onAddClick() {
        MainFrame.getInstance()?.state = Frame.ICONIFIED
        val screenshot = screenshot(delay = 300)
        MainFrame.getInstance()?.state = Frame.NORMAL

        EventQueue.invokeLater {
            CalibrationScreen.createAndShow(
                onSubmitCallback = { onSubmitNew(it) },
                screenshot = screenshot
            )
        }
    }

    private fun onSubmitNew(point: Point?) {
        if (point == null) {
            return
        }

        val action = Action(Date(), point)
        State.actions.add(action)
        EventsDispatcher.onActionsUpdated()
    }
}