package io.github.mosser.arduinoml.kernel.behavioral;

import io.github.mosser.arduinoml.kernel.generator.Visitor;
import io.github.mosser.arduinoml.kernel.structural.Ressource;
import io.github.mosser.arduinoml.kernel.structural.Video;

public class Stack extends BinaryAction {
    public enum Corner {
        TOP_LEFT(20, 20),
        TOP_RIGHT(1260, 20),
        BOTTOM_LEFT(20, 580),
        BOTTOM_RIGHT(1260, 580);

        private final int x;
        private final int y;

        Corner(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() { return x; }
        public int getY() { return y; }
    }

    private Corner corner;
    private float scale;

    public Corner getCorner() {
        return corner;
    }

    public void setCorner(Corner corner) {
        // Validate that corner is not null
        if (corner == null) {
            throw new IllegalArgumentException("Corner cannot be null");
        }
        this.corner = corner;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        // Validate scale (must be positive and within a reasonable range)
        if (scale <= 0) {
            throw new IllegalArgumentException("Scale must be a positive number");
        }
        if (scale > 10) {
            throw new IllegalArgumentException("Scale cannot exceed 10");
        }
        this.scale = scale;
    }

    @Override
    public Ressource execute() {
        // Validate sources before execution
        validateSources();

        // Create result video with maximum duration
        Video result = new Video();
        result.setName(this.getName());
        result.setDuration(Math.max(this.source.getDuration(), this.target.getDuration()));
        return result;
    }

    /**
     * Validate that both sources are present and are Videos
     */
    private void validateSources() {
        // Check source video
        if (source == null) {
            throw new IllegalStateException("Source video is missing");
        }
        if (!(source instanceof Video)) {
            throw new IllegalArgumentException("Source must be a Video");
        }

        // Check target video
        if (target == null) {
            throw new IllegalStateException("Target video is missing");
        }
        if (!(target instanceof Video)) {
            throw new IllegalArgumentException("Target must be a Video");
        }


        if (corner == null) {
            throw new IllegalStateException("Corner position must be set before execution");
        }
    }

    @Override
    public void accept(Visitor visitor) {

        validateSources();
        visitor.visit(this);
    }
}