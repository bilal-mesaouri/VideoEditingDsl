package main.groovy.groovuinoml.dsl;

import java.math.BigDecimal;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import groovy.lang.Binding;

import io.github.mosser.arduinoml.kernel.App;
import io.github.mosser.arduinoml.kernel.behavioral.*;
import io.github.mosser.arduinoml.kernel.generator.*;
import io.github.mosser.arduinoml.kernel.structural.*;
public class GroovuinoMLModel {
	// private List<Ressource> ressources;
	// private List<Action> actions;
	// private List<Snippet> snippetsList;
	private List<Visitable> elements = new ArrayList<>();
	private Binding binding;
	private String filePath ="";
	private Map<String, String> snippets = new HashMap<>();
	public GroovuinoMLModel(Binding binding) {
		// this.ressources = new ArrayList<Ressource>();
		// this.actions = new ArrayList<Action>();
		// this.snippetsList = new ArrayList<Snippet>();
		this.elements = new ArrayList<Visitable>();
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
		this.elements.add(textVideo);
	}
	public void createVideo(String name, String path, float duration) {
		Video video = new Video();
		video.setName(name);
		video.setPath(path);
		video.setDuration(duration);
		this.binding.setVariable(name, video);
		this.elements.add(video);
	}
	public void createText(String name, String content, String font, int x, int y) {
		Text text = new Text();
		text.setName(name);
		text.setContent(content);
		text.setFont(font);
		text.setPositionX(x);
		text.setPositionY(y);
		this.binding.setVariable(name, text);
		this.elements.add(text);
	}
	public void createSuperpose(Object video, Object text, float startTime, float duration, String name) {
		Superpose superpose = new Superpose();
		superpose.setName(name);

		if (video instanceof Video) {
			superpose.setSource((Video) video);
		} else if (video instanceof Action) {
			superpose.setSource(((Action) video).execute());
		} else {
			throw new IllegalArgumentException("First argument must be a Video");
		}

		if (text instanceof Text) {
			superpose.setTarget((Text) text);
		} else {
			throw new IllegalArgumentException("Second argument must be a Text");
		}

		superpose.setStartTime(startTime);
		superpose.setDuration(duration);

		this.elements.add(superpose);
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
		this.elements.add(after);
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
		this.elements.add(cut);
		this.binding.setVariable(name, cut);
		
	}

	public void createAudio(String name, String path, Object duration) {
		Audio audio = new Audio();
		audio.setName(name);
		audio.setPath(path);

		try {
			float parsedDuration;
			if (duration instanceof String) {
				parsedDuration = Float.parseFloat((String) duration);
			} else if (duration instanceof Integer) {
				parsedDuration = ((Integer) duration).floatValue();
			} else if (duration instanceof Float) {
				parsedDuration = (Float) duration;
			} else {
				throw new IllegalArgumentException("Duration must be a String, Integer, or Float");
			}
			audio.setDuration(parsedDuration);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Duration must be a valid number");
		}

		this.binding.setVariable(name, audio);
		this.elements.add(audio);
	}



	public void createSetAudio(Object audio, Object video, String name) {
		SetAudio setAudio = new SetAudio();
		setAudio.setName(name);

		// Gérer les actions pour `audio`
		if (audio instanceof Action) {
			audio = ((Action) audio).execute();
		}

		if (audio instanceof Audio) {
			setAudio.setSource((Ressource) audio);
		} else {
			throw new IllegalArgumentException("Audio source must be of type Audio");
		}

		// Gérer les actions pour `video`
		if (video instanceof Action) {
			video = ((Action) video).execute();
		}

		if (video instanceof Video) {
			setAudio.setTarget((Ressource) video);
		} else {
			throw new IllegalArgumentException("Video target must be of type Video");
		}

		this.elements.add(setAudio);
		this.binding.setVariable(name, setAudio);
	}


	public void createConcatAudio(Object audio1, Object audio2, String name) {
		ConcatAudio concatAudio = new ConcatAudio();
		concatAudio.setName(name);

		// Gérer les actions pour audio1
		if (audio1 instanceof Action) {
			audio1 = ((Action) audio1).execute();
		}

		// Gérer les actions pour audio2
		if (audio2 instanceof Action) {
			audio2 = ((Action) audio2).execute();
		}

		// Vérifiez que les deux sont de type Audio
		if (audio1 instanceof Audio) {
			concatAudio.setSource((Ressource) audio1);
		} else {
			throw new IllegalArgumentException("First argument must be of type Audio");
		}

		if (audio2 instanceof Audio) {
			concatAudio.setTarget((Ressource) audio2);
		} else {
			throw new IllegalArgumentException("Second argument must be of type Audio");
		}

		this.elements.add(concatAudio);
		this.binding.setVariable(name, concatAudio.execute());
	}



	public void createAdjustVolume(Object audio, Object volumeFactor, String name) {
		AdjustVolume adjustVolume = new AdjustVolume();
		adjustVolume.setName(name);

		// Gérer les actions pour `audio`
		if (audio instanceof Action) {
			audio = ((Action) audio).execute();
		}

		if (audio instanceof Audio) {
			adjustVolume.setTarget((Ressource) audio);
		} else {
			throw new IllegalArgumentException("Argument must be of type Audio");
		}

		try {
			float parsedVolume;
			if (volumeFactor instanceof String) {
				parsedVolume = Float.parseFloat((String) volumeFactor);
			} else if (volumeFactor instanceof BigDecimal) {
				parsedVolume = ((BigDecimal) volumeFactor).floatValue();
			} else if (volumeFactor instanceof Integer) {
				parsedVolume = ((Integer) volumeFactor).floatValue();
			} else if (volumeFactor instanceof Float) {
				parsedVolume = (Float) volumeFactor;
			} else {
				throw new IllegalArgumentException("Volume factor must be a String, BigDecimal, Integer, or Float");
			}
			adjustVolume.setVolumeFactor(parsedVolume);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Volume factor must be a valid number");
		}

		this.elements.add(adjustVolume);
		this.binding.setVariable(name, adjustVolume);
	}




	public void createAudioTransition(Object audio1, Object audio2, Object duration, String name) {
		AudioTransition audioTransition = new AudioTransition();
		audioTransition.setName(name);

		// Vérifiez si les arguments audio sont des actions
		if (audio1 instanceof Action) {
			audio1 = ((Action) audio1).execute();
		}
		if (audio2 instanceof Action) {
			audio2 = ((Action) audio2).execute();
		}

		// Validez les types audio
		if (audio1 instanceof Audio) {
			audioTransition.setSource((Ressource) audio1);
		} else {
			throw new IllegalArgumentException("First argument must be of type Audio");
		}

		if (audio2 instanceof Audio) {
			audioTransition.setTarget((Ressource) audio2);
		} else {
			throw new IllegalArgumentException("Second argument must be of type Audio");
		}

		// Convertissez duration en Float
		try {
			float parsedDuration;
			if (duration instanceof String) {
				parsedDuration = Float.parseFloat((String) duration);
			} else if (duration instanceof Integer) {
				parsedDuration = ((Integer) duration).floatValue();
			} else if (duration instanceof Float) {
				parsedDuration = (Float) duration;
			} else {
				throw new IllegalArgumentException("Duration must be a String, Integer, or Float");
			}
			audioTransition.setTransitionDuration(parsedDuration);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Transition duration must be a valid number");
		}

		// Ajoutez l'action
		this.elements.add(audioTransition);
		this.binding.setVariable(name, audioTransition);
	}

	public void createStack(Object overlay, Object main, float scale, String corner, String name) {
		Stack stack = new Stack();
		stack.setName(name);

		if (overlay instanceof Ressource) {
			stack.setTarget((Ressource) overlay);
		} else if (overlay instanceof Action) {
			stack.setTarget(((Action) overlay).execute());
		} else {
			throw new IllegalArgumentException("Overlay must be of type Ressource or Action");
		}

		if (main instanceof Ressource) {
			stack.setSource((Ressource) main);
		} else if (main instanceof Action) {
			stack.setSource(((Action) main).execute());
		} else {
			throw new IllegalArgumentException("Main video must be of type Ressource or Action");
		}

		stack.setScale(scale);

		if (corner.equals("TOP_LEFT"))
			stack.setCorner(Stack.Corner.TOP_LEFT);
		else if (corner.equals("TOP_RIGHT"))
			stack.setCorner(Stack.Corner.TOP_RIGHT);
		else if (corner.equals("BOTTOM_LEFT"))
			stack.setCorner(Stack.Corner.BOTTOM_LEFT);
		else if (corner.equals("BOTTOM_RIGHT"))
			stack.setCorner(Stack.Corner.BOTTOM_RIGHT);
		else
			throw new IllegalArgumentException("Corner must be one of: TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT");

		this.elements.add(stack);
		this.binding.setVariable(name, stack);
	}


 public void createFade(String name, Object target, float duration, String type, Object stack) {
		if (name == null || name.trim().isEmpty()) {
			throw new IllegalArgumentException("Fade name cannot be null or empty");
		}
		if (type == null || (!type.equals("IN") && !type.equals("OUT"))) {
			throw new IllegalArgumentException("Type must be either 'IN' or 'OUT', got: " + type);
		}

		Fade fade = new Fade();
		fade.setName(name);
		fade.setDuration(duration);
		fade.setType(type);

		if (target instanceof Ressource) {
			fade.setTarget((Ressource) target);
		} else if (target instanceof Action) {
			fade.setTarget(((Action) target).execute());
		} else {
			throw new IllegalArgumentException("Target must be of type Ressource or Action");
		}

		if (stack instanceof Stack) {
			fade.setStack((Stack) stack);
		} else if (stack != null) {
			throw new IllegalArgumentException("Stack must be of type Stack");
		}

		this.elements.add(fade);
		this.binding.setVariable(name, fade);
	}

	@SuppressWarnings("rawtypes")
	public Object generateCode(String appName) {
		App app = new App();
		app.setName(appName);
		// app.setActions(this.actions);
		// app.setRessources(this.ressources);
		// app.setSnippets(this.snippetsList);
		app.setElements(this.elements);
		Visitor codeGenerator = new ToWiring();
		app.accept(codeGenerator);


		return codeGenerator.getResult();
	}


	private static final String START_COMMENT_PREFIX = "#start";
    private static final String END_COMMENT = "#end";
    
    public static Map<String, String> extractCodeSnippets(String pythonFilePath) throws IOException {
        Map<String, String> snippets = new HashMap<>();
        BufferedReader reader = new BufferedReader(new FileReader(pythonFilePath));
        
        String currentLine;
        String currentSnippetId = null;
        List<String> currentSnippetLines = new ArrayList<>();
        boolean isCollectingSnippet = false;
        
        while ((currentLine = reader.readLine()) != null) {
            String trimmedLine = currentLine.trim();
            
            // Check for start of snippet
            if (trimmedLine.startsWith(START_COMMENT_PREFIX)) {
                isCollectingSnippet = true;
                currentSnippetId = trimmedLine.substring(START_COMMENT_PREFIX.length()).trim();
                continue;
            }
            
            // Check for end of snippet
            if (trimmedLine.equals(END_COMMENT) && isCollectingSnippet) {
                // Add newline after each line
                StringBuilder snippetBuilder = new StringBuilder();
                for (String line : currentSnippetLines) {
                    snippetBuilder.append(line).append("\n"); // Add newline after each line
                }
                snippets.put(currentSnippetId, snippetBuilder.toString());
                
                // Reset variables
                isCollectingSnippet = false;
                currentSnippetId = null;
                currentSnippetLines.clear();
                continue;
            }
            
            // Collect lines between start and end comments
            if (isCollectingSnippet) {
                currentSnippetLines.add(currentLine);
            }
        }
        
        reader.close();
        return snippets;
    }

	public void reference(String filePath){
		try {
            this.snippets = extractCodeSnippets(filePath);

        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
	}
	public void load(String name, List args) {
		Snippet snippet = new Snippet();
		snippet.setCode(this.snippets.get(name));
		snippet.setName(name);
		snippet.setArgs(args);
		this.elements.add(snippet);
		this.binding.setVariable(name, snippet);
	}

	public void export(String name, Object target, String filePath, int fps, String codec) {
		Export export = new Export();
		export.setName(name);
		export.setPathString(filePath);
		export.setFps(fps);
		export.setCodec(codec);
		if (target instanceof Ressource) {
			export.setTarget((Ressource) target);
		} else if (target instanceof Action) {
			export.setTarget(((Action) target).execute());
		} else {
			throw new IllegalArgumentException("Target must be of type Ressource or Action");
		}
		this.elements.add(export);
		this.binding.setVariable(name, export);
	}

};