
textVideo "intro" content "Holiday in Paris - Summer 2024" backgroundColor "black" textColor "white" width 1920 height 1080 duration 10


video "clip1" path "video1.mp4" duration 30
video "clip2" path "video2.mp4" duration 30


textVideo "thanks" content "Thanks for watching!" backgroundColor "black" textColor "white" width 1920 height 1080 duration 15


after "sequence1" firstVideo intro secondVideo clip1
after "sequence2" firstVideo sequence1 secondVideo clip2
after "final" firstVideo sequence2 secondVideo thanks


export "HolidayVideo"