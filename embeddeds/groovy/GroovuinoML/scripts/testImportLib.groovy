

importLib "testImportLib.py"

video "clip" path "video.mp4" duration 200

// Load a custom effect from the imported library and apply it

load "applyFilter" args "vintage", 0.8

text "s1" content "Mon texte" font "Arial" positionX 100 positionY 200


export "finalResult" ressource  "clip" filepath "finalResult.mp4" fps 24 codec "libx264"


generate "Test"