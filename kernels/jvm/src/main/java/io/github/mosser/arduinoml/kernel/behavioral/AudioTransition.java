package io.github.mosser.arduinoml.kernel.behavioral;

import io.github.mosser.arduinoml.kernel.structural.Audio;
import io.github.mosser.arduinoml.kernel.generator.Visitor;
import io.github.mosser.arduinoml.kernel.structural.Ressource;

public class AudioTransition extends BinaryAction {
    private float transitionDuration;

    public float getTransitionDuration() {
        return transitionDuration;
    }

    public void setTransitionDuration(float transitionDuration) {
        if (transitionDuration < 0) {
            throw new IllegalArgumentException("Transition duration cannot be negative");
        }
        this.transitionDuration = transitionDuration;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public Ressource execute() {
        if (!(source instanceof Audio) || !(target instanceof Audio)) {
            throw new IllegalArgumentException("AudioTransition can only be applied to Audio resources");
        }

        Audio audio1 = (Audio) source;
        Audio audio2 = (Audio) target;

        if (transitionDuration > Math.min(audio1.getDuration(), audio2.getDuration())) {
            throw new IllegalArgumentException("Transition duration exceeds the duration of one of the audio tracks");
        }

        try {
            Audio transitionedAudio = new Audio();
            transitionedAudio.setName(this.getName());
            transitionedAudio.setDuration(audio1.getDuration() + audio2.getDuration() - transitionDuration);
            return transitionedAudio;
        } catch (Exception e) {
            throw new RuntimeException("Failed to create transitioned audio", e);
        }
    }
}
