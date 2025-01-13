// Définir plusieurs pistes audio avec des durées variées
audio "backgroundMusic" path "background.wav" duration "10"
audio "voiceOver1" path "voice1.wav" duration "8"
audio "voiceOver2" path "voice2.wav" duration "6"
audio "effectsTrack" path "effects.wav" duration "5"

// Découper plusieurs pistes audio
cut "shortBackground" target backgroundMusic startTime "2.0" endTime "6.0"
cut "shortVoiceOver1" target voiceOver1 startTime "1.0" endTime "5.0"

// Appliquer des transitions individuelles
audioTransition "shortBackgroundFaded" firstAudio shortBackground secondAudio shortBackground transitionDuration "0.5"
audioTransition "shortVoiceOver1Faded" firstAudio shortVoiceOver1 secondAudio shortVoiceOver1 transitionDuration "0.5"

// Concaténer plusieurs pistes audio après les transitions
concatAudio "combinedVoiceOver" firstAudio shortVoiceOver1Faded secondAudio voiceOver2
concatAudio "fullAudioTrack" firstAudio shortBackgroundFaded secondAudio combinedVoiceOver

// Ajuster le volume de différentes pistes
adjustVolume "adjustedEffects" audio effectsTrack volumeFactor "0.6"
adjustVolume "adjustedFullAudio" audio fullAudioTrack volumeFactor "0.8"

// Ajouter une transition finale entre l'audio principal et les effets sonores
audioTransition "finalAudioMix" firstAudio adjustedFullAudio secondAudio adjustedEffects transitionDuration "3"

// Charger plusieurs vidéos
video "introVideo" path "intro.mp4" duration 5
video "mainVideo" path "main.mp4" duration 10
video "outroVideo" path "outro.mp4" duration 7

// Combiner les vidéos avec des effets audio synchronisés
after "introMainVideo" firstVideo introVideo secondVideo mainVideo
after "finalVideoSequence" firstVideo introMainVideo secondVideo outroVideo

// Synchroniser l'audio mixé avec la séquence vidéo finale
setAudio "finalExport" audio finalAudioMix video finalVideoSequence

// Exporter le projet final
export "ComplexAudioSupportProject"
