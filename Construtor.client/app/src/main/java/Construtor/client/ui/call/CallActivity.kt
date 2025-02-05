package com.Construtor.client.ui.call

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.Construtor.client.databinding.ActivityCallBinding
import org.webrtc.*

class CallActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCallBinding
    private lateinit var peerConnectionFactory: PeerConnectionFactory
    private lateinit var localVideoTrack: VideoTrack
    private lateinit var remoteVideoTrack: VideoTrack

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCallBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeWebRTC()

        binding.btnEncerrarChamada.setOnClickListener {
            finish()
        }
    }

    private fun initializeWebRTC() {
        val options = PeerConnectionFactory.InitializationOptions.builder(this).createInitializationOptions()
        PeerConnectionFactory.initialize(options)

        peerConnectionFactory = PeerConnectionFactory.builder().createPeerConnectionFactory()

        val videoCapturer = createCameraCapturer()
        val videoSource = peerConnectionFactory.createVideoSource(false)
        localVideoTrack = peerConnectionFactory.createVideoTrack("localVideo", videoSource)

        binding.localView.init(EglBase.create().eglBaseContext, null)
        localVideoTrack.addSink(binding.localView)

        // Criando conexão remota (desativada por enquanto)
        val remoteVideoSource = peerConnectionFactory.createVideoSource(false)
        remoteVideoTrack = peerConnectionFactory.createVideoTrack("remoteVideo", remoteVideoSource)

        binding.remoteView.init(EglBase.create().eglBaseContext, null)
        remoteVideoTrack.addSink(binding.remoteView)
    }

    private fun createCameraCapturer(): CameraVideoCapturer? {
        val enumerator = Camera2Enumerator(this)
        val deviceNames = enumerator.deviceNames
        for (deviceName in deviceNames) {
            if (enumerator.isFrontFacing(deviceName)) {
                return enumerator.createCapturer(deviceName, null)
            }
        }
        return null
    }
}
