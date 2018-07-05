package model

import io.ktor.websocket.CloseReason
import kotlinx.coroutines.experimental.DisposableHandle
import kotlinx.coroutines.experimental.io.ByteBuffer

sealed class Frame {
    var fin: Boolean = false // Is this frame a final frame?
    lateinit var frameType: FrameType // The Type of the frame
    lateinit var buffer: ByteBuffer // Payload
    lateinit var disposableHandle: DisposableHandle

    class Binary : Frame()
    class Text : Frame() {
        fun readText(): String {
        return ""
    }
    }
    class Close : Frame() {
        fun readReason(): CloseReason? {
            return null
        }
    }
    class Ping : Frame()
    class Pong : Frame()
}