package main.groovy.groovuinoml.dsl;

import java.util.ArrayList;
import java.util.List;

import groovy.lang.Binding;

import io.github.mosser.arduinoml.kernel.App;
import io.github.mosser.arduinoml.kernel.behavioral.*;
import io.github.mosser.arduinoml.kernel.generator.ToWiring;
import io.github.mosser.arduinoml.kernel.generator.Visitor;
import io.github.mosser.arduinoml.kernel.structural.*;
public class GroovuinoMLModel {
	private List<Ressource> ressources;
	private List<Action> actions;
	private Binding binding;

	public GroovuinoMLModel(Binding binding) {
		this.ressources = new ArrayList<Ressource>();
		this.actions = new ArrayList<Action>();
		this.binding = binding;
	}
	public void createTextVideo(String name, String content, String backgroundColor, String textColor,
								int width, int height, float duration) {
		TextVideo textVideo = new TextVideo();
		textVideo.setName(name);
		textVideo.setContent(content);
		textVideo.setBackgroundColor(backgroundColor);
		textVideo.setTextColor(textColor);
		textVideo.setWidth(width);
		textVideo.setHeight(height);
		textVideo.setDuration(duration);

		this.binding.setVariable(name, textVideo);
		this.ressources.add(textVideo);
	}
	public void createVideo(String name, String path, float duration) {
		Video video = new Video();
		video.setName(name);
		video.setPath(path);
		video.setDuration(duration);
		this.binding.setVariable(name, video);
		this.ressources.add(video);
	}
	public void createText(String name, String content, String font, int x, int y) {
		Text text = new Text();
		text.setName(name);
		text.setContent(content);
		text.setFont(font);
		text.setPositionX(x);
		text.setPositionY(y);
		this.binding.setVariable(name, text);
		this.ressources.add(text);
	}
	public void createSuperpose(Object video, Object text, float startTime, float duration, String name) {
		Superpose superpose = new Superpose();
		superpose.setName(name);

		if (video instanceof Video) {
			superpose.setVideo((Video) video);
		} else {
			throw new IllegalArgumentException("First argument must be a Video");
		}

		if (text instanceof Text) {
			superpose.setText((Text) text);
		} else {
			throw new IllegalArgumentException("Second argument must be a Text");
		}

		superpose.setStartTime(startTime);
		superpose.setDuration(duration);

		this.actions.add(superpose);
		this.binding.setVariable(name, superpose);
	}
	public void createAfter(Object source, Object target, String name) {
		After after = new After();
		after.setName(name);
	
		if (source instanceof Ressource) {
			after.setSource((Ressource) source);
		} else if (source instanceof Action) {
			after.setSource(((Action) source).execute());
		} else {
			throw new IllegalArgumentException("Source must be of type Ressource or Action");
		}
	
		if (target instanceof Ressource) {
			after.setTarget((Ressource) target);
		} else if (target instanceof Action) {
			after.setTarget(((Action) target).execute());
		} else {
			throw new IllegalArgumentException("Target must be of type Ressource or Action");
		}
		this.actions.add(after);
		this.binding.setVariable(name, after);
	}

	public void createCut(Object target, String name, float startTime, float endTime){
		Cut cut = new Cut();
		cut.setName(name);
		if (target instanceof Ressource) {
			cut.setTarget((Ressource) target);
		} else if (target instanceof Action) {
			cut.setTarget(((Action) target).execute());
		} else {
			throw new IllegalArgumentException("Target must be of type Ressource or Action");
		}
		cut.setStartTime(startTime);
		cut.setEndTime(endTime);
		this.actions.add(cut);
		this.binding.setVariable(name, cut);
		
	}

	@SuppressWarnings("rawtypes")
	public Object generateCode(String appName) {
		App app = new App();
		app.setName(appName);
		app.setActions(this.actions);
		app.setRessources(this.ressources);
		Visitor codeGenerator = new ToWiring();
		app.accept(codeGenerator);

		return codeGenerator.getResult();
	}
};