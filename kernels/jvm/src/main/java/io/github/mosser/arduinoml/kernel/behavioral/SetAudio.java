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
            throw new IllegalArgumentException("Source must be Audio and Target must be Video");
        }

        Audio audio = (Audio) source;
        Video video = (Video) target;

        if (audio.getDuration() > video.getDuration()) {
            throw new IllegalArgumentException("Audio duration cannot exceed Video duration");
        }

        try {
            Video syncedVideo = new Video();
            syncedVideo.setName(this.getName());
            syncedVideo.setPath(video.getPath());
            syncedVideo.setDuration(video.getDuration());
            syncedVideo.setVolume(audio.getVolume()); // Synchronisation audio
            return syncedVideo;
        } catch (Exception e) {
            throw new RuntimeException("Failed to synchronize audio with video", e);
        }
    }


    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
