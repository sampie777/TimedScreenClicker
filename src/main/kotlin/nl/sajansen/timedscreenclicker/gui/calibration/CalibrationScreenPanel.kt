package nl.sajansen.timedscreenclicker.gui.calibration


import nl.sajansen.timedscreenclicker.utils.gui.getNumericFontHeight
import nl.sajansen.timedscreenclicker.utils.gui.setDefaultRenderingHints
import org.slf4j.LoggerFactory
import java.awt.*
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.awt.image.BufferedImage
import javax.swing.JPanel

class CalibrationScreenPanel(
    private val onSubmitCallback: (selectedPoint: Point?) -> Unit,
    private val screenshot: BufferedImage? = null
) : JPanel() {
    private val logger = LoggerFactory.getLogger(CalibrationScreenPanel::class.java.name)

    private val selectedPointOuterRadius = 60
    private val selectedPointInnerRadius = 4
    private var selectedPoint: Point? = null

    init {
        createGui()

        addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent) {
                onClick(e)
            }
        })

        addKeyListener(object : KeyListener {
            override fun keyTyped(e: KeyEvent) {
            }

            override fun keyPressed(e: KeyEvent) {
            }

            override fun keyReleased(e: KeyEvent) {
                logger.info("[KeyReleased] $e")

                if (e.keyCode == KeyEvent.VK_ENTER) {
                    onSubmit()
                }
            }

        })
    }

    private fun createGui() {
        background = Color.DARK_GRAY
        isFocusable = true
        requestFocusInWindow()
    }

    private fun onClick(e: MouseEvent) {
        selectedPoint = e.point
        logger.info("Set selected point to: $selectedPoint")

        repaint()
    }

    private fun onSubmit() {
        logger.info("Submitting changes")
        onSubmitCallback(selectedPoint)
    }

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        val g2 = g as Graphics2D
        setDefaultRenderingHints(g2)

        if (screenshot == null) {
            g2.color = Color.GRAY
            g2.font = Font("Dialog", Font.PLAIN, 64)

            val text = "SCREENSHOT UNAVAILABLE"
            val fontMetrics: FontMetrics = g2.fontMetrics
            val fontHeight = getNumericFontHeight(fontMetrics)
            val fontWidth = fontMetrics.stringWidth(text)
            g2.drawString(text, ((width - fontWidth).toDouble() / 2).toInt(), ((height + fontHeight) / 2).toInt())
        } else {
            g2.drawImage(screenshot, 0, 0, null)
        }

        if (selectedPoint == null) {
            return
        }

        g2.stroke = BasicStroke(3F)
        g2.color = Color.RED
        g2.drawOval(
            selectedPoint!!.x - selectedPointOuterRadius,
            selectedPoint!!.y - selectedPointOuterRadius,
            2 * selectedPointOuterRadius,
            2 * selectedPointOuterRadius
        )
        g2.fillOval(
            selectedPoint!!.x - selectedPointInnerRadius,
            selectedPoint!!.y - selectedPointInnerRadius,
            2 * selectedPointInnerRadius,
            2 * selectedPointInnerRadius
        )
    }
}