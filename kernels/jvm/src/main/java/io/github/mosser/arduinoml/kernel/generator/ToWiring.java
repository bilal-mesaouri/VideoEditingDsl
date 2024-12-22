package io.github.mosser.arduinoml.kernel.generator;

import io.github.mosser.arduinoml.kernel.App;
import io.github.mosser.arduinoml.kernel.behavioral.*;
import io.github.mosser.arduinoml.kernel.structural.*;

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
		//first pass, create global vars
		w("# Wiring code generated from an ArduinoML model\n");
		w(String.format("# Application name: %s\n", app.getName())+"\n");

		w("from moviepy.editor import VideoFileClip, concatenate_videoclips\n");


		for(Ressource ressource: app.getRessources()){
			ressource.accept(this);
		}
		for(Action action: app.getActions()){
			action.accept(this);
		}


	}

	@Override
	public void visit(Video video) {

		w(String.format("%s = VideoFileClip(%s)\n", video.getName(), video.getPath()));
		return;
		
	}

	@Override
	public void visit(After after) {

		w(String.format("%s = concatenate_videoclips([%s, %s])\n",after.getName() , after.getSource().getName(), after.getTarget().getName()));
		return;
		
	}

	@Override
	public void visit(Cut cut) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'visit'");
	}

	@Override
	public void visit(Text text) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'visit'");
	}

	@Override
	public void visit(TextVideo textVideo) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'visit'");
	}

}
