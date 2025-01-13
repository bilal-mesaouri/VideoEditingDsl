audio "introAudio" path "intro.wav" duration "5"
audio "explanation1" path "explanation1.wav" duration "8"
audio "explanation2" path "explanation2.wav" duration "6"

concatAudio "combinedAudio" firstAudio introAudio secondAudio explanation1

adjustVolume "adjustedAudio" audio combinedAudio volumeFactor "0.8"

audioTransition "smoothTransition" firstAudio adjustedAudio secondAudio explanation2 transitionDuration "2"

video "mainVideo" path "main.mp4" duration 15

setAudio "finalVideo" audio smoothTransition video mainVideo

export "AudioEnhancedProject"
