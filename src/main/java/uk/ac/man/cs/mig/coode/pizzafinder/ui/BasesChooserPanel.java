package uk.ac.man.cs.mig.coode.pizzafinder.ui;

import uk.ac.man.cs.mig.coode.pizzafinder.model.ChoiceModel;
import uk.ac.man.cs.mig.coode.pizzafinder.model.PizzaOntology;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Collection;

public class BasesChooserPanel extends JPanel{
    private PizzaOntology ontology;

    private ChoiceModel choiceModel;

    private BasesPanel basesPanel;

    private BaseListPanel includeListPanel;

    private BaseListPanel excludeListPanel;

    private PizzaApplication application;

    public BasesChooserPanel(PizzaOntology ontology, PizzaApplication application) {
        this.ontology = ontology;
        this.application = application;
        choiceModel = new ChoiceModel(ontology);
        createUI();
    }

    protected void createUI() {
        setLayout(new BorderLayout());

        //add size Panel here

        JSplitPane splitPane = new JSplitPane();
        splitPane.setDividerLocation(200);
        basesPanel = new BasesPanel(ontology);
        splitPane.setLeftComponent(basesPanel);
        Box box = new Box(BoxLayout.Y_AXIS);
        includeListPanel = new IncludeBaseListPanel(ontology, basesPanel, choiceModel);
        box.add(includeListPanel);

        box.add(Box.createVerticalStrut(12));
        excludeListPanel = new ExcludeBaseListPanel(ontology, basesPanel, choiceModel);
        box.add(excludeListPanel);
        box.setBorder(BorderFactory.createEmptyBorder(0, 0, 12, 12));
        splitPane.setRightComponent(box);
        add(splitPane);
        setupQueryPanel();
    }


    public Dimension getPreferredSize() {
        return new Dimension(800, 600);
    }


    protected void setupQueryPanel() {
        JPanel queryPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        queryPanel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        Action queryReasonerAction = new AbstractAction("Get Pizzas") {
            public void actionPerformed(ActionEvent e) {
                Collection c = ontology.getBases(choiceModel.getIncluded(),choiceModel.getExcluded());
                application.showResultsBasePanel(c);
            }
        };
        queryPanel.add(new JButton(queryReasonerAction));
        add(queryPanel, BorderLayout.SOUTH);
    }

}
