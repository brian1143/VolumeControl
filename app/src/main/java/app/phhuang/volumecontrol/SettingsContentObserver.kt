package app.phhuang.volumecontrol

import android.database.ContentObserver
import android.os.Handler

class SettingsContentObserver(handler: Handler, private val tileService: VolumeControlTileService) : ContentObserver(handler) {
    override fun onChange(selfChange: Boolean) {
        super.onChange(selfChange)
        tileService.updateTile()
    }
}