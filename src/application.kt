package com.thinkdevs

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import io.ktor.http.*
import com.fasterxml.jackson.databind.*
import io.ktor.jackson.*
import io.ktor.features.*
import io.ktor.http.cio.websocket.CloseReason
import io.ktor.http.cio.websocket.Frame
import io.ktor.http.cio.websocket.close
import io.ktor.http.cio.websocket.readText
import io.ktor.websocket.WebSockets
import io.ktor.websocket.webSocket
import java.time.Duration

fun main(args: Array<String>): Unit = io.ktor.server.netty.DevelopmentEngine.main(args)

fun Application.main() {
	install(DefaultHeaders)
	install(CallLogging)

	/**
	 * Install Websocket
	 */
	install(WebSockets)

	install(WebSockets){
		pingPeriod = Duration.ofSeconds(60)
		timeout = Duration.ofSeconds(15)
		maxFrameSize = Long.MAX_VALUE
		masking = false
	}

	install(ContentNegotiation) {
		jackson {
			enable(SerializationFeature.INDENT_OUTPUT)
		}
	}

	routing {
		//websocket
		webSocket ("/"){
			while (true){
				val frame = incoming.receive()
				when(frame){
					is Frame.Text->{
						val text = frame.readText()
						outgoing.send(Frame.Text("Welcome $text"))
						if (text.equals("bye" , ignoreCase = true)){
							close(CloseReason(CloseReason.Codes.NORMAL, "Client said Bye"))
						}
					}
				}
			}
		}


		get("/json/jackson") {
			call.respond(mapOf("hello" to "world"))
		}
	}
}

fun testConversation(){

}

