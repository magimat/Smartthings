preferences {
    

}
 

metadata {

	definition (name: "TV Remote dad", namespace: "magimat", author: "Mathieu Girard") {
        capability "switch" 

        command "tv_on" 
        command "tv_off"
      

}



    standardTile("off", "device.switch", decoration: "flat", canChangeIcon: false) {
        state "default", label:'off', action:"switch.off", icon:"st.samsung.da.RC_ic_power", backgroundColor:"#888888"
    }    

    standardTile("on", "device.switch", decoration: "flat", canChangeIcon: false) {
        state "default", label:'on', action:"switch.on", icon:"st.samsung.da.RC_ic_power", backgroundColor:"#888888"
    }  



	main "switch"
    details (["off","on"])	
}
 
def irAction(buttonPath) {
	log.debug "executing....$buttonPath" 

	def result = new physicalgraph.device.HubAction(
        method: "GET",
        path: "$buttonPath",
        headers: [
            HOST: "192.168.0.111:4000"
        ]
    )
    sendHubCommand(result)

    log.debug result

}


def updated() {

}


def parse(String description) {
	return null
}


def off() {
    tv_off()
}
 
def on() {
    tv_on()
}


def tv_off() {
    irAction("/tv/off") 
}
 
def tv_on() {
    irAction("/tv/on") 
}