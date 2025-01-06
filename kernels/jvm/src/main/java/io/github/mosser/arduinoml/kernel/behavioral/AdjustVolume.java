package io.github.mosser.arduinoml.kernel.behavioral;

import io.github.mosser.arduinoml.kernel.structural.Audio;
import io.github.mosser.arduinoml.kernel.generator.Visitor;
import io.github.mosser.arduinoml.kernel.structural.Ressource;

public class AdjustVolume extends UnaryAction {
    private float volumeFactor;

    public float getVolumeFactor() {
        return volumeFactor;
    }

    public void setVolumeFactor(float volumeFactor) {
        if (volumeFactor < 0) {
            throw new IllegalArgumentException("Volume factor must be positive");
        }
        this.volumeFactor = volumeFactor;
    }

    @Override
    public Ressource execute() {
        if (!(target instanceof Audio)) {
            throw new IllegalArgumentException("Target must be of type Audio");
        }

        Audio audio = (Audio) target;
        try {
            Audio adjustedAudio = new Audio();
            adjustedAudio.setName(this.getName());
            adjustedAudio.setDuration(audio.getDuration());
            adjustedAudio.setVolume((int) (audio.getVolume() * volumeFactor));
            return adjustedAudio;
        } catch (Exception e) {
            throw new RuntimeException("Failed to adjust audio volume", e);
        }
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}

