

reference "after.py"

textVideo "intro" content "Holiday in Paris - Summer 2024" backgroundColor "black" textColor "white" width 1920 height 1080 duration 10

video "clip1" path "video.mp4" duration 200

cut   "clip1a" video clip1 startTime "23" endTime "107"

cut   "clip1b" video clip1 startTime "121" endTime "141"

load "codeSnippet1" args "clip1", "clip1a", "clip1b"

text "s1" content "Mon texte" font "Arial" positionX 100 positionY 200

superpose "clip1a" firstVideo clip1a secondVideo s1 startTime 0 duration 10

text "s2" content "not in french" font "Arial" positionX 100 positionY 200

superpose "clip1a" firstVideo clip1a secondVideo s2 startTime 40 duration 10

after "result1" firstVideo clip1a secondVideo clip1b

text "s3" content "third sub" font "Arial" positionX 100 positionY 200

superpose "result1" firstVideo clip1a secondVideo s3 startTime 99 duration 15


textVideo "outro" content "adios" backgroundColor "black" textColor "white" width 1920 height 1080 duration 10
load "codeSnippet1"
after "result2" firstVideo intro secondVideo result1

after "result3" firstVideo result2 secondVideo outro





export "Test"