audio "introAudio" path "intro.mp3" duration "5"
audio "explanation1" path "explanation1.mp3" duration "8"
audio "explanation2" path "explanation2.mp3" duration "6"

concatAudio "combinedAudio" firstAudio introAudio secondAudio explanation1

adjustVolume "adjustedAudio" audio combinedAudio volumeFactor "0.8"

audioTransition "smoothTransition" firstAudio adjustedAudio secondAudio explanation2 transitionDuration "2"

video "mainVideo" path "main.mp4" duration 15

setAudio "finalVideo" audio smoothTransition video mainVideo

export "AudioEnhancedProject"
