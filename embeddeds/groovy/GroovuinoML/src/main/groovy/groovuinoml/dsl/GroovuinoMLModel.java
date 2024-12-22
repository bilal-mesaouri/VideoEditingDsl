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
	
	public void createVideo(String name, String path, float duration) {
		Video video = new Video();
		video.setName(name);
		video.setPath(path);
		video.setDuration(duration);
		this.binding.setVariable(name, video);
		this.ressources.add(video);
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