video "videoA" path "videoA.mp4" duration 10
video "videoB" path "videoB.mp4" duration 10

after "videoC" firstVideo videoA secondVideo videoB

after "videoD" firstVideo videoC secondVideo videoA

export "Switch!"