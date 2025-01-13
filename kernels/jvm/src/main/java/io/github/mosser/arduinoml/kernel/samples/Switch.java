package io.github.mosser.arduinoml.kernel.samples;

import io.github.mosser.arduinoml.kernel.App;
import io.github.mosser.arduinoml.kernel.behavioral.*;
import io.github.mosser.arduinoml.kernel.generator.ToWiring;
import io.github.mosser.arduinoml.kernel.generator.Visitor;
import io.github.mosser.arduinoml.kernel.structural.*;

import java.util.Arrays;

public class Switch {

	public static void main(String[] args) {
		//Declaring Ressources
		// Video videoA = new Video();
		// videoA.setName("VideoA");
		// videoA.setPath("videoA.mp4");
		// videoA.setDuration(10);
		// Video videoB = new Video();
		// videoB.setName("VideoB");
		// videoB.setPath("videoB.mp4");
		// videoB.setDuration(10);
		// Video videoD = new Video();
		// videoD.setName("VideoD");
		// videoD.setPath("videoD.mp4");
		// videoD.setDuration(10);


		// //Declaring Actions
		// After after = new After();
		// after.setName("VideoC");
		// after.setSource(videoA);
		// after.setTarget(videoB);

		// After after2 = new After();
		// after2.setName("videoE");
		// Ressource vid = after.execute();
		// System.out.println(vid.getName());
		// System.out.println(vid instanceof Ressource);
		// after2.setSource(vid);
		// System.out.println(after2.getSource().getName());
		// after2.setTarget(videoD);







		// // Building the App
		// App theSwitch = new App();
		// theSwitch.setName("Switch!");
		// theSwitch.setRessources(Arrays.asList(videoA, videoB, videoD));
		// theSwitch.setActions(Arrays.asList(after, after2));

		// // Generating Code
		// Visitor codeGenerator = new ToWiring();
		// theSwitch.accept(codeGenerator);

		// // Printing the generated code on the console
		// System.out.println(codeGenerator.getResult());
	}

}
