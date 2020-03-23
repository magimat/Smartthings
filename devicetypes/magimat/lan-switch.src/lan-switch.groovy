import groovy.json.JsonSlurper

metadata {
	definition (name: "LAN Switch", namespace: "magimat", author: "Mathieu Girard") {
			capability "Switch"
			capability "Switch level"
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
       

def on() {


    sendEvent(name: "switch", value: "on") 
    sendEvent(name: "level", value: 100) 
}

def off() {

	sendEvent(name: "switch", value: "off")
    sendEvent(name: "level", value: 0) 
}

def setLevel(Number level) {
    sendEvent(name: "switch", value: "on")
    sendEvent(name: "level", value: ${level}) 
}



def parse(msg) {
	def map
	def bodyString
   
    map = stringToMap(msg)
    def s = mybase64Decoder(map.body);
    
	def events = []

	if(s == 'on') {
	    events << createEvent(name:"switch", value:"on")
    }
    else if(s == 'off') {
	    events << createEvent(name:"switch", value:"off")
    }
    else {
	    events << createEvent(name:"level", value:s)
    }
    
     log.debug events
    return events
}



def mybase64Decoder(String s) {
    String base64chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
	s = s.replaceAll("[^" + base64chars + "=]", "");
	String p = (s.charAt(s.length() - 1) == '=' ? (s.charAt(s.length() - 2) == '=' ? "AA" : "A") : "");
	String r = "";
	s = s.substring(0, s.length() - p.length()) + p;
	for (int c = 0; c < s.length(); c += 4) {
	    int n = (base64chars.indexOf((String)s.charAt(c)) << 18)
		n += (base64chars.indexOf((String)s.charAt(c + 1)) << 12)
		n += (base64chars.indexOf((String)s.charAt(c + 2)) << 6)
		n += base64chars.indexOf((String)s.charAt(c + 3));
	    r += "" + (char) ((n >>> 16) & 0xFF) + (char) ((n >>> 8) & 0xFF) + (char) (n & 0xFF);
	}
	return r.substring(0, r.length() - p.length());    
}