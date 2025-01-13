package io.github.mosser.arduinoml.kernel;

import io.github.mosser.arduinoml.kernel.behavioral.Action;
import io.github.mosser.arduinoml.kernel.generator.Visitable;
import io.github.mosser.arduinoml.kernel.generator.Visitor;
import io.github.mosser.arduinoml.kernel.structural.Ressource;
import io.github.mosser.arduinoml.kernel.structural.Snippet;

import java.util.ArrayList;
import java.util.List;

public class App implements NamedElement, Visitable {

    private String name;
    private List<Visitable> elements = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Visitable> getElements() {
        return elements;
    }

    public void setElements(List<Visitable> elements) {
        this.elements = elements;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
