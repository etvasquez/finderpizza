package uk.ac.man.cs.mig.coode.pizzafinder.selection;

public interface Selectable {
	public Object getSelection();
	public void setSelection(Object obj);
	public void addSelectionListener(SelectionListener lsnr);
	public void removeSelectionListener(SelectionListener lsnr);
}
