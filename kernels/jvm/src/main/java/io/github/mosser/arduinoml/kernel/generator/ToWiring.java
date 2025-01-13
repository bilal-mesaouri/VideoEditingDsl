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


		w("from moviepy.editor import VideoFileClip, concatenate_videoclips, TextClip, " +
				"CompositeVideoClip, ColorClip, vfx\n\n");

		for (Visitable e : app.getElements()) {
			e.accept(this);
		}
	}

	@Override
	public void visit(Cut cut) {
		if (cut.getTarget() instanceof Video) {
			w(String.format(Locale.US,
					"%s = %s.subclip(%f, %f)\n",
					cut.getName(),
					cut.getTarget().getName(),
					cut.getStartTime(),
					cut.getEndTime()
			));
		} else if (cut.getTarget() instanceof Audio) {
			w(String.format(Locale.US,
					"%s_audio = %s_audio.subclip(%f, %f)\n",
					cut.getName(),
					cut.getTarget().getName(),
					cut.getStartTime(),
					cut.getEndTime()
			));
		} else {
			throw new IllegalArgumentException("Cut can only be applied to Audio or Video resources");
		}
	}



	@Override
	public void visit(Video video) {
		w(String.format("%s = VideoFileClip(\"%s\").set_fps(24)\n",
				video.getName(),
				video.getPath()
		));
	}
	@Override
	public void visit(After after) {
		// Vérifiez la source et la cible
		if (after.getSource() == null || after.getTarget() == null) {
			throw new IllegalStateException("Source or target cannot be null for an After operation");
		}

		// Concaténer en s'assurant des dimensions et fps
		w(String.format(Locale.US,
				"%s = concatenate_videoclips([" +
						"%s.resize(height=1080, width=1920).set_fps(24), " +
						"%s.resize(height=1080, width=1920).set_fps(24)]).set_fps(24)\n",
				after.getName(),
				after.getSource().getName(),
				after.getTarget().getName()
		));
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
		// Générer le fond noir avec les dimensions spécifiées
		w(String.format(Locale.US,
				"background_%s = ColorClip(size=(%d, %d), color=(0, 0, 0), duration=%.1f).set_fps(24)\n",
				textVideo.getName(),
				textVideo.getWidth(),
				textVideo.getHeight(),
				textVideo.getDuration()
		));

		// Générer le texte
		w(String.format(Locale.US,
				"text_%s = TextClip(txt='%s', font='Arial', color='%s', fontsize=70, size=(%d, %d))\n",
				textVideo.getName(),
				textVideo.getContent(),
				textVideo.getTextColor(),
				textVideo.getWidth(),
				textVideo.getHeight()
		));

		// Positionner le texte
		w(String.format(Locale.US,
				"text_%s = text_%s.set_position('center')\n",
				textVideo.getName(),
				textVideo.getName()
		));

		// Composer le clip final
		w(String.format(Locale.US,
				"%s = CompositeVideoClip([background_%s, text_%s]).set_duration(%.1f).set_fps(24)\n",
				textVideo.getName(),
				textVideo.getName(),
				textVideo.getName(),
				textVideo.getDuration()
		));
	}


	@Override
	public void visit(Superpose superpose) {
		w(String.format(Locale.US,
				"%s = CompositeVideoClip([%s, %s.set_start(%.1f).set_duration(%.1f)]).set_fps(24)\n",  // Ajout du fps
				superpose.getName(),
				superpose.getSource().getName(),
				superpose.getTarget().getName(),
				superpose.getStartTime(),
				superpose.getDuration()
		));
	}

	@Override
	public void visit(Audio audio) {
		w(String.format("# Chargement de l'audio : %s\n", audio.getName()));
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
		w(String.format(Locale.US,
				"%s_audio = %s_audio.volumex(%f)\n",
				adjustVolume.getName(),
				adjustVolume.getTarget().getName(),
				adjustVolume.getVolumeFactor()
		));
	}



	@Override
	public void visit(AudioTransition audioTransition) {
		// Appliquez fadeout sur la première piste
		w(String.format(Locale.US,
				"%s_audio_faded_out = %s_audio.audio_fadeout(%f)\n",
				audioTransition.getSource().getName(),
				audioTransition.getSource().getName(),
				audioTransition.getTransitionDuration()
		));

		// Appliquez fadein sur la seconde piste
		w(String.format(Locale.US,
				"%s_audio_faded_in = %s_audio.audio_fadein(%f)\n",
				audioTransition.getTarget().getName(),
				audioTransition.getTarget().getName(),
				audioTransition.getTransitionDuration()
		));

		// Concaténez les pistes audio après avoir appliqué les transitions
		w(String.format(Locale.US,
				"%s_audio = concatenate_audioclips([%s_audio_faded_out, %s_audio_faded_in])\n",
				audioTransition.getName(),
				audioTransition.getSource().getName(),
				audioTransition.getTarget().getName()
		));
	}






	@Override
	public void visit(Stack stack) {
		// Vérifiez que la cible a un clip
		if (stack.getTarget() == null) {
			throw new IllegalStateException("Target video for stacking cannot be null");
		}

		// Générer la vidéo empilée avec des dimensions cohérentes
		w(String.format(Locale.US,
				"%s_resized = %s.resize(%.2f)\n",
				stack.getTarget().getName(),
				stack.getTarget().getName(),
				stack.getScale()
		));

		w(String.format(Locale.US,
				"%s = CompositeVideoClip([%s, %s_resized.set_position((%d, %d))]).set_fps(24).resize(height=1080, width=1920)\n",
				stack.getName(),
				stack.getSource().getName(),
				stack.getTarget().getName(),
				stack.getCorner().getX(),
				stack.getCorner().getY()
		));
	}

	@Override
	public void visit(Fade fade) {
		if (fade.getStack() != null) {
			// Vérifiez que la pile a une vidéo cible valide
			if (fade.getStack().getTarget() == null) {
				throw new IllegalStateException("Stack target cannot be null for a fade operation");
			}

			if (fade.getType().equals("IN")) {
				w(String.format(Locale.US, "%s = %s.fx(vfx.fadein, %.1f).set_duration(%s.duration).resize(height=1080, width=1920)\n",
						fade.getTarget().getName(),
						fade.getTarget().getName(),
						fade.getDuration(),
						fade.getTarget().getName()
				));
			} else {  // "OUT"
				w(String.format(Locale.US, "%s = %s.fx(vfx.fadeout, %.1f).set_duration(%s.duration).resize(height=1080, width=1920)\n",
						fade.getTarget().getName(),
						fade.getTarget().getName(),
						fade.getDuration(),
						fade.getTarget().getName()
				));
			}

			// Générer le clip composite
			w(String.format(Locale.US, "%s = CompositeVideoClip([%s, %s_resized.set_position((%d, %d))]).set_duration(%s.duration).set_fps(24)\n",
					fade.getStack().getName(),
					fade.getTarget().getName(),
					fade.getStack().getTarget().getName(),
					fade.getStack().getCorner().getX(),
					fade.getStack().getCorner().getY(),
					fade.getTarget().getName()
			));
		} else {
			if (fade.getType().equals("IN")) {
				w(String.format(Locale.US, "%s = %s.fx(vfx.fadein, %.1f).set_duration(%s.duration).resize(height=1080, width=1920)\n",
						fade.getTarget().getName(),
						fade.getTarget().getName(),
						fade.getDuration(),
						fade.getTarget().getName()
				));
			} else {  // "OUT"
				w(String.format(Locale.US, "%s = %s.fx(vfx.fadeout, %.1f).set_duration(%s.duration).resize(height=1080, width=1920)\n",
						fade.getTarget().getName(),
						fade.getTarget().getName(),
						fade.getDuration(),
						fade.getTarget().getName()
				));
			}
		}
	public void visit(Snippet snippet) {
		// TODO Auto-generated method stub

		w(String.format("#start %s\n", snippet.getName()));
		w(snippet.getCode());
		w("#end \n");

	}

}
