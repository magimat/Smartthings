preferences {
	section("Internal Access"){
		input "govee_key", "text", title: "api key", required: false
        input "govee_id", "text", title: "device id", required: false
        input "govee_type", "text", title: "device type", required: false
	}
}




metadata {
	definition (name: "Ledstrip Govee controller", namespace: "magimat", author: "Mathieu Girard") {
	        capability "Actuator"
			capability "Switch"

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

        
		main "button"
		details (["button"])        

	}
}

def parse(String description) {
	log.debug(description)
}

def on() {

	def params = [:]

	params.put("uri", "https://developer-api.govee.com:443")
	params.put("path", "/v1/devices/control")
    params.put("contentType", "application/json")

	def headers = [:] 
    headers.put("Govee-API-Key", "$govee_key")
	params.put("headers", headers)

    def body="""{"device": "$govee_id", "model": "$govee_type", "cmd": { "name": "turn","value": "on"}}"""   
	params.put("body", body)


    try {
        httpPutJson(params) { resp ->
            log.debug resp
        }
    } catch (e) {
        log.debug e
    }

    sendEvent(name: "switch", value: "on") 
    log.debug "Executing ON" 
    
}

def off() {

	def params = [:]

	params.put("uri", "https://developer-api.govee.com:443")
	params.put("path", "/v1/devices/control")
    params.put("contentType", "application/json")

	def headers = [:] 
    headers.put("Govee-API-Key", "$govee_key")
	params.put("headers", headers)

    def body="""{"device": "$govee_id", "model": "$govee_type", "cmd": { "name": "turn","value": "off"}}"""   
	params.put("body", body)


    try {
        httpPutJson(params) { resp ->
            log.debug resp
        }
    } catch (e) {
        log.debug e
    }

    sendEvent(name: "switch", value: "off") 
    log.debug "Executing OFF" 
    
}

