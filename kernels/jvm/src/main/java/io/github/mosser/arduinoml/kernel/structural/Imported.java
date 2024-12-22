package io.github.mosser.arduinoml.kernel.structural;

public abstract class Imported extends Ressource {
    protected String path;
    protected int volume;

    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }
}
