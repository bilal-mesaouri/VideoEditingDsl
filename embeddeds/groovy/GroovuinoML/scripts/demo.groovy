// Introduction avec écran titre
textVideo "openingScene" content "Travel Diary - 2024 Highlights" backgroundColor "black" textColor "white" width 1920 height 1080 duration 10

// Chargement des vidéos principales
video "highlightClip1" path "highlight1.mp4" duration 20
video "highlightClip2" path "highlight2.mp4" duration 25
video "highlightClip3" path "highlight3.mp4" duration 15

// Vidéo avec commentaire incrusté
video "commentaryClip" path "commentary.mp4" duration 10
stack "annotatedClip" overlay commentaryClip main highlightClip1 scale 0.4 corner "TOP_RIGHT"

// Ajout de transitions en fondu
fade "highlightFadeOut" target highlightClip1 duration 1.5 type "OUT" stack annotatedClip
fade "highlightFadeIn" target highlightClip2 duration 1.5 type "IN"

// Ajout de l'audio
audio "backgroundMusic" path "travel_music.wav" duration "20"
audio "voiceNarration1" path "narration1.wav" duration "10"
audio "voiceNarration2" path "narration2.wav" duration "8"

// Découpe et ajustement des pistes audio
cut "trimmedMusic" target backgroundMusic startTime "5.0" endTime "15.0"
adjustVolume "softMusic" audio trimmedMusic volumeFactor "0.5"
cut "trimmedNarration" target voiceNarration1 startTime "2.0" endTime "8.0"
adjustVolume "softNarration" audio trimmedNarration volumeFactor "0.7"

// Création de transitions audio
audioTransition "smoothNarrationTransition" firstAudio softNarration secondAudio voiceNarration2 transitionDuration "1.0"
concatAudio "combinedAudioTrack" firstAudio smoothNarrationTransition secondAudio softMusic

// Combinaison des clips vidéo avec audio synchronisé
after "compiledHighlights" firstVideo annotatedClip secondVideo highlightClip2
after "extendedHighlights" firstVideo compiledHighlights secondVideo highlightClip3
setAudio "synchronizedHighlights" audio combinedAudioTrack video extendedHighlights

// Ajout de l'écran de conclusion
textVideo "closingScene" content "Thank you for watching!" backgroundColor "black" textColor "white" width 1920 height 1080 duration 10

// Création de la vidéo finale avec transitions vidéo et audio
after "finalCompilation" firstVideo synchronizedHighlights secondVideo closingScene

// Export du projet
export "TravelDiaryFinalProject"
