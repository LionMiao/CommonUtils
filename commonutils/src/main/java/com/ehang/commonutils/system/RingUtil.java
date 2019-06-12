package com.ehang.commonutils.system;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;

import com.ehang.commonutils.ui.TomApplication;

import java.io.IOException;
/**
 * 播放系统铃声
 * Created by KwokSiuWang on 2018/3/26.
 */

public class RingUtil {
    private MediaPlayer mMediaPlayer;

    public RingUtil(Context context) {
        try {
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setDataSource(TomApplication.getContext(), RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE));
            mMediaPlayer.setAudioAttributes(new AudioAttributes.Builder().setLegacyStreamType(AudioManager.STREAM_RING).build());
            mMediaPlayer.setLooping(true); //循环播放
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public RingUtil useSystemRing() {
        // 使用来电铃声的铃声路径
        return setRingParam(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE));
    }

    /**
     * @param uri 铃声的uri
     */
    public RingUtil setRingParam(Uri uri) {
        // 如果为空，才构造，不为空，说明之前有构造过
        try {
            mMediaPlayer.setDataSource(TomApplication.getContext(), uri);
            mMediaPlayer.setAudioAttributes(new AudioAttributes.Builder().setLegacyStreamType(AudioManager.STREAM_RING).build());
            mMediaPlayer.setLooping(true); //循环播放
            mMediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public void ring() throws IOException {
        // 如果为空，才构造，不为空，说明之前有构造过
        if (mMediaPlayer != null){
            mMediaPlayer.setDataSource(TomApplication.getContext(), RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE));
            mMediaPlayer.prepare();
            mMediaPlayer.start();
        }
    }

    public void stop() {
        if (null != mMediaPlayer) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    public void release() {

    }
}
