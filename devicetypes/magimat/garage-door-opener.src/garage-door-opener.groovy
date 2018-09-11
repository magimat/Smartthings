preferences {
	section("Internal Access"){
		input "insteon_ip", "text", title: "IP of Matsteon service", required: false
		input "insteon_port", "text", title: "Port of Matsteon service", required: false
	}
}

metadata {
	definition (name: "Garage Door Opener", namespace: "magimat", author: "Mathieu Girard") {
		capability "Actuator"
		capability "Door Control"
        capability "Garage Door Control"
		capability "Contact Sensor"
		capability "Refresh"
		capability "Sensor"
		capability "Relay switch"
        
        command "setOn"
        command "setOff"
	}

	simulator {
		
	}

	tiles(scale: 2) {
		standardTile("toggle", "device.door", width: 6, height: 6) {
			state("closed", label:'${name}', action:"door control.open", icon:"st.doors.garage.garage-closed", backgroundColor:"#e51048", nextState:"opening")
			state("open", label:'${name}', action:"door control.close", icon:"st.doors.garage.garage-open", backgroundColor:"#2baa17", nextState:"closing")
			state("opening", label:'${name}', icon:"st.doors.garage.garage-closed", backgroundColor:"#00A0DC")
			state("closing", label:'${name}', icon:"st.doors.garage.garage-open", backgroundColor:"#00A0DC")
			
		}
		standardTile("open", "device.door", inactiveLabel: false, decoration: "flat", width: 2, height: 2) {
			state "default", label:'open', action:"door control.open", icon:"st.doors.garage.garage-opening"
		}
		standardTile("close", "device.door", inactiveLabel: false, decoration: "flat", width: 2, height: 2) {
			state "default", label:'close', action:"door control.close", icon:"st.doors.garage.garage-closing"
		}
        standardTile("refresh", "device.door", inactiveLabel: false, width: 2, height: 2) {
            state "default", label:'Refresh', action:"refresh", icon:"st.secondary.refresh"
        }

		main "toggle"
		details(["toggle", "open", "close", "refresh"])
	}
}


// parse events into attributes
def parse(String description) {
    log.debug("--- handler.parse")
	def msg = parseLanMessage(description)
    
    if (msg.body == "fermé") {
	    log.debug("--- parsed fermé")
    	sendEvent(name: "door", value: "closed")
    	sendEvent(name: "contact", value: "closed")
    }
    else if (msg.body == "ouvert") {
	    log.debug("--- parsed ouvert")
    	sendEvent(name: "door", value: "open")
    	sendEvent(name: "contact", value: "open")
	}
  
}

/*** Capabilities ***/


def refresh() {
	log.trace("--- refresh")
	def result = new physicalgraph.device.HubAction(
        method: "GET",
        path: "/garage/status",
        headers: [
            HOST: "${insteon_ip}:${insteon_port}"
        ]
    )
    sendHubCommand(result)   
    log.debug result
   
}





def open() {
    
    log.debug("door open")
    
    def result = new physicalgraph.device.HubAction(
        method: "GET",
        path: "/garage/bouton",
        headers: [
            HOST: "${insteon_ip}:${insteon_port}"
        ]
    )
    sendHubCommand(result)
    log.debug result

	//sendEvent(name: "door", value: "opening")
    sendEvent(name: "door", value: "open")
    sendEvent(name: "contact", value: "open")
}

def close() {
    log.debug("door close")
    
    def result = new physicalgraph.device.HubAction(
        method: "GET",
        path: "/garage/bouton",
        headers: [
            HOST: "${insteon_ip}:${insteon_port}"
        ]
    )
    sendHubCommand(result)
    log.debug result

    sendEvent(name: "door", value: "closing")

}


// appelé par mqtt lorsque le senseur iolinc est ouvert, donc porte ouverte
def setOn() {
    sendEvent(name: "door", value: "open")
    sendEvent(name: "contact", value: "open")
}

// appelé par mqtt lorsque le senseur iolinc est fermé, donc porte fermée
def setOff() {
    sendEvent(name: "door", value: "closed")
    sendEvent(name: "contact", value: "closed")
}