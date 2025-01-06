package io.github.mosser.arduinoml.kernel.behavioral;

import io.github.mosser.arduinoml.kernel.structural.Audio;
import io.github.mosser.arduinoml.kernel.structural.Ressource;
import io.github.mosser.arduinoml.kernel.structural.Video;
import io.github.mosser.arduinoml.kernel.generator.Visitor;

import java.lang.reflect.InvocationTargetException;

public class SetAudio extends BinaryAction {
    @Override
    public Ressource execute() {
        if (!(source instanceof Audio) || !(target instanceof Video)) {
            throw new IllegalArgumentException("Source must be of type Audio and Target must be of type Video");
        }

        Audio audio = (Audio) source;
        Video video = (Video) target;

        // Vérifier si les durées de l'audio et de la vidéo sont compatibles
        if (audio.getDuration() > video.getDuration()) {
            throw new IllegalArgumentException("Audio duration cannot exceed Video duration");
        }

        try {
            // Créer une nouvelle instance de vidéo combinée
            Video newVideo = video.getClass().getDeclaredConstructor().newInstance();
            newVideo.setName(this.getName());
            newVideo.setPath(video.getPath()); // Garder le chemin de la vidéo originale
            newVideo.setDuration(video.getDuration());
            newVideo.setVolume(audio.getVolume()); // Synchroniser le volume
            return newVideo;
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException("Failed to create a new instance of the video", e);
        }
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
