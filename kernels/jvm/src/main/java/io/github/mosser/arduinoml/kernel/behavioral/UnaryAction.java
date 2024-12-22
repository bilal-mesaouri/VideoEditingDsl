package io.github.mosser.arduinoml.kernel.behavioral;

import io.github.mosser.arduinoml.kernel.structural.Ressource;

public abstract class UnaryAction extends Action {
    protected Ressource target;

    public Ressource getTarget() {
        return target;
    }

    public void setTarget(Ressource target) {
        this.target = target;
    }
}
