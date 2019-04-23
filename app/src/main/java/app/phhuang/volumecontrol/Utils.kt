package app.phhuang.volumecontrol

import android.content.Context
import android.media.AudioManager

class Utils {
    companion object {
        fun getVolumeLabel(context: Context): String {
            val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
            val currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
            return context.getString(R.string.volume_control_label, currentVolume, audioManager.getStreamMaxVolume(
                AudioManager.STREAM_MUSIC))
        }

        fun isMute(context: Context): Boolean {
            val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
            return audioManager.isStreamMute(AudioManager.STREAM_MUSIC) || audioManager.getStreamVolume(AudioManager.STREAM_MUSIC) == 0
        }

        fun isMax(context: Context): Boolean {
            val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
            return audioManager.getStreamVolume(AudioManager.STREAM_MUSIC) == audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
        }
    }
}