video "videoA" path "videoA.mp4" duration 10
video "videoB" path "videoB.mp4" duration 10

after "videoC" firstVideo videoA secondVideo videoB
add codSnippet1
after "videoD" firstVideo videoC secondVideo videoA

cut   "videoE" video videoD startTime "2.54" endTime "29"

export "Switch!"