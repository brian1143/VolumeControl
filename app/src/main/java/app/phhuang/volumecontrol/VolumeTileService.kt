package app.phhuang.volumecontrol

import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import android.util.Log

class VolumeTileService : TileService() {
    private fun getLabel() : String {
        val audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        val currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
        return resources.getString(R.string.volume_control_label, currentVolume, audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC))
    }

    private fun isMute() : Boolean {
        val audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        return audioManager.isStreamMute(AudioManager.STREAM_MUSIC) || audioManager.getStreamVolume(AudioManager.STREAM_MUSIC) == 0
    }

    override fun onTileAdded() {
        Log.i("debug", "on tile added")
    }

    override fun onTileRemoved() {
        Log.i("debug", "on tile removed")
    }

    override fun onStartListening() {
        Log.i("debug", "on tile start listening")

        val tile = qsTile
        tile?.let {
            it.state = if (isMute()) Tile.STATE_INACTIVE else Tile.STATE_ACTIVE
            it.label = getLabel()
            it.updateTile()
        }
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