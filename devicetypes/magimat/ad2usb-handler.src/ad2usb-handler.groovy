import groovy.json.JsonSlurper;

preferences {
    section() {
	input "alarm_ip", "text", title: "IP of Matalarm service", required: false
	input "alarm_port", "text", title: "Port of Matalarm service", required: false
    }
}

metadata {
    definition (name: "AD2USB handler", namespace: "magimat", author: "Mathieu Girard") {
        capability "Refresh"
        capability "Switch"  // nuit on/off
        capability "Lock"  // full lock / unlock

        command "disarm"
        command "armNuit"
        command "armFull"
    }

    simulator {
        // TODO: define status and reply messages here
    }

    tiles(scale: 2) {
        multiAttributeTile(name: "status", type: "generic", width: 6, height: 2) {
            tileAttribute("device.lock", key: "PRIMARY_CONTROL") {
                attributeState "locked", label: 'Armed', icon: "st.security.alarm.on", backgroundColor: "#ffa81e"
                attributeState "unlocked", label: 'Disarmed', icon: "st.security.alarm.off", backgroundColor: "#79b821", defaultState: true
            }
        }

        standardTile("arm_full", "device.lock", inactiveLabel: false, width: 3, height: 2) {
            state "default", label:'Arm full', action:"armFull", icon:"st.security.alarm.on"
        }
        standardTile("arm_nuit", "device.lock", inactiveLabel: false, width: 3, height: 2) {
            state "default", label:'Arm nuit', action:"armNuit", icon:"st.security.alarm.on"
        }
        standardTile("disarm", "device.lock", inactiveLabel: false, width: 3, height: 2) {
            state "default", label:'Disarm', action:"disarm", icon:"st.Home.home4"
        }
        standardTile("refresh", "device.lock", inactiveLabel: false, width: 3, height: 2) {
            state "default", label:'Refresh', action:"refresh", icon:"st.secondary.refresh"
        }


        main(["status"])
        details(["status", "arm_full", "arm_nuit", "disarm", "refresh"])
    }
}

/*** Handlers ***/

def installed() {
}

def updated() {
    log.trace "--- handler.updated"
}

def uninstalled() {
    log.trace "--- handler.uninstalled"
}

// parse events into attributes
def parse(String description) {
    log.debug("--- handler.parse")
	def msg = parseLanMessage(description)

	log.debug(msg.body)
    
    if(msg.body == "true") {
    	sendEvent(name: "lock", value: "locked")
    	sendEvent(name: "switch", value: "on")
    }
//    	sendEvent(name: "lock", value: "unlocked")
//    	sendEvent(name: "switch", value: "off")

}

/*** Capabilities ***/


def refresh() {

   log.trace("--- refresh")
	def result = new physicalgraph.device.HubAction(
        method: "GET",
        path: "/status",
        headers: [
            HOST: "${alarm_ip}:${alarm_port}"
        ]
    )
    sendHubCommand(result)   
}

def lock() {
	armFull()
}


def unlock() {
	disarm()
}



def on() {
	armNuit()
}


def off() {
	disarm()
}


/*** Commands ***/

def disarm() {
    log.trace("--- disarm")
    
    def result = new physicalgraph.device.HubAction(
        method: "GET",
        path: "/disarm",
        headers: [
            HOST: "${alarm_ip}:${alarm_port}"
        ]
    )
    sendHubCommand(result)
    log.debug result

    sendEvent(name: "switch", value: "off")
    sendEvent(name: "lock", value: "unlocked")
    
}

def armFull() {
    log.trace("--- arm_full")

    def result = new physicalgraph.device.HubAction(
        method: "GET",
        path: "/arm/full",
        headers: [
            HOST: "${alarm_ip}:${alarm_port}"
        ]
    )
    sendHubCommand(result)
    log.debug result

    sendEvent(name: "switch", value: "off", isStateChange: true)
    sendEvent(name: "lock", value: "locked", isStateChange: true)

}

def armNuit() {
    log.trace("--- arm_nuit")

    def result = new physicalgraph.device.HubAction(
        method: "GET",
        path: "/arm/nuit",
        headers: [
            HOST: "${alarm_ip}:${alarm_port}"
        ]
    )
    sendHubCommand(result)
    log.debug result

    sendEvent(name: "switch", value: "on")
    sendEvent(name: "lock", value: "locked")

}


