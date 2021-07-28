package nl.sajansen.timedscreenclicker.gui.clock


import org.slf4j.LoggerFactory
import java.awt.Color
import javax.swing.BorderFactory
import javax.swing.Box
import javax.swing.BoxLayout
import javax.swing.JPanel
import javax.swing.border.CompoundBorder
import javax.swing.border.EmptyBorder

class ClockPanel : JPanel() {
    private val logger = LoggerFactory.getLogger(ClockPanel::class.java.name)

    init {
        createGui()
    }

    private fun createGui() {
        layout = BoxLayout(this, BoxLayout.PAGE_AXIS)
        border = CompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 0, 1, Color(180, 180, 180)),
            EmptyBorder(10, 15, 10, 15)
        )

        add(Box.createVerticalGlue())
        add(DigitalClock())
        add(Box.createVerticalStrut(20))
        add(NextRunnerCheckTimeClock())
        add(Box.createVerticalGlue())
    }
}