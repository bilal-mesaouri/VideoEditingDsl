package io.github.mosser.arduinoml.kernel.behavioral;

import java.lang.reflect.InvocationTargetException;

import io.github.mosser.arduinoml.kernel.generator.Visitor;
import io.github.mosser.arduinoml.kernel.structural.Ressource;

public class Cut extends UnaryAction {
    private float startTime = 0;
    private float endTime ;

    public float getStartTime() {
        return startTime;
    }

    public void setStartTime(float startTime) {
        if( startTime<0){
            throw new IllegalArgumentException("Start time cannot be negative");
        }
        this.startTime = startTime;
    }

    public float getEndTime() {
        return endTime;
    }

    public void setEndTime(float endTime) {
        if (endTime < startTime) {
            throw new IllegalArgumentException("End time cannot be earlier than start time");
        }

        else if (endTime > this.getTarget().getDuration()) {
            throw new IllegalArgumentException("end time too long");
        }
        this.endTime = endTime;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public Ressource execute() {
            try {
                // Create a new instance of the same class
                Ressource newRessource = this.target.getClass().getDeclaredConstructor().newInstance();
                newRessource.setName(this.getName());
                newRessource.setDuration(endTime-startTime);
                return newRessource;
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                throw new RuntimeException("Failed to create a new instance of the resource", e);
            }
        
    }

}
