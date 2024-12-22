package io.github.mosser.arduinoml.kernel;

import io.github.mosser.arduinoml.kernel.behavioral.Action;
import io.github.mosser.arduinoml.kernel.generator.Visitable;
import io.github.mosser.arduinoml.kernel.generator.Visitor;
import io.github.mosser.arduinoml.kernel.structural.Ressource;

import java.util.ArrayList;
import java.util.List;

public class App implements NamedElement, Visitable {

    private String name;
    private List<Action> actions = new ArrayList<Action>();
    private List<Ressource> ressources = new ArrayList<Ressource>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Action> getActions() {
        return actions;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }

    public List<Ressource> getRessources() {
        return ressources;
    }

    public void setRessources(List<Ressource> ressources) {
        this.ressources = ressources;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
