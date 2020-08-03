package uk.ac.man.cs.mig.coode.pizzafinder.model;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

import java.util.*;

public class ChoiceModel {

    private final PizzaOntology ontology;

    private Set<OWLClass> included = new TreeSet<OWLClass>();

    private Set<OWLClass> excluded = new TreeSet<OWLClass>();

    //private Set<OWLClass> includedB = new TreeSet<OWLClass>();

    // Set<OWLClass> excludedB = new TreeSet<OWLClass>();

    private List<ChoiceModelListener> listeners = new ArrayList<ChoiceModelListener>();

    public ChoiceModel(PizzaOntology ontology) {
        this.ontology = ontology;
    }

    public Set<OWLClass> getIncluded() {
        return new TreeSet<OWLClass>(included);
    }

    public Set<OWLClass> getExcluded() {
        return new TreeSet<OWLClass>(excluded);
    }

    /*public Set<OWLClass> getExcludedB() {
        return new TreeSet<OWLClass>(excludedB);
    }

    public Set<OWLClass> getIncludedB() {
        return new TreeSet<OWLClass>(includedB);
    }*/

    /**
     * Adds the specified class to the list of included
     * classes. This will remove any descendants of the
     * class from the included list (as they are subsumed by
     * the specified class).  It also remove any ancestor classes
     * of the specified class (as they have been replaced by something
     * more specific) and also removes the class and
     * its ancestor classes from the list of excluded list.
     *
     * @param cls The class to add
     */
    public void addIncluded(OWLClass cls) {
        OWLReasoner reasoner = getReasoner();
        boolean changed = included.add(cls);
        changed |= included.removeAll(reasoner.getSubClasses(cls, false).getFlattened());
        changed |= included.removeAll(reasoner.getSuperClasses(cls, false).getFlattened());
        changed |= excluded.remove(cls);
        changed |= excluded.removeAll(reasoner.getSuperClasses(cls, false).getFlattened());
        if (changed) {
            fireModelChangedEvent();
        }
    }

    /**
     * Removes the specified class from the list of
     * included classes.
     *
     * @param cls The class to be removed.
     */
    public void removeIncluded(OWLClass cls) {
        if (included.remove(cls)) {
            fireModelChangedEvent();
        }
    }

    /**
     * Adds the specified class to the list of excluded
     * classes. This will remove any descendants of the
     * class from the excluded list.  This will also
     * remove the class and its descendants from the list
     * of included classes.
     *
     * @param cls
     */
    public void addExcluded(OWLClass cls) {
        OWLReasoner reasoner = getReasoner();
        boolean changed = excluded.add(cls);
        changed |= excluded.removeAll(reasoner.getSuperClasses(cls, false).getFlattened());
        changed |= excluded.removeAll(reasoner.getSubClasses(cls, false).getFlattened());
        changed |= included.remove(cls);
        changed |= included.removeAll(reasoner.getSubClasses(cls, false).getFlattened());
        if (changed) {
            fireModelChangedEvent();
        }
    }

    public void removeExcluded(OWLClass cls) {
        if (excluded.remove(cls)) {
            fireModelChangedEvent();
        }
    }

    public void addChoiceModelListener(ChoiceModelListener lsnr) {
        listeners.add(lsnr);
    }

    public void removeChoiceModelListener(ChoiceModelListener lsnr) {
        listeners.remove(lsnr);
    }

    protected void fireModelChangedEvent() {
        for(ChoiceModelListener listener : new ArrayList<ChoiceModelListener>(listeners)) {
            listener.modelChanged(new ChoiceModelChangedEvent(this));
        }
    }

    private OWLReasoner getReasoner() {
        return ontology.getReasoner();
    }
}

