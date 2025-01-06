package io.github.mosser.arduinoml.kernel.behavioral;

import java.lang.reflect.InvocationTargetException;

import io.github.mosser.arduinoml.kernel.generator.Visitor;
import io.github.mosser.arduinoml.kernel.structural.Ressource;
import io.github.mosser.arduinoml.kernel.structural.TextVideo;
import io.github.mosser.arduinoml.kernel.structural.Video;

public class After extends BinaryAction{

    @Override
    public Ressource execute() {
        boolean isSourcePlayable = isPlayableResource(this.source);
        boolean isTargetPlayable = isPlayableResource(this.target);

        if (isSourcePlayable && isTargetPlayable) {
            try {
                // On crée toujours une Video comme résultat
                Video newRessource = new Video();
                newRessource.setName(this.getName());
                newRessource.setDuration(this.source.getDuration() + this.target.getDuration());
                return newRessource;
            } catch (Exception e) {
                throw new RuntimeException("Failed to create a new instance of the resource", e);
            }
        } else {
            throw new IllegalArgumentException("Resources must be Video or TextVideo for concatenation");
        }
    }

    private boolean isPlayableResource(Ressource resource) {
        return (resource instanceof Video || resource instanceof TextVideo);
    }
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
    
}
