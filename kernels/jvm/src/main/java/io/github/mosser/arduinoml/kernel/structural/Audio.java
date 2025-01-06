package io.github.mosser.arduinoml.kernel.structural;


import io.github.mosser.arduinoml.kernel.generator.Visitor;

public class Audio extends Imported {
    private String format;

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}

