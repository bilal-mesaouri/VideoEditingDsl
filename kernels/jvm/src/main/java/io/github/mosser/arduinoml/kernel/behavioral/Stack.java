package io.github.mosser.arduinoml.kernel.behavioral;

import io.github.mosser.arduinoml.kernel.generator.Visitor;
import io.github.mosser.arduinoml.kernel.structural.Ressource;
import io.github.mosser.arduinoml.kernel.structural.Video;

public class Stack extends BinaryAction {
    public enum Corner {
        TOP_LEFT(20, 20),
        TOP_RIGHT(820, 20),
        BOTTOM_LEFT(20, 460),
        BOTTOM_RIGHT(820, 460);

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
    private float scale;  // échelle de la vidéo en overlay (ex: 0.3 pour 30%)

    public Corner getCorner() {
        return corner;
    }

    public void setCorner(Corner corner) {
        this.corner = corner;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    @Override
    public Ressource execute() {
        Video result = new Video();
        result.setName(this.getName());
        result.setDuration(Math.max(this.source.getDuration(), this.target.getDuration()));
        return result;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
