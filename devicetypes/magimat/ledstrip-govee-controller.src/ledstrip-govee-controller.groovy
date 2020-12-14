preferences {
	section("Internal Access"){
		input "api key govee", "text", title: "api key", required: false
	}
}




metadata {
	definition (name: "Ledstrip Govee controller", namespace: "magimat", author: "Mathieu Girard") {
	        capability "Actuator"
			capability "Switch"
			capability "Sensor"
            capability "Color Control"
            capability "Switch Level"
	}

	// simulator metadata
	simulator {
	}

	// UI tile definitions
	tiles(scale: 2) {
		standardTile("button", "device.switch", width: 3, height: 2, canChangeIcon: true) {
			    state "off", label: 'Off', action: "switch.on", icon: "st.switches.switch.off", backgroundColor: "#ffffff", nextState: "on"
				state "on", label: 'On', action: "switch.off", icon: "st.switches.switch.on", backgroundColor: "#79b821", nextState: "off"
		}
        
		controlTile("levelSliderControl", "device.level", "slider", height: 2, width: 3, range:"(0..100)") {
		    state "level", action:"switch level.setLevel"
		}        
        
		controlTile("rgbSelector", "device.color", "color", height: 6, width: 6,
            inactiveLabel: false) {
		    state "color", action: "color control.setColor"
		}        
        
        
		main "button"
		details (["button", "levelSliderControl", "rgbSelector"])        

	}
}

def parse(String description) {
	log.debug(description)
}

def on() {

    def result = new physicalgraph.device.HubAction(
        method: "GET",
        path: "/leds/on",
        headers: [
            HOST: "${matleds_ip}:${matleds_port}"
        ]
    )
    sendHubCommand(result)
    sendEvent(name: "switch", value: "on") 
    log.debug "Executing ON" 
    log.debug result
}

def off() {
    def result = new physicalgraph.device.HubAction(
        method: "GET",
        path: "/leds/off",
        headers: [
            HOST: "${matleds_ip}:${matleds_port}"
        ]
    )
    sendHubCommand(result)
    sendEvent(name: "switch", value: "off")
    log.debug "Executing OFF" 
    log.debug result
}

def setLevel(Number level) {
    def result = new physicalgraph.device.HubAction(
        method: "GET",
        path: "/leds/dim/${level}",
        headers: [
            HOST: "${matleds_ip}:${matleds_port}"
        ]
    )
    sendHubCommand(result)
    sendEvent(name: "switch", value: "on")
    sendEvent(name: "level", value: ${level}) 
    log.debug "Executing setLevel(${level})" 
    log.debug result
}

def setColor(Map props) {
log.debug props
log.debug props.hex
    def result = new physicalgraph.device.HubAction(
        method: "GET",
        path: "/leds/color/${props.hex.drop(1)}",
        headers: [
            HOST: "${matleds_ip}:${matleds_port}"
        ]
    )
    sendHubCommand(result)
}