/**
 *  Motion security sensor
 *
 *  Copyright 2018 Mathieu Girard
 *
 */
metadata {
	definition (name: "Motion security sensor", namespace: "magimat", author: "Mathieu Girard") {
		capability "Motion Sensor"
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
	// TODO: handle 'motion' attribute

}


def setStatus(val) {
	sendEvent(name: "motion", value: val)
}