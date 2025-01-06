# Wiring code generated from an ArduinoML model
# Application name: Test

from moviepy.editor import VideoFileClip, concatenate_videoclips, TextClip, CompositeVideoClip

clip1 = VideoFileClip("video.mp4")
subtitle1 = TextClip(txt='Mon texte', font='Arial', fontsize=70, color='white')
subtitle1 = subtitle1.set_position((100, 200))
result1 = CompositeVideoClip([clip1, subtitle1.set_start(5.0).set_duration(10.0)])

