package uk.ac.man.cs.mig.coode.pizzafinder.selection;

public class SelectionEvent {
	private Object source;


	public SelectionEvent(Object source) {
		this.source = source;
	}


	public Object getSource() {
		return source;
	}
}

