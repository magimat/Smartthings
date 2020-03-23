/**
 *  Security siren
 *
 *  Copyright 2018 Mathieu Girard
 *
 */
metadata {
	definition (name: "Security siren", namespace: "magimat", author: "Mathieu Girard") {
		capability "Alarm"
	}


	simulator {
		// TODO: define status and reply messages here
	}

	tiles {
		// TODO: define your main and details tiles here
	}
}

// parse events into attributes
def parse(String description) {
	log.debug "Parsing '${description}'"
	// TODO: handle 'alarm' attribute

}

// handle commands
def off() {
	log.debug "Executing 'off'"
	def result = new physicalgraph.device.HubAction(
        method: "GET",
        path: "/alarmOff",
        headers: [
            HOST: "192.168.11.12:80"
        ]
    )
    sendHubCommand(result)
}

def strobe() {
	log.debug "Executing 'strobe'"
	// TODO: handle 'strobe' command
}

def siren() {
	log.debug "Executing 'siren'"
	def result = new physicalgraph.device.HubAction(
        method: "GET",
        path: "/alarmOn",
        headers: [
            HOST: "192.168.11.12:80"
        ]
    )
    sendHubCommand(result)
}

def both() {
	log.debug "Executing 'both'"
	siren();
}