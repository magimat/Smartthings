/**
 *  Security contact sensor
 *
 *  Copyright 2018 Mathieu Girard
 *
 */
metadata {
	definition (name: "Security contact sensor", namespace: "magimat", author: "Mathieu Girard") {
		capability "Contact Sensor"
        command "setStatus"
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
	// TODO: handle 'contact' attribute

}

def setStatus(val) {
	sendEvent(name: "contact", value: val)
}
