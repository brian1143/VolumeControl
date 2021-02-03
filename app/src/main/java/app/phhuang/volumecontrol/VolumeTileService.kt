package app.phhuang.volumecontrol

import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.os.Handler
import android.provider.Settings
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import android.util.Log

class VolumeTileService : TileService(), VolumeControlTileService {
    private var settingsContentObserver: SettingsContentObserver? = null

    override fun updateTile() {
        val tile = qsTile
        tile?.let {
            it.state = if (Utils.isMute(this)) Tile.STATE_INACTIVE else Tile.STATE_ACTIVE
            it.label = Utils.getVolumeLabel(this)
            it.updateTile()
        }
    }

    override fun onTileAdded() {
        Log.i("debug", "on tile added")
    }

    override fun onTileRemoved() {
        Log.i("debug", "on tile removed")
    }

    override fun onStartListening() {
        Log.i("debug", "on tile start listening")

        settingsContentObserver = SettingsContentObserver(Handler(), this)
        settingsContentObserver?.let {
            applicationContext.contentResolver.registerContentObserver(Settings.System.CONTENT_URI, true, it)
        }

        updateTile()
    }

    override fun onStopListening() {
        Log.i("debug", "on tile stop listening")

        settingsContentObserver?.let {
            applicationContext.contentResolver.unregisterContentObserver(it)
        }
    }

    override fun onClick() {
        Log.i("debug", "on click")

        sendBroadcast(Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS))

        Handler().postDelayed({
            val audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
            audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_SAME, AudioManager.FLAG_SHOW_UI)
        }, 400)
    }
}