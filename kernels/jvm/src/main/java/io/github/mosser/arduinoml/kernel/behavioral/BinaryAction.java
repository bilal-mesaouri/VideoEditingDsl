package io.github.mosser.arduinoml.kernel.behavioral;

import io.github.mosser.arduinoml.kernel.structural.Ressource;

public abstract class BinaryAction extends Action {
    protected Ressource target;
    protected Ressource source;

    public Ressource getTarget() {
        return target;
    }

    public void setTarget(Ressource target) {
        this.target = target;
    }

    public Ressource getSource() {
        return source;
    }

    public void setSource(Ressource source) {
        this.source = source;
    }
    
}
