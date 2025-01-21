// (a) Écran titre d'introduction
textVideo "intro" content "Introduction" backgroundColor "black" textColor "white" width 1920 height 1080 duration 10


video "clip1" path "video.mp4" duration 180  // durée exemple de 3 minutes

cut "clip1a" video clip1 startTime "23" endTime "87"
cut "clip1b" video clip1 startTime "121" endTime "141"


text "s1" content "Premier sous-titre" font "Arial" positionX 100 positionY 200
text "s2" content "Deuxième sous-titre" font "Arial" positionX 100 positionY 200
superpose "clip1a_s1" firstVideo clip1a secondVideo s1 startTime 0 duration 10
superpose "clip1a_s2" firstVideo clip1a secondVideo s2 startTime 30 duration 10

after "clip1ab" firstVideo clip1a_s1 secondVideo clip1a_s2

text "s3" content "Dernier sous-titre" font "Arial" positionX 100 positionY 200
superpose "clip1b_s3" firstVideo clip1ab secondVideo s3 startTime 71 duration 15


textVideo "thanks" content "Merci d'avoir regardé" backgroundColor "black" textColor "white" width 1920 height 1080 duration 10


after "sequence1" firstVideo clip1a_s1 secondVideo clip1a_s2
after "sequence2" firstVideo sequence1 secondVideo clip1b_s3
after "final" firstVideo sequence2 secondVideo thanks


export "AliceCast"

generate "Test"