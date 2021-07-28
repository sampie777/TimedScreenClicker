package nl.sajansen.timedscreenclicker.gui.actions.action


import nl.sajansen.timedscreenclicker.ApplicationRuntimeSettings
import nl.sajansen.timedscreenclicker.objects.Action
import org.slf4j.LoggerFactory
import java.awt.Color
import java.awt.Dimension
import java.awt.Font
import java.text.SimpleDateFormat
import java.time.ZoneId
import javax.swing.*
import javax.swing.border.EmptyBorder

class TimeEditPanel(private val action: Action) : JPanel() {
    private val logger = LoggerFactory.getLogger(TimeEditPanel::class.java.name)

    private val hourInput = JSpinner()
    private val minuteInput = JSpinner()
    private val secondInput = JSpinner()

    init {
        createGui()
    }

    private fun createGui() {
        layout = BoxLayout(this, BoxLayout.LINE_AXIS)

        val time = action.timestamp.toInstant().atZone(ZoneId.systemDefault())

        val size = Dimension(60, 20)
        val border = BorderFactory.createLineBorder(Color(168, 168, 168))
        val font = Font("Dialog", Font.PLAIN, 14)

        hourInput.model = SpinnerNumberModel(time.hour, 0, 23, 1)
        hourInput.toolTipText = "hours"
        hourInput.preferredSize = size
        hourInput.border = border
        hourInput.font = font
        hourInput.addChangeListener { onSpinnerChange() }

        minuteInput.model = SpinnerNumberModel(time.minute, 0, 59, 1)
        minuteInput.toolTipText = "minutes"
        minuteInput.preferredSize = size
        minuteInput.border = border
        minuteInput.font = font
        minuteInput.addChangeListener { onSpinnerChange() }

        secondInput.model = SpinnerNumberModel(time.second, 0, 59, 1)
        secondInput.toolTipText = "seconds"
        secondInput.preferredSize = size
        secondInput.border = border
        secondInput.font = font
        secondInput.addChangeListener { onSpinnerChange() }

        add(hourInput)
        add(createSeparatorLabel())
        add(minuteInput)

        if (ApplicationRuntimeSettings.useSeconds) {
            add(createSeparatorLabel())
            add(secondInput)
        }
    }

    private fun createSeparatorLabel(): JLabel {
        val separatorLabel = JLabel(":")
        separatorLabel.border = EmptyBorder(0, 5, 0, 5)
        return separatorLabel
    }

    private fun onSpinnerChange() {
        val hours = hourInput.value as Int
        val minutes = minuteInput.value as Int
        val seconds = secondInput.value as Int

        action.timestamp = SimpleDateFormat("HH:mm:ss").parse("$hours:$minutes:$seconds")
    }
}