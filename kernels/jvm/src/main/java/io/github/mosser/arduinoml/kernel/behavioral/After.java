package io.github.mosser.arduinoml.kernel.behavioral;

import java.lang.reflect.InvocationTargetException;

import io.github.mosser.arduinoml.kernel.generator.Visitor;
import io.github.mosser.arduinoml.kernel.structural.Ressource;

public class After extends BinaryAction{

    @Override
    public Ressource execute() {
        if (this.source.getClass() == this.target.getClass()) {
            try {
                // Create a new instance of the same class
                Ressource newRessource = this.source.getClass().getDeclaredConstructor().newInstance();
                newRessource.setName(this.getName());
                newRessource.setDuration(this.source.getDuration() + this.target.getDuration());
                return newRessource;
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                throw new RuntimeException("Failed to create a new instance of the resource", e);
            }
        } else {
            throw new IllegalArgumentException("Resources are not of the same type");
        }
    }
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
    
}
