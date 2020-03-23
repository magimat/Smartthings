preferences {
    

}
 

metadata {

	definition (name: "RM Bridge TV Remote", namespace: "magimat", author: "Mathieu Girard") {
        capability "switch" 

        command "tv_on" 
        command "tv_off"
        command "tv_hdmi3"

        command "atv_up"
        command "atv_down"
        command "atv_right"
        command "atv_left"
        command "atv_menu"
        command "atv_play"
        command "atv_ok"

        command "sb_inputtv"
        command "sb_volup"
        command "sb_voldown"

}



    standardTile("off", "device.switch", decoration: "flat", canChangeIcon: false) {
        state "default", label:'off', action:"switch.off", icon:"st.samsung.da.RC_ic_power", backgroundColor:"#888888"
    }    
    standardTile("up", "device.switch", decoration: "flat", canChangeIcon: false) {
        state "default", label:'Up', action:"atv_up", icon:"st.thermostat.thermostat-up"
    }
    standardTile("on", "device.switch", decoration: "flat", canChangeIcon: false) {
        state "default", label:'on', action:"switch.on", icon:"st.samsung.da.RC_ic_power", backgroundColor:"#888888"
    }  
    
    
    standardTile("left", "device.switch", decoration: "flat", canChangeIcon: false) {
        state "default", label:'Left', action:"atv_left", icon:"st.thermostat.thermostat-left"
    }
    standardTile("ok", "device.switch", decoration: "flat", canChangeIcon: false) {
        state "default", label:'ok', action:"atv_ok", icon:"st.illuminance.illuminance.dark"
    }
    standardTile("right", "device.switch", decoration: "flat", canChangeIcon: false) {
        state "default", label:'Right', action:"atv_right", icon:"st.thermostat.thermostat-right"
    }
    
    
    standardTile("voldown", "device.switch", decoration: "flat", canChangeIcon: false) {
        state "default", label:'Vol-', action:"sb_voldown", icon:"st.thermostat.thermostat-down"
    }
	standardTile("down", "device.switch", decoration: "flat", canChangeIcon: false) {
        state "default", label:'Down', action:"atv_down", icon:"st.thermostat.thermostat-down"
    }
	standardTile("volup", "device.switch", decoration: "flat", canChangeIcon: false) {
        state "default", label:'Vol+', action:"sb_volup", icon:"st.thermostat.thermostat-up"
    }

    
	standardTile("menu", "device.switch", decoration: "flat", canChangeIcon: false) {
        state "default", label:'menu', action:"atv_menu", icon:"st.vents.vent"
    }
	standardTile("hdmi3", "device.switch", decoration: "flat", canChangeIcon: false) {
        state "default", label:'hdmi3', action:"tv_hdmi3", icon:"st.Electronics.electronics15"
    }
	standardTile("tv", "device.switch", decoration: "flat", canChangeIcon: false) {
        state "default", label:'tv', action:"sb_inputtv", icon:"st.Electronics.electronics15"
    }


	main "switch"
    details (["off","up","on", "left","ok","right","voldown","down","volup", "menu", "hdmi3", "tv"])	
}
 
def irAction(buttonPath) {
	log.debug "executing....$buttonPath" 

	def result = new physicalgraph.device.HubAction(
        method: "GET",
        path: "$buttonPath",
        headers: [
            HOST: "192.168.11.202:4000"
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

def tv_hdmi3() {
    irAction("/tv/hdmi3") 
}


def atv_up() {
    irAction("/atv/up") 
}
 
def atv_down() {
    irAction("/atv/down") 
}

def atv_right() {
    irAction("/atv/right") 
}
 
def atv_left() {
    irAction("/atv/left") 
}

def atv_menu() {
    irAction("/atv/menu") 
}
 
def atv_play() {
    irAction("/atv/play") 
}

def atv_ok() {
    irAction("/atv/ok") 
}
 

def sb_inputtv() {
    irAction("/sb/inputtv") 
}
 
def sb_volup() {
    irAction("/sb/volup") 
}

def sb_voldown() {
    irAction("/sb/voldown") 
}