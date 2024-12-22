package main.groovy.groovuinoml.dsl

import io.github.mosser.arduinoml.kernel.behavioral.*

import io.github.mosser.arduinoml.kernel.structural.*


abstract class GroovuinoMLBasescript extends Script {
//	public static Number getDuration(Number number, TimeUnit unit) throws IOException {
//		return number * unit.inMillis;
//	}

	// pinSensor "name" pin n
	def video(String name) {
		[path: { path ->
			[
				duration: { duration -> ((GroovuinoMLBinding)this.getBinding()).getGroovuinoMLModel().createVideo(name, path, duration) },
			]	
		}	
		]
	}
	// actuator "name" pin n

	def after(String name) {
		[firstVideo: { source ->
			[
				secondVideo: { target -> 
				sourceObject = source instanceof String ? (Object)((GroovuinoMLBinding)this.getBinding()).getVariable(source) : (Object)source
				targetObject = target instanceof String ? (Object)((GroovuinoMLBinding)this.getBinding()).getVariable(target) : (Object)target
				((GroovuinoMLBinding)this.getBinding()).getGroovuinoMLModel().createAfter(sourceObject, targetObject, name) 				
				},

			]	
		}	
		]
	}

	// export name
	def export(String name) {
		println(((GroovuinoMLBinding) this.getBinding()).getGroovuinoMLModel().generateCode(name).toString())
	}
	
	// disable run method while running
	int count = 0
	abstract void scriptBody()
	def run() {
		if(count == 0) {
			count++
			scriptBody()
		} else {
			println "Run method is disabled"
		}
	}
}
