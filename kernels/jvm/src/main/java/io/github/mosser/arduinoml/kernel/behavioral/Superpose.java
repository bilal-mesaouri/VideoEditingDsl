package io.github.mosser.arduinoml.kernel.behavioral;

import io.github.mosser.arduinoml.kernel.generator.Visitor;
import io.github.mosser.arduinoml.kernel.structural.Ressource;
import io.github.mosser.arduinoml.kernel.structural.Text;
import io.github.mosser.arduinoml.kernel.structural.Video;

public class Superpose extends BinaryAction {

    private float startTime = 0;
    private float duration;

    public float getStartTime() {
        return startTime;
    }

    public void setStartTime(float startTime) {
        this.startTime = startTime;
    }

    public float getDuration() {
        return duration;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }

    @Override
    public Ressource execute() {
        // Logique pour superposer le texte sur la vidéo
        Video result = new Video();
        result.setName(this.getName());
        result.setDuration(this.source.getDuration());
        return result;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}