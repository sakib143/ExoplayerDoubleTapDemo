package com.example.exoplayertest

import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Bundle
import android.view.View
import com.example.exoplayertest.exoplayer_utils.BaseVideoActivity
import com.example.exoplayertest.exoplayer_utils.DataAndUtils
import com.example.exoplayertest.exoplayer_utils.YouTubeOverlay
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.exo_playback_control_view_yt.*

class MainActivity : BaseVideoActivity() {

    private var isVideoFullscreen = false
    private var currentVideoId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.videoPlayer = previewPlayerView
        initDoubleTapPlayerView()
        startNextVideo()

        fullscreen_button.setOnClickListener {
            toggleFullscreen()
        }

//        fullscreen_button.performClick()

    }

    private fun initDoubleTapPlayerView() {
        ytOverlay
            .performListener(object : YouTubeOverlay.PerformListener {
                override fun onAnimationStart() {
                    previewPlayerView.useController = false
                    ytOverlay.visibility = View.VISIBLE
                }
                override fun onAnimationEnd() {
                    ytOverlay.visibility = View.GONE
                    previewPlayerView.useController = true
                }
            })

        previewPlayerView.doubleTapDelay = 800
    }

    private fun startNextVideo() {
        releasePlayer()
        initializePlayer()
        ytOverlay.player(player!!)

        currentVideoId = (currentVideoId + 1).rem(DataAndUtils.videoList.size)
        buildMediaSource(Uri.parse(DataAndUtils.videoList[currentVideoId]))
    }

    private fun toggleFullscreen() {
        if (isVideoFullscreen) {
            setFullscreen(false)
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE;
            if(supportActionBar != null){
                supportActionBar?.show();
            }
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
            isVideoFullscreen = false
        } else {
            setFullscreen(true)
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_FULLSCREEN
                    and View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    and View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)

            if(supportActionBar != null){
                supportActionBar?.hide();
            }
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
            isVideoFullscreen = true
        }
    }

    override fun onBackPressed() {
        if (isVideoFullscreen) {
            toggleFullscreen()
            return
        }
        super.onBackPressed()
    }
}