# Wiring code generated from an ArduinoML model
# Application name: Test

from moviepy.editor import VideoFileClip, concatenate_videoclips, TextClip, CompositeVideoClip

background_intro = ColorClip(size=(1920, 1080), color='black', duration=10.0)
text_intro = TextClip(txt='Holiday in Paris - Summer 2024', font='Arial', color='white', fontsize=70)
text_intro = text_intro.set_position('center')
intro = CompositeVideoClip([background_intro, text_intro])
clip1 = VideoFileClip("video.mp4")

#start codeSnippet1
print("Mamak")
#end


s1 = TextClip(txt='Mon texte', font='Arial', fontsize=70, color='white')
s1 = s1.set_position((100, 200))
s2 = TextClip(txt='not in french', font='Arial', fontsize=70, color='white')
s2 = s2.set_position((100, 200))
s3 = TextClip(txt='third sub', font='Arial', fontsize=70, color='white')
s3 = s3.set_position((100, 200))
background_outro = ColorClip(size=(1920, 1080), color='black', duration=10.0)
text_outro = TextClip(txt='adios', font='Arial', color='white', fontsize=70)
text_outro = text_outro.set_position('center')
outro = CompositeVideoClip([background_outro, text_outro])
clip1a = clip1.subclip(23.0, 107.0)clip1b = clip1.subclip(121.0, 141.0)clip1a = CompositeVideoClip([clip1a, s1.set_start(0.0).set_duration(10.0)])
clip1a = CompositeVideoClip([clip1a, s2.set_start(40.0).set_duration(10.0)])
result1 = concatenate_videoclips([clip1a, clip1b])
result1 = CompositeVideoClip([clip1a, s3.set_start(99.0).set_duration(15.0)])
result2 = concatenate_videoclips([intro, result1])
result3 = concatenate_videoclips([result2, outro])

