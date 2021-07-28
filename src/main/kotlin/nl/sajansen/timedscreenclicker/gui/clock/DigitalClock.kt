package nl.sajansen.timedscreenclicker.gui.clock


import org.slf4j.LoggerFactory
import java.awt.Font
import java.text.SimpleDateFormat
import java.util.*
import javax.swing.BoxLayout
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.Timer

class DigitalClock : JPanel() {
    private val logger = LoggerFactory.getLogger(DigitalClock::class.java.name)

    private val timeLabel = JLabel()
    private val timer: Timer

    init {
        createGui()

        timer = Timer(1000 / 10) {
            step()
        }
        timer.start()
    }

    private fun createGui() {
        layout = BoxLayout(this, BoxLayout.LINE_AXIS)
        timeLabel.font = Font("Dialog", Font.PLAIN, 52)

        add(timeLabel)
    }

    private fun step() {
        timeLabel.text = SimpleDateFormat("HH:mm:ss").format(Date())
    }
}