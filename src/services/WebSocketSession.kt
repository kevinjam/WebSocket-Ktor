package services

import io.ktor.http.cio.websocket.CloseReason
import io.ktor.http.cio.websocket.Frame
import kotlinx.coroutines.experimental.channels.ReceiveChannel
import kotlinx.coroutines.experimental.channels.SendChannel

interface WebSocketSession {
    val incoming:ReceiveChannel<Frame> //Incoming frames channel
    val outgoing:SendChannel<Frame> //Pitgoing frames channel
    fun close(reason:CloseReason)
}