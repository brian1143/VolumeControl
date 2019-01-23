package app.phhuang.volumecontrol

import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.os.Handler
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import android.util.Log

class VolumeTileService : TileService() {

    override fun onTileAdded() {
        Log.i("debug", "on tile added")
    }

    override fun onTileRemoved() {
        Log.i("debug", "on tile removed")
    }

    override fun onStartListening() {
        Log.i("debug", "on tile start listening")
        val audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        val label = "Volume: ${audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)}/${audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)}"

        val tile = qsTile
        tile.state = Tile.STATE_INACTIVE
        tile.label = label
        tile.updateTile()
    }

    override fun onStopListening() {
        Log.i("debug", "on tile stop listening")
    }

    override fun onClick() {
        Log.i("debug", "on click")

        sendBroadcast(Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS))

        val audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_SAME, AudioManager.FLAG_SHOW_UI)
    }
}