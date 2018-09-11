import groovy.json.JsonSlurper

preferences {
    section("Nanoleaf"){
        input "nanoleaf_ip", "text", title: "nanoleaf ip", required: true
        input "nanoleaf_port", "text", title: "nanoleaf port", required: true
        input "nanoleaf_token", "text", title: "api token", required: true
    }    
}


metadata {
	definition (name: "Nanoleaf", namespace: "magimat", author: "Mathieu Girard") {
		capability "Light"
		capability "Switch Level"
		capability "Switch"
        
	}

	simulator {}
    
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


def off() {
	sendEvent(name: "switch", value: "off", isStateChange: true)
	return createPutRequest("state", "{\"on\" : false}")
} 

def on() {
	sendEvent(name: "switch", value: "on", isStateChange: true)
	return createPutRequest("state", "{\"on\" : true}")
}

def setLevel(Integer value) {
    sendEvent(name: "level", value: value, isStateChange: true)
	return createPutRequest("state", "{\"brightness\" : ${value}}")
}

private createPutRequest(String url, String body) {

	log.debug("/api/beta/${nanoleaf_token}/${url}")
    log.debug("body : ${body}")
    
    def result = new physicalgraph.device.HubAction(
            method: "PUT",
            path: "/api/beta/${nanoleaf_token}/${url}",
            body: body,
            headers: [
              	HOST: "${nanoleaf_ip}:${nanoleaf_port}"
            ]
        )
	result
}

private createGetRequest(String url) {

	log.debug("/api/beta/${apiKey}/${url}")
    
    def result = new physicalgraph.device.HubAction(
            method: "GET",
            path: "/api/beta/${nanoleaf_token}/${url}",
            headers: [
              	HOST: "${nanoleaf_ip}:${nanoleaf_port}"
            ]
        )
    result
}

