textVideo "intro" content "Holiday in Paris - Summer 2024" backgroundColor "black" textColor "white" width 1920 height 1080 duration 10

video "clip1" path "video1.mp4" duration 30
video "clip2" path "video2.mp4" duration 30

video "bobComment" path "bobcomment.mp4" duration 30

stack "commentedClip" overlay bobComment main clip1 scale 0.3 corner "BOTTOM_RIGHT"

fade "fadeOut1" target clip1 duration 1.0 type "OUT" stack commentedClip

fade "fadeIn2" target clip2 duration 1.0 type "IN"

textVideo "thanks" content "Thanks for watching!" backgroundColor "black" textColor "white" width 1920 height 1080 duration 15

after "sequence1" firstVideo intro secondVideo commentedClip
after "sequence2" firstVideo sequence1 secondVideo clip2  // Utilisation de clip2 au lieu de fadeIn2
after "final" firstVideo sequence2 secondVideo thanks

export "youtubeVideo"