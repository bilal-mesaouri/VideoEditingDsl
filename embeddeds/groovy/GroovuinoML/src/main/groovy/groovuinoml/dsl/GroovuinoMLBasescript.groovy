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

	def cut(String name) {
		[
				video: { video ->
					[
							startTime: { String startTime ->
								[
										endTime: { String endTime ->
											float floatStartTime = startTime.toFloat()
											float floatEndTime = endTime.toFloat()
											def videoObject = video instanceof String ? (Object)((GroovuinoMLBinding)this.getBinding()).getVariable(video) : (Object)video
											((GroovuinoMLBinding)this.getBinding()).getGroovuinoMLModel().createCut(videoObject, name, floatStartTime, floatEndTime)
										}
								]
							}
					]
				},
				audio: { audio ->
					[
							startTime: { String startTime ->
								[
										endTime: { String endTime ->
											float floatStartTime = startTime.toFloat()
											float floatEndTime = endTime.toFloat()
											def audioObject = audio instanceof String ? (Object)((GroovuinoMLBinding)this.getBinding()).getVariable(audio) : (Object)audio
											((GroovuinoMLBinding)this.getBinding()).getGroovuinoMLModel().createCut(audioObject, name, floatStartTime, floatEndTime)
										}
								]
							}
					]
				},
				target: { target ->
					[
							startTime: { String startTime ->
								[
										endTime: { String endTime ->
											float floatStartTime = startTime.toFloat()
											float floatEndTime = endTime.toFloat()
											def targetObject = target instanceof String ? (Object)((GroovuinoMLBinding)this.getBinding()).getVariable(target) : (Object)target
											((GroovuinoMLBinding)this.getBinding()).getGroovuinoMLModel().createCut(targetObject, name, floatStartTime, floatEndTime)
										}
								]
							}
					]
				}
		]
	}
	def stack(String name) {
		[
				overlay: { overlay ->
					[
							main: { main ->
								[
										scale: { scale ->
											[
													corner: { corner ->
														def overlayObject = overlay instanceof String ? (Object)((GroovuinoMLBinding)this.getBinding()).getVariable(overlay) : (Object)overlay
														def mainObject = main instanceof String ? (Object)((GroovuinoMLBinding)this.getBinding()).getVariable(main) : (Object)main

														if (scale <= 0) {
															throw new IllegalArgumentException("Scale must be greater than 0")
														}

														((GroovuinoMLBinding)this.getBinding()).getGroovuinoMLModel().createStack(overlayObject, mainObject, scale, corner, name)
													}
											]
										}
								]
							}
					]
				}
		]
	}
	def fade(String name) {
		[target: { target ->
			[duration: { duration ->
				[type: { type ->
					[stack: { stack = null ->
						def targetObject = target instanceof String ?
								(Object)((GroovuinoMLBinding)this.getBinding()).getVariable(target) :
								(Object)target
						def stackObject = stack instanceof String ?
								(Object)((GroovuinoMLBinding)this.getBinding()).getVariable(stack) :
								(Object)stack
						((GroovuinoMLBinding)this.getBinding()).getGroovuinoMLModel().createFade(
								name,
								targetObject,
								duration,
								type,
								stackObject
						)
					}]
				}]
			}]
		}]
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
						def audio1Object = audio1 instanceof String ? (Object)((GroovuinoMLBinding)this.getBinding()).getVariable(audio1) : (Object)audio1
						if (audio1Object instanceof Action) {
							audio1Object = ((Action)audio1Object).execute()
						}

						def audio2Object = audio2 instanceof String ? (Object)((GroovuinoMLBinding)this.getBinding()).getVariable(audio2) : (Object)audio2
						if (audio2Object instanceof Action) {
							audio2Object = ((Action)audio2Object).execute()
						}

						((GroovuinoMLBinding)this.getBinding()).getGroovuinoMLModel().createConcatAudio(audio1Object, audio2Object, name)
					}
			]
		}]
	}


	def adjustVolume(String name) {
		[audio: { audio ->
			[
					volumeFactor: { factor ->
						audioObject = audio instanceof String ? (Object)((GroovuinoMLBinding)this.getBinding()).getVariable(audio) : (Object)audio
						def parsedFactor = factor instanceof String ? Float.parseFloat(factor) : factor
						((GroovuinoMLBinding)this.getBinding()).getGroovuinoMLModel().createAdjustVolume(audioObject, parsedFactor, name)
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

									def parsedDuration = duration instanceof String ? Float.parseFloat(duration) : duration
									((GroovuinoMLBinding)this.getBinding()).getGroovuinoMLModel().createAudioTransition(audio1Object, audio2Object, parsedDuration, name)
								}
						]
					}
			]
		}]
	}






}

