package uk.ac.man.cs.mig.coode.pizzafinder.ui;

import org.semanticweb.owlapi.model.OWLClass;
import uk.ac.man.cs.mig.coode.pizzafinder.model.ChoiceModel;
import uk.ac.man.cs.mig.coode.pizzafinder.model.PizzaOntology;
import uk.ac.man.cs.mig.coode.pizzafinder.selection.Selectable;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Collection;

public class ExcludeBaseListPanel extends BaseListPanel {
    public static final String TITLE = "Excluded bases:";

    private ChoiceModel choiceModel;

    private Selectable selectable;

    private Action addAction = new AbstractAction("Add") {
        public void actionPerformed(ActionEvent e) {
            Object selObj = selectable.getSelection();
            if (selObj != null && selObj instanceof OWLClass) {
                choiceModel.addExcluded((OWLClass) selObj);
            }
        }
    };

    private Action removeAction = new AbstractAction("Rem") {
        public void actionPerformed(ActionEvent e) {
            removeExcluded();
        }
    };

    public ExcludeBaseListPanel(final PizzaOntology ontology,
                                   final Selectable selectable,
                                   final ChoiceModel choiceModel) {
        super(ontology, TITLE, selectable, choiceModel);
        this.selectable = selectable;
        this.choiceModel = choiceModel;
        createUI();
    }

    protected Collection getListItems() {
        return choiceModel.getExcluded();
    }

    private void removeExcluded() {
        Object selObj = getSelection();
        if (selObj != null && selObj instanceof OWLClass) {
            choiceModel.removeExcluded((OWLClass) selObj);
        }
    }

    protected Action getAddAction() {
        return addAction;
    }

    protected Action getRemoveAction() {
        return removeAction;
    }
}
