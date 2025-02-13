package io.github.mosser.arduinoml.kernel.generator;

import io.github.mosser.arduinoml.kernel.behavioral.*;
import io.github.mosser.arduinoml.kernel.structural.*;
import io.github.mosser.arduinoml.kernel.App;

import java.util.HashMap;
import java.util.Map;

public abstract class Visitor<T> {

	public abstract void visit(App app);

	public abstract void visit(Cut cut);

	public abstract void visit(After after);

	public abstract void visit(Text text);
	public abstract void visit(Superpose superpose);
	
	public abstract void visit(TextVideo textVideo);
	public abstract void visit(Video Video);
	public abstract void visit(Audio audio);
	public abstract void visit(SetAudio setAudio);
	public abstract void visit(ConcatAudio concatAudio);
	public abstract void visit(AdjustVolume adjustVolume);
	public abstract void visit(AudioTransition audioTransition);

	public abstract void visit(Snippet snippet); 
	public abstract void visit(Export export);

	/***********************
	 ** Helper mechanisms **
	 ***********************/

	protected Map<String,Object> context = new HashMap<>();

	protected T result;

	public T getResult() {
		return result;
	}



	public abstract void visit(Stack stack);

	public abstract void visit(Fade fade);
}

