package io.github.mosser.arduinoml.kernel.structural;

import io.github.mosser.arduinoml.kernel.NamedElement;
import io.github.mosser.arduinoml.kernel.generator.Visitable;
import io.github.mosser.arduinoml.kernel.generator.Visitor;

public class Snippet implements NamedElement, Visitable {

	private String name;
    private String code;

	public Snippet() {

	}

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

}