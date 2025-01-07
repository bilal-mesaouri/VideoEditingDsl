// Vidéo avec durée numérique
video "videoClip1" path "video1.mp4" duration 10
// Audio avec durée numérique
audio "introAudio" path "intro.mp3" duration 5
// Audio avec durée en chaîne
audio "explanation1" path "explanation1.mp3" duration "8"

// Ajustement du volume avec chaîne
adjustVolume "adjustedAudio1" audio introAudio volumeFactor "0.8"
// Ajustement du volume avec nombre
adjustVolume "adjustedAudio2" audio explanation1 volumeFactor 1.0

// Transition audio avec chaîne
audioTransition "smoothTransition1" firstAudio adjustedAudio1 secondAudio adjustedAudio2 transitionDuration "2"
// Transition audio avec nombre
audioTransition "smoothTransition2" firstAudio adjustedAudio1 secondAudio adjustedAudio2 transitionDuration 2

// Vidéo avec audio combiné
setAudio "finalVideo" audio smoothTransition1 video videoClip1

export "TestProject"
