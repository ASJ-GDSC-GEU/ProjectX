package com.example.projectx.screens

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.projectx.R
import com.example.projectx.token
import io.agora.agorauikit_android.AgoraConnectionData
import io.agora.agorauikit_android.AgoraVideoViewer
import io.agora.agorauikit_android.requestPermissions
import io.agora.rtc2.Constants

class MeetingsActivity : AppCompatActivity() {

    // Fill the App ID of your project generated on Agora Console.
    private val appId = "4b6b5bbe77884cb8ae65c6dabcf44d5f"

    // Fill the temp token generated on Agora Console.

    // Fill the channel name.
    private val channelName = "project"

    private var agView: AgoraVideoViewer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meetings)
        initializeAndJoinChannel()
    }


    private fun initializeAndJoinChannel(){
        // Create AgoraVideoViewer instance
        try {
            agView = AgoraVideoViewer(
                this, AgoraConnectionData(appId),
            )
        } catch (e: Exception) {
            print("Could not initialize AgoraVideoViewer. Check your App ID is valid.")
            print(e.message)
            return
        }
        // Fill the parent ViewGroup (MainActivity)
        this.addContentView(
            agView,
            FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
            )
        )

        // Check permission and join channel
        if (AgoraVideoViewer.requestPermissions(this)) {
            agView!!.join(channelName, token = token, role = Constants.CHANNEL_PROFILE_LIVE_BROADCASTING)
        }

        else {
            val joinButton = Button(this)
            joinButton.text = "Allow Camera and Microphone, then click here"
            joinButton.setOnClickListener(View.OnClickListener {
                // When the button is clicked, check permissions again and join channel
                // if permissions are granted.
                if (AgoraVideoViewer.requestPermissions(this)) {
                    (joinButton.parent as ViewGroup).removeView(joinButton)
                    agView!!.join(channelName, token = token, role = Constants.CHANNEL_PROFILE_LIVE_BROADCASTING)
                }
            })
            joinButton.setBackgroundColor(Color.GREEN)
            joinButton.setTextColor(Color.RED)
            this.addContentView(joinButton, FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, 300))
        }
    }
}