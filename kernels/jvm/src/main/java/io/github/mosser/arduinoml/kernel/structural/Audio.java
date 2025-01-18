package io.github.mosser.arduinoml.kernel.structural;


import io.github.mosser.arduinoml.kernel.generator.Visitor;

public class Audio extends Imported {
    private String format;
    private String codec;
    private int channels;

    public String getCodec() {
        return codec;
    }

    public void setCodec(String codec) {
        this.codec = codec;
    }

    public int getChannels() {
        return channels;
    }

    public void setChannels(int channels) {
        this.channels = channels;
    }

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

