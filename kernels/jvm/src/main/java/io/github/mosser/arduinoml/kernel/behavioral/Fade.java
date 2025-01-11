package io.github.mosser.arduinoml.kernel.behavioral;

import io.github.mosser.arduinoml.kernel.generator.Visitor;
import io.github.mosser.arduinoml.kernel.structural.Ressource;
import io.github.mosser.arduinoml.kernel.structural.Video;
public class Fade extends UnaryAction {
    private float duration;
    private String type;
    private Stack stack;

    public float getDuration() {
        return duration;
    }

    public void setDuration(float duration) {
        if (duration <= 0) {
            throw new IllegalArgumentException("Duration must be positive, got: " + duration);
        }
        this.duration = duration;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        if (type == null) {
            throw new IllegalArgumentException("Fade type cannot be null");
        }
        if (!type.equals("IN") && !type.equals("OUT")) {
            throw new IllegalArgumentException("Fade type must be either 'IN' or 'OUT', got: " + type);
        }
        this.type = type;
    }

    public Stack getStack() {
        return stack;
    }

    public void setStack(Stack stack) {
        if (stack != null && stack.getTarget() == null) {
            throw new IllegalArgumentException("Stack must have a target video");
        }
        this.stack = stack;
    }

    @Override
    public void setTarget(Ressource target) {
        if (!(target instanceof Video)) {
            throw new IllegalArgumentException("Fade target must be a Video, got: " +
                    (target != null ? target.getClass().getSimpleName() : "null"));
        }
        super.setTarget(target);
    }

    public boolean isFadeIn() {
        return "IN".equals(type);
    }

    @Override
    public Ressource execute() {
        if (target == null) {
            throw new IllegalStateException("Cannot execute fade: target is null");
        }
        if (type == null) {
            throw new IllegalStateException("Cannot execute fade: type is not set");
        }
        if (duration <= 0) {
            throw new IllegalStateException("Cannot execute fade: invalid duration");
        }
        return this.target;
    }

    @Override
    public void accept(Visitor visitor) {
        if (visitor == null) {
            throw new IllegalArgumentException("Visitor cannot be null");
        }
        visitor.visit(this);
    }
}
