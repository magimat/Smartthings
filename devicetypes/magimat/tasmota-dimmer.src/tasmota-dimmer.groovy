preferences {
	section("Internal Access"){
		input "switchip", "text", title: "IP of tasmota wifi switch", required: false
	}
}



metadata {
	definition (name: "Tasmota dimmer", namespace: "magimat", author: "Mathieu Girard") {
     		        capability "Actuator"
			capability "Switch"
			capability "Switch level"
			capability "Sensor"
            
            command "setOn"
            command "setOff"
	}

	// simulator metadata
	simulator {
	}

	// UI tile definitions
	tiles {
		standardTile("button", "device.switch", width: 3, height: 3, canChangeIcon: true) {
			    state "off", label: 'Off', action: "switch.on", icon: "st.switches.switch.off", backgroundColor: "#ffffff", nextState: "on"
				state "on", label: 'On', action: "switch.off", icon: "st.switches.switch.on", backgroundColor: "#79b821", nextState: "off"
		}
		controlTile("levelSliderControl", "device.level", "slider", height: 1, width: 3, range:"(0..100)") {
		    state "level", action:"switch level.setLevel"
		}        
        
		main "button"
		details (["button", "levelSliderControl"])
	}
}

def parse(String description) {
	log.debug(description)
}

def on() {

    def result = new physicalgraph.device.HubAction(
        method: "GET",
        path: "/cm?cmnd=Power%20ON",
        headers: [
            HOST: "${switchip}:80"
        ]
    )
    sendHubCommand(result)
    sendEvent(name: "switch", value: "on") 
    sendEvent(name: "level", value: 100) 
    log.debug "Executing ON" 
    log.debug result
}

def off() {
    def result = new physicalgraph.device.HubAction(
        method: "GET",
        path: "/cm?cmnd=Power%20OFF",
        headers: [
            HOST: "${switchip}:80"
        ]
    )
    sendHubCommand(result)
    sendEvent(name: "switch", value: "off")
    sendEvent(name: "level", value: 0) 
    log.debug "Executing OFF" 
    log.debug result
}

def setLevel(Number level) {
    def result = new physicalgraph.device.HubAction(
        method: "GET",
        path: "/?m=1&d0=${level}:80",
        headers: [
            HOST: "${switchip}"
        ]
    )
    sendHubCommand(result)
    sendEvent(name: "switch", value: "on")
    sendEvent(name: "level", value: ${level}) 
    log.debug "Executing setLevel(${level})" 
    log.debug result
}

def setOn() {
    sendEvent(name: "switch", value: "on")
}

def setOff() {
    sendEvent(name: "switch", value: "off")
}