package nl.sajansen.timedscreenclicker.events


interface DataEventListener {
    fun onActionsUpdated() {}
    fun onRunningStateUpdated() {}
}