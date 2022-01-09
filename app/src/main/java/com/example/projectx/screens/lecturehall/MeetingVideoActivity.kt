package com.example.projectx.screens.lecturehall

import android.graphics.PorterDuff
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.projectx.APP_ID
import com.example.projectx.R
import com.example.projectx.token
import io.agora.rtc2.Constants
import io.agora.rtc2.IRtcEngineEventHandler
import io.agora.rtc2.RtcEngine
import io.agora.rtc2.video.VideoCanvas



class MeetingVideoActivity : AppCompatActivity() {
    private var mRtcEngine: RtcEngine?= null
    private var channelName: String? = null
    private var userRole = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meeting_video)

        channelName = intent.getStringExtra("ChannelName")
        userRole = intent.getIntExtra("UserRole", - 1)

        initAgoraEngineAndJoinChannel()
    }

    override fun onDestroy() {
        super.onDestroy()
        mRtcEngine!!.leaveChannel()
    }

    fun initAgoraEngineAndJoinChannel(){
        initializeAgoraEngine()

        mRtcEngine!!.setChannelProfile(Constants.CHANNEL_PROFILE_LIVE_BROADCASTING)
        mRtcEngine!!.setClientRole(userRole)

        mRtcEngine!!.enableVideo()
        mRtcEngine!!.startPreview()
        if(userRole == 1)
            setupLocalVideo()
        else{
            val localVideoCanvas = findViewById<View>(R.id.local_video_view_container)
            localVideoCanvas.isVisible = false
        }

        joinChannel()
    }

    private val mRtcEventHandler: IRtcEngineEventHandler = object : IRtcEngineEventHandler(){
        override fun onUserJoined(uid: Int, elapsed: Int) {
            runOnUiThread{ setupRemoteVideo(uid)}
        }

        override fun onUserOffline(uid: Int, reason: Int) {
            runOnUiThread { onRemoteUserLeft() }
        }

        override fun onJoinChannelSuccess(channel: String?, uid: Int, elapsed: Int) {
            runOnUiThread { println("Join channel success : $uid") }
        }
    }

    private fun initializeAgoraEngine(){
        try{
            mRtcEngine = RtcEngine.create(baseContext, APP_ID, mRtcEventHandler)
        } catch (e : Exception){
            println("Exception : ${e.message}")
        }
    }

    private fun setupLocalVideo(){
        val container = findViewById<View>(R.id.local_video_view_container) as FrameLayout
        val surfaceView = RtcEngine.CreateRendererView(baseContext)
        surfaceView.setZOrderMediaOverlay(true)
        container.addView(surfaceView)
        mRtcEngine!!.setupLocalVideo(VideoCanvas(surfaceView, VideoCanvas.RENDER_MODE_FIT, 0))
    }

    private fun joinChannel(){
        mRtcEngine!!.joinChannel(token, channelName, null, 0)
    }

    private fun setupRemoteVideo(uid : Int){
        val container = findViewById<View>(R.id.remote_video_view_container) as FrameLayout

        if(container.childCount >= 1){
            return
        }

        val surfaceView = RtcEngine.CreateRendererView(baseContext)
        container.addView(surfaceView)
        mRtcEngine!!.setupRemoteVideo(VideoCanvas(surfaceView, VideoCanvas.RENDER_MODE_FIT))
        surfaceView.tag = uid
    }

    private fun onRemoteUserLeft(){
        val container = findViewById<View>(R.id.remote_video_view_container) as FrameLayout
        container.removeAllViews()
    }

    fun onLocalAudioMuteClicked(view: View){
        val iv = view as ImageView
        if(iv.isSelected){
            iv.isSelected = false
            iv.clearColorFilter()
        }else{
            iv.isSelected = true
            iv.setColorFilter(resources.getColor(R.color.mainColor), PorterDuff.Mode.MULTIPLY)
        }

        mRtcEngine!!.muteLocalAudioStream(iv.isSelected)
    }

    fun onSwitchCameraClicked(view: View){
        mRtcEngine!!.switchCamera()
    }

    fun onEndCallClicked(view: View){
        finish()
    }
}