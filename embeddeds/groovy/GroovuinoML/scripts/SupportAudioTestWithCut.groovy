audio "introAudio" path "intro.mp3" duration "5"
audio "explanation1" path "explanation1.mp3" duration 8
audio "explanation2" path "explanation2.mp3" duration 6

// Découper une piste audio
cut "shortAudio" audio introAudio startTime "1.0" endTime "3.0"

// Concaténer des pistes audio
concatAudio "combinedAudio" firstAudio shortAudio secondAudio explanation1

// Ajuster le volume
adjustVolume "adjustedAudio" audio combinedAudio volumeFactor "0.8"

// Ajouter une transition
audioTransition "smoothTransition" firstAudio adjustedAudio secondAudio explanation2 transitionDuration "2"

// Synchroniser avec une vidéo
video "mainVideo" path "video.mp4" duration 15
setAudio "finalVideo" audio smoothTransition video mainVideo

// Exporter le projet
export "AudioEnhancedProject"
