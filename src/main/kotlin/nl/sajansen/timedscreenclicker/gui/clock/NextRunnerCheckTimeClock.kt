package nl.sajansen.timedscreenclicker.gui.clock


import nl.sajansen.timedscreenclicker.objects.Runner
import org.slf4j.LoggerFactory
import java.awt.Color
import java.awt.Font
import java.text.SimpleDateFormat
import javax.swing.*

class NextRunnerCheckTimeClock : JPanel() {
    private val logger = LoggerFactory.getLogger(NextRunnerCheckTimeClock::class.java.name)

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
        toolTipText = "Next runner check time"

        timeLabel.font = Font("Dialog", Font.PLAIN, 20)
        timeLabel.foreground = Color.GRAY

        add(Box.createHorizontalGlue())
        add(timeLabel)
        add(Box.createHorizontalGlue())
    }

    private fun step() {
        if (Runner.nextCheckTime == null) {
            timeLabel.text = "unknown"
            return
        }
        timeLabel.text = SimpleDateFormat("HH:mm:ss").format(Runner.nextCheckTime)
    }
}