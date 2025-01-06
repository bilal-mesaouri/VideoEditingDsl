package io.github.mosser.arduinoml.kernel.behavioral;

import io.github.mosser.arduinoml.kernel.structural.Audio;
import io.github.mosser.arduinoml.kernel.generator.Visitor;
import io.github.mosser.arduinoml.kernel.structural.Ressource;

public class ConcatAudio extends BinaryAction {

    @Override
    public Ressource execute() {
        if (!(source instanceof Audio) || !(target instanceof Audio)) {
            throw new IllegalArgumentException("Both source and target must be of type Audio");
        }

        Audio audioSource = (Audio) source;
        Audio audioTarget = (Audio) target;

        try {
            Audio combinedAudio = new Audio();
            combinedAudio.setName(this.getName());
            combinedAudio.setDuration(audioSource.getDuration() + audioTarget.getDuration());
            return combinedAudio;
        } catch (Exception e) {
            throw new RuntimeException("Failed to concatenate audio tracks", e);
        }
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
