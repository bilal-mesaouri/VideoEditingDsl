package io.github.mosser.arduinoml.kernel.generator;

import io.github.mosser.arduinoml.kernel.App;
import io.github.mosser.arduinoml.kernel.behavioral.*;
import io.github.mosser.arduinoml.kernel.structural.*;

import java.util.Locale;

/**
 * Quick and dirty visitor to support the generation of Wiring code
 */
public class ToWiring extends Visitor<StringBuffer> {

	public ToWiring() {
		this.result = new StringBuffer();
	}

	private void w(String s) {
		result.append(String.format("%s",s));
	}

	@Override
	public void visit(App app) {
		w("# Wiring code generated from an ArduinoML model\n");
		w(String.format("# Application name: %s\n", app.getName()) + "\n");

		w("from moviepy.editor import VideoFileClip, concatenate_videoclips, TextClip, CompositeVideoClip\n\n");

		for(Ressource ressource: app.getRessources()) {
			ressource.accept(this);
		}
		for(Action action: app.getActions()) {
			action.accept(this);
		}
	}

	@Override
	public void visit(Cut cut) {
		// TODO Auto-generated method stub
		w(String.format("%s = %s.subclip(%s, %s)", cut.getName(), cut.getTarget().getName(), cut.getStartTime(), cut.getEndTime()));
		return;
	}

	@Override
	public void visit(Video video) {
		w(String.format("%s = VideoFileClip(\"%s\")\n",
				video.getName(),
				video.getPath()
		));
	}
	@Override
	public void visit(After after) {

		w(String.format("%s = concatenate_videoclips([%s, %s])\n",after.getName() , after.getSource().getName(), after.getTarget().getName()));
		return;

	}

	@Override
	public void visit(Text text) {
		w(String.format("%s = TextClip(txt='%s', font='%s', fontsize=70, color='white')\n",
				text.getName(),
				text.getContent(),
				text.getFont()
		));
		if (text.getPositionX() != 0 || text.getPositionY() != 0) {
			w(String.format("%s = %s.set_position((%d, %d))\n",
					text.getName(),
					text.getName(),
					text.getPositionX(),
					text.getPositionY()
			));
		}
	}

	@Override
	public void visit(TextVideo textVideo) {

		w(String.format("background_%s = ColorClip(size=(%d, %d), color='%s', duration=%.1f)\n",
				textVideo.getName(),
				textVideo.getWidth(),
				textVideo.getHeight(),
				textVideo.getBackgroundColor(),
				textVideo.getDuration()
		));


		w(String.format("text_%s = TextClip(txt='%s', font='Arial', color='%s', fontsize=70)\n",
				textVideo.getName(),
				textVideo.getContent(),
				textVideo.getTextColor()
		));


		w(String.format("text_%s = text_%s.set_position('center')\n",
				textVideo.getName(),
				textVideo.getName()
		));

		// Combine background and text
		w(String.format("%s = CompositeVideoClip([background_%s, text_%s])\n",
				textVideo.getName(),
				textVideo.getName(),
				textVideo.getName()
		));
	}
	@Override
	public void visit(Superpose superpose) {
		// Forcer l'utilisation de la locale anglaise pour avoir des points
		w(String.format(Locale.US,
				"%s = CompositeVideoClip([%s, %s.set_start(%.1f).set_duration(%.1f)])\n",
				superpose.getName(),
				superpose.getVideo().getName(),
				superpose.getText().getName(),
				superpose.getStartTime(),
				superpose.getDuration()
		));
	}

	@Override
	public void visit(Audio audio) {
		w(String.format("%s_audio = AudioFileClip('%s')\n", audio.getName(), audio.getPath()));
	}


	@Override
	public void visit(SetAudio setAudio) {
		w(String.format("%s_combined = %s.set_audio(%s_audio)\n",
				setAudio.getName(),
				setAudio.getTarget().getName(),
				setAudio.getSource().getName()));
	}

	@Override
	public void visit(ConcatAudio concatAudio) {
		w(String.format("%s_audio = concatenate_audioclips([%s_audio, %s_audio])\n",
				concatAudio.getName(),
				concatAudio.getSource().getName(),
				concatAudio.getTarget().getName()));
	}

	@Override
	public void visit(AdjustVolume adjustVolume) {
		w(String.format("%s_audio = %s_audio.volumex(%f)\n",
				adjustVolume.getName(),
				adjustVolume.getTarget().getName(),
				adjustVolume.getVolumeFactor()));
	}


	@Override
	public void visit(AudioTransition audioTransition) {
		w(String.format("%s_audio = %s_audio.crossfadeout(%f).crossfadein(%s_audio, %f)\n",
				audioTransition.getName(),
				audioTransition.getSource().getName(),
				audioTransition.getTransitionDuration(),
				audioTransition.getTarget().getName(),
				audioTransition.getTransitionDuration()));
	}



}
