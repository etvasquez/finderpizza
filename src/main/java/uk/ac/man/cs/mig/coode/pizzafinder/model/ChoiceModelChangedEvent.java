package uk.ac.man.cs.mig.coode.pizzafinder.model;

public class ChoiceModelChangedEvent {

	final private ChoiceModel source;

	public ChoiceModelChangedEvent(ChoiceModel source) {
		this.source = source;
	}


	public ChoiceModel getSource() {
		return source;
	}
}

