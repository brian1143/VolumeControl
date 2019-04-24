package app.phhuang.volumecontrol

import android.content.Context
import android.media.AudioManager
import android.os.Handler
import android.provider.Settings
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import android.util.Log

class VolumeUpTileService : TileService(), VolumeControlTileService {
    private var settingsContentObserver: SettingsContentObserver? = null

    override fun updateTile() {
        val tile = qsTile
        tile?.let {
            it.state = if (Utils.isMax(this)) Tile.STATE_INACTIVE else Tile.STATE_ACTIVE
            it.label = Utils.getVolumeLabel(this) + "(+1)"
            it.updateTile()
        }
    }

    override fun onTileAdded() {
        Log.i("debug", "on up tile added")
    }

    override fun onTileRemoved() {
        Log.i("debug", "on up tile removed")
    }

    override fun onStartListening() {
        Log.i("debug", "on up tile start listening")

        settingsContentObserver = SettingsContentObserver(Handler(), this)
        settingsContentObserver?.let {
            applicationContext.contentResolver.registerContentObserver(Settings.System.CONTENT_URI, true, it)
        }

        updateTile()
    }

    override fun onStopListening() {
        Log.i("debug", "on up tile stop listening")

        settingsContentObserver?.let {
            applicationContext.contentResolver.unregisterContentObserver(it)
        }
    }

    override fun onClick() {
        Log.i("debug", "on up click")

        val audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_RAISE, 0)
    }
}