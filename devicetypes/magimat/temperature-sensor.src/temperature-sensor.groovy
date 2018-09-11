
metadata {
	definition (name: "Temperature Sensor", namespace: "magimat", author: "Mathieu Girard") {
		capability "Temperature Measurement"
		capability "Relative Humidity Measurement"
		capability "Sensor"

		command "updateTemp"
		command "updateHumidity"

	}

	simulator {

	    
        
	}

	// UI tile definitions
	tiles {
		valueTile("temperature", "device.temperature", width: 2, height: 1) {
			state("temperature", label:'${currentValue}°',
				backgroundColors:[
					[value: 31, color: "#153591"],
					[value: 44, color: "#1e9cbb"],
					[value: 59, color: "#90d2a7"],
					[value: 74, color: "#44b621"],
					[value: 84, color: "#f1d801"],
					[value: 95, color: "#d04e00"],
					[value: 96, color: "#bc2323"]
				]
			)
		}
		valueTile("humidity", "device.humidity") {
			state "humidity", label:'${currentValue}%', unit:""
		}

		htmlTile(name:"graphHTML",
			action: "getGraphHTML",
			refreshInterval: 1,
			width: 3,
			height: 3,
			whitelist: ["www.gstatic.com"])

		main(["temperature"])
		details(["temperature", "humidity", "graphHTML"])
	}
}


mappings {
	path("/getGraphHTML") {action: [GET: "getGraphHTML"]}
}






def parse(String description) {
}

def updateTemp(temp) {
	sendEvent(name: "temperature", value: temp)
}

def updateHumidity(hum) {
	sendEvent(name: "humidity", value: hum)
}


def getLastEvents() {

	log.trace("getLastEvents")
   
   
   device.events().each {
   
   		try {log.debug(it.name)} catch(Exception e) {log.debug('exeption!!')}
   }


}




def getGraphHTML() {

getLastEvents()


	def html = """
		<!DOCTYPE html>
			<html>


  <head>
    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    <script type="text/javascript" src="http://localhost:8080/data.txt"></script>
    <script>

    google.charts.load('current', {'packages':['line', 'corechart']});
    google.charts.setOnLoadCallback(drawChart);

    function drawChart() {

    var chartDiv = document.getElementById('chart_div');

    var data = new google.visualization.DataTable();
    data.addColumn('date');
    data.addColumn('number');
    data.addColumn('number');

var datatxt =
[
      [new Date(2017, 11, 8, 8, 5),  21.5,  88],
      [new Date(2017, 11, 8, 8, 10),  21.6,  83],
      [new Date(2017, 11, 8, 8, 15),  21.6,  77],
      [new Date(2017, 11, 8, 8, 20),  21.3,  72],
      [new Date(2017, 11, 8, 8, 25),  21.1,  77],
      [new Date(2017, 11, 8, 8, 30),  19.5,  60],
      [new Date(2017, 11, 8, 8, 35),  19.4,  50],
      [new Date(2017, 11, 8, 8, 40),  20.3,  54],
      [new Date(2017, 11, 8, 8, 45),  21.1,  53],
      [new Date(2017, 11, 8, 8, 50),  21.0,  55],
]


    data.addRows(datatxt);

    var materialOptions = {
      height: 800,
      legend: {position: 'none'},
      
      series: {
        0: {axis: 'temp'},
        1: {axis: 'humitidy'}
      },
      
      axes: {
        // Adds labels to each axis; they don't have to match the axis names.
        y: {
          temp: {
          				//range: { min: 10, max: 26},
                  textStyle: { color: '#0000FF' }
                },
          humitidy: {
          						range: { min: 0, max: 100}
                    }
        }
      }
    };


      var materialChart = new google.charts.Line(chartDiv);
      materialChart.draw(data, materialOptions);
    }
    </script>
  </head>

  <body>
      <div id="chart_div"></div>
  </body>
</html>

		"""
	render contentType: "text/html", data: html, status: 200
}


