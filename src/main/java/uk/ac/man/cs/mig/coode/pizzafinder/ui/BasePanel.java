package uk.ac.man.cs.mig.coode.pizzafinder.ui;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import uk.ac.man.cs.mig.coode.pizzafinder.model.PizzaOntology;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BasePanel extends JPanel {

    private PizzaOntology ontology;

    private OWLClass baseClass;

    private List<String> baseNames;

    private final String pizzaName;

    private Dimension prefSize;

    public static final int LINE_SPACING = 5;

    public static final int SEPARATOR_SPACING = 5;

    public static final int VERTICAL_OFFSET = 7;

    public static final int INGEREDIENT_FONT_SIZE = 10;

    public static final int PIZZA_NAME_FONT_SIZE = 17;

    public static final Font PIZZA_NAME_FONT = new Font("SansSerif", Font.BOLD, PIZZA_NAME_FONT_SIZE);

    public static final Font INGREDIENT_FONT = new Font("SansSerif", Font.PLAIN, INGEREDIENT_FONT_SIZE);

    private ImageIcon spicyIcon = null;

    private ImageIcon vegIcon = null;

    public BasePanel(PizzaOntology ontology, OWLClass baseClass) {
        this.ontology = ontology;
        this.baseClass = baseClass;
        this.baseNames = new ArrayList<String>();
        for (OWLClass o : getBases()) {
            baseNames.add(ontology.render1(o));
        }
        pizzaName = ontology.render1(baseClass);
        if (ontology.isBasePizza(baseClass)) {
            vegIcon = Icons.getPizzaSliceIcon();
        }
        int width = 300;
        int height = 30;
        height += VERTICAL_OFFSET;
        height += pointsToPixels(PIZZA_NAME_FONT_SIZE);
        height += SEPARATOR_SPACING * 2 + 1;
        height += (baseNames.size() + 1) * (pointsToPixels(INGEREDIENT_FONT_SIZE) + LINE_SPACING);
        prefSize = new Dimension(width, height);
    }

    protected int pointsToPixels(int points) {
        return (int) (((points / 72.0) * getToolkit().getScreenResolution()));
    }

    public Dimension getPreferredSize() {
        return prefSize;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int xPos = 20;
        int yPos = VERTICAL_OFFSET;
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        Shape clip = g2.getClip();
        g2.setColor(Color.WHITE);
        g2.fill(clip);
        g2.setColor(getForeground());
        g2.setFont(PIZZA_NAME_FONT);
        yPos += g2.getFontMetrics().getHeight();
        g2.drawString(pizzaName, xPos, yPos);
        int iconOffset = 0;
        iconOffset = xPos + 10 + g2.getFont().getStringBounds(pizzaName, g2.getFontRenderContext()).getBounds().width;
        if (vegIcon != null) {
            g2.drawImage(vegIcon.getImage(), iconOffset, yPos - vegIcon.getIconHeight(), this);
            iconOffset += vegIcon.getIconWidth();
        }
        if (spicyIcon != null) {
            g2.drawImage(spicyIcon.getImage(), iconOffset, yPos - spicyIcon.getIconHeight(), this);
        }
        yPos += SEPARATOR_SPACING;
        g2.drawLine(xPos, yPos, 300, yPos);
        xPos += 30;
        yPos += SEPARATOR_SPACING + 1;
        g2.setFont(INGREDIENT_FONT);
        for (String toppingName : baseNames) {
            yPos += g2.getFontMetrics().getHeight() + LINE_SPACING;
            g2.drawString(toppingName, xPos, yPos);
        }
    }

    /**
     * gets Toppings of the Named pizza (OWLClass pizzaClass),e.g.  for Margherita Pizza: Mozzarella topping and Tomato topping
     *
     * @return
     */
    protected Collection<OWLClass> getBases() {
        final java.util.List<OWLClass> toppingClasses = new ArrayList<OWLClass>();
        final OWLObjectProperty toppingProperty = ontology.getBaseProperty();     //e.g. has_topping property
        OWLClass superPizzaClass = null;
        // get topping from super calss e.g. SmallAmerican toppings are American Topping, if no super class e.g American pizza toppings are directly from AmericanPizza
        for (OWLClassExpression superCls : baseClass.getSuperClasses(ontology.getOntology())) {
            if (superCls instanceof OWLClass)
                if (ontology.isNamedPizza((OWLClass) superCls)) superPizzaClass = (OWLClass) superCls;
            //if no super class e.g American pizza toppings are directly from AmericanPizza
            if (superPizzaClass == null && (ontology.isNamedPizza((OWLClass) baseClass))) {
                superPizzaClass = baseClass;
            }
            if (superPizzaClass != null) {
                for (OWLClassExpression curSuperCls : superPizzaClass.getSuperClasses(ontology.getOntology())) {
                    if (curSuperCls instanceof OWLObjectSomeValuesFrom) {
                        OWLObjectSomeValuesFrom someValuesFrom = (OWLObjectSomeValuesFrom) curSuperCls;
                        if (someValuesFrom.getProperty().equals(toppingProperty)) {
                            OWLClassExpression filler = someValuesFrom.getFiller();
                            if (filler instanceof OWLClass) {
                                if (!toppingClasses.contains(filler))
                                    toppingClasses.add((OWLClass) filler);
                            }
                        }
                    }
                }
            }
        }
        return toppingClasses;
    }

    public String toString() {
        return "PizzaPanel(" + pizzaName + ")";
    }
}
