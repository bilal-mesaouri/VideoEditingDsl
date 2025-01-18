# Wiring code generated from an ArduinoML model
# Application name: Test

from moviepy.editor import VideoFileClip, concatenate_videoclips, TextClip, CompositeVideoClip

background_intro = ColorClip(size=(1920, 1080), color='black', duration=10.0)
text_intro = TextClip(txt='Holiday in Paris - Summer 2024', font='Arial', color='white', fontsize=70)
text_intro = text_intro.set_position('center')
intro = CompositeVideoClip([background_intro, text_intro])
clip = VideoFileClip("video.mp4")

#start applyFilter
if not applyFilterArg1 or not applyFilterArg2 :
    raise ValueError("All arguments (arg1, arg2, arg3) must be provided")
if applyFilterArg1 == "vintage":
    # Apply a vintage filter by reducing color intensity
    clip = clip.fx(colorx, applyFilterArg2)
elif applyFilterArg1 == "grayscale":
    # Convert the clip to grayscale
    clip =  clip.fx(lambda c: c.to_RGB().fx(vfx.blackwhite, applyFilterArg2))
else:
    raise ValueError(f"Unknown filter: {filter_name}")
#end


