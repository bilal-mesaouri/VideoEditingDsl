// Déclare une piste audio et une vidéo
audio "audioTrack" path "audio.mp3" duration 5
video "videoClip" path "video.mp4" duration 10

// Synchronise la piste audio avec la vidéo
setAudio "combinedVideo" audio audioTrack video videoClip

// Exporte le projet
export "AudioSyncTest"
