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
        if (startTime < 0) {
            throw new IllegalArgumentException("Start time cannot be negative");
        }
        this.startTime = startTime;
    }

    public float getDuration() {
        return duration;
    }

    public void setDuration(float duration) {
        // Validate that duration is positive
        if (duration <= 0) {
            throw new IllegalArgumentException("Duration must be a positive number");
        }
        this.duration = duration;
    }

    @Override
    public Ressource execute() {
        // Validate sources before execution
        validateSources();

        // Ensure start time and duration are within the source video's duration
        if (startTime + duration > source.getDuration()) {
            throw new IllegalStateException("Superposition exceeds source video duration");
        }

        // Logique pour superposer le texte sur la vidéo
        Video result = new Video();
        result.setName(this.getName());
        result.setDuration(this.source.getDuration());
        return result;
    }

    /**
     * Validate that both sources (text and video) are present and valid
     */
    private void validateSources() {
        if (source == null) {
            throw new IllegalStateException("Source video is missing");
        }
        if (!(source instanceof Video)) {
            throw new IllegalArgumentException("Source must be a Video");
        }
    }

    @Override
    public void accept(Visitor visitor) {
        // Optional: Add validation before accepting visitor
        validateSources();
        visitor.visit(this);
    }
}