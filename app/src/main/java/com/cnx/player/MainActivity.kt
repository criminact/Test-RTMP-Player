package com.cnx.player

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.cnx.player.databinding.ActivityMainBinding
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ext.rtmp.RtmpDataSourceFactory
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.PlayerControlView
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.util.Util


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    companion object {
        const val CONNECTION_URL = "rtmp://192.168.0.102:1935/live/stream"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val player by lazy {
            ExoPlayerFactory.newSimpleInstance(this, DefaultTrackSelector())
        }

        val playerView: PlayerView = binding.playerView.also {
            it.player = player
        }

        val playerControlView: PlayerControlView = binding.playerControlView.also{
            it.player = player
        }

        val defaultDataSourceFactory = DefaultExtractorsFactory()
        val uriOfContentUrl = Uri.parse(CONNECTION_URL)
        val mediaSource = ExtractorMediaSource(
            uriOfContentUrl,
            RtmpDataSourceFactory(),
            defaultDataSourceFactory,
            null,
            null
        )

        player?.let{
         it.prepare(mediaSource)
         it.playWhenReady = true
        }

    }

}