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

	// Définir un fichier audio
	def audio(String name) {
		[path: { path ->
			[
					duration: { duration -> ((GroovuinoMLBinding)this.getBinding()).getGroovuinoMLModel().createAudio(name, path, duration) },
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

	def cut(String name){
		[
			video: { target ->
				[
					startTime: { String startTime ->
						[
							endTime: { String endTime ->
								float floatStartTime = startTime.toFloat()
								float floatEndTime = endTime.toFloat()
								targetObject = target instanceof String ? (Object)((GroovuinoMLBinding)this.getBinding()).getVariable(target) : (Object)target
								((GroovuinoMLBinding)this.getBinding()).getGroovuinoMLModel().createCut(targetObject, name, floatStartTime, floatEndTime) 				
							}
						]

					} 
				]

			}
		]
	}

	// export name
	def export(String name) {
		println(((GroovuinoMLBinding) this.getBinding()).getGroovuinoMLModel().generateCode(name).toString())
	}

	// setAudio "name" audio "audioName" video "videoName"
	def setAudio(String name) {
		[audio: { audio ->
			[video: { video ->
				audioObject = audio instanceof String ? (Object)((GroovuinoMLBinding)this.getBinding()).getVariable(audio) : (Object)audio
				if (audioObject instanceof Action) {
					audioObject = ((Action) audioObject).execute()
				}

				videoObject = video instanceof String ? (Object)((GroovuinoMLBinding)this.getBinding()).getVariable(video) : (Object)video
				((GroovuinoMLBinding)this.getBinding()).getGroovuinoMLModel().createSetAudio(audioObject, videoObject, name)
			}]
		}]
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


	def text(String name) {
		[content: { content ->
			[font: { font ->
				[positionX: { x->
					[positionY: {y->
						((GroovuinoMLBinding)this.getBinding()).getGroovuinoMLModel()
								.createText(name, content, font, x, y)
						}
					]
				}]
			}]
		}]
	}


	def superpose(String name) {
		[firstVideo: { video ->
			[secondVideo: { text ->
				[startTime: { start ->
					[duration: { duration ->
						def videoObj = video instanceof String ?
								((GroovuinoMLBinding)this.getBinding()).getVariable(video) :
								video
						def textObj = text instanceof String ?
								((GroovuinoMLBinding)this.getBinding()).getVariable(text) :
								text
						((GroovuinoMLBinding)this.getBinding()).getGroovuinoMLModel()
								.createSuperpose(videoObj, textObj, start, duration, name)
					}]
				}]
			}]
		}]
	}

	def textVideo(String name) {
		[content: { content ->
			[backgroundColor: { backgroundColor ->
				[textColor: { textColor ->
					[width: { width ->
						[
							height: { height->
								[
									duration: { int duration ->
									((GroovuinoMLBinding)this.getBinding()).getGroovuinoMLModel()
											.createTextVideo(name, content, backgroundColor, textColor, width, height, duration)
									}
								]
							}
						]
					}]
				}]
			}]
		}]
	}
	def concatAudio(String name) {
		[firstAudio: { audio1 ->
			[
					secondAudio: { audio2 ->
						((GroovuinoMLBinding)this.getBinding()).getGroovuinoMLModel().createConcatAudio(audio1, audio2, name)
					}
			]
		}]
	}

	def adjustVolume(String name) {
		[audio: { audio ->
			[
					volumeFactor: { factor ->
						audioObject = audio instanceof String ? (Object)((GroovuinoMLBinding)this.getBinding()).getVariable(audio) : (Object)audio
						((GroovuinoMLBinding)this.getBinding()).getGroovuinoMLModel().createAdjustVolume(audioObject, factor, name)
					}
			]
		}]
	}


	def audioTransition(String name) {
		[firstAudio: { audio1 ->
			[
					secondAudio: { audio2 ->
						[
								transitionDuration: { duration ->
									audio1Object = audio1 instanceof String ? (Object)((GroovuinoMLBinding)this.getBinding()).getVariable(audio1) : (Object)audio1
									if (audio1Object instanceof Action) {
										audio1Object = ((Action)audio1Object).execute()
									}

									audio2Object = audio2 instanceof String ? (Object)((GroovuinoMLBinding)this.getBinding()).getVariable(audio2) : (Object)audio2
									if (audio2Object instanceof Action) {
										audio2Object = ((Action)audio2Object).execute()
									}

									((GroovuinoMLBinding)this.getBinding()).getGroovuinoMLModel().createAudioTransition(audio1Object, audio2Object, duration, name)
								}
						]
					}
			]
		}]
	}





}

