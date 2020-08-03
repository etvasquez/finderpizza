package uk.ac.man.cs.mig.coode.pizzafinder.model;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.util.AnnotationValueShortFormProvider;
import org.semanticweb.owlapi.util.ShortFormProvider;
import uk.ac.manchester.cs.jfact.JFactFactory;

import javax.swing.*;
import java.util.*;

public class PizzaOntology {

    public static final Preferences PREFERENCES;

    public static final String TOPPING_SUFFIX = "Topping";

    public static final String BASE_SUFFIX = "Base";

    private OWLReasoner reasoner;

    private OWLOntology ontology;

    private OWLDataFactory df;

    private OWLOntologyManager manager;

    private ShortFormProvider sfp;

    static {
        PREFERENCES = Preferences.getInstance();
    }

    public PizzaOntology() {
        loadOntology();
        setupReasoner();
        setupShortFormProvider();
    }


    protected void loadOntology() {
        try {
            manager = OWLManager.createOWLOntologyManager();
            df = manager.getOWLDataFactory();
            ontology = manager.loadOntologyFromOntologyDocument(PREFERENCES.getOntologyDocumentIRI());
        } catch (final Throwable e) {
            Runnable runnable = new Runnable() {
                public void run() {
                    JOptionPane.showMessageDialog(null,
                            "Could not create the ontology.  (This probably happened\n" +
                                    "because the ontology could not be accessed due to network\n" +
                                    "problems.)\n" +
                                    "[" + e.getMessage() + "]",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    System.exit(1);
                }
            };
            SwingUtilities.invokeLater(runnable);
        }
    }

    public OWLOntology getOntology() {
        return ontology;
    }

    /**
     * setup fact++ reasoner
     */
    protected void setupReasoner() {
        // run reasoner
        try {
            JFactFactory factory = new JFactFactory();
            reasoner = factory.createReasoner(ontology);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "A reasoner error has ocurred.\n" +
                            "[" + e.getMessage() + "]",
                    "Reasoner Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void setupShortFormProvider() {
        final Map<OWLAnnotationProperty, List<String>> langMap = new HashMap<OWLAnnotationProperty, List<String>>();
        langMap.put(df.getRDFSLabel(), Arrays.asList(PREFERENCES.getLanguage()));
        sfp = new AnnotationValueShortFormProvider(Arrays.asList(df.getRDFSLabel()),
                langMap, manager);
    }

    /**
     * getReasoner
     *
     * @return reasoner
     */
    public OWLReasoner getReasoner() {
        return reasoner;
    }

    /**
     * Gets the main topping categories.  This actually returns
     * the direct named subclasses of the Pizza Topping class.
     *
     * @return A <code>Collection</code> of <code>OWLNamedClasses</code>
     */
    public Collection<OWLClass> getPizzaToppingCategories() {
        OWLClass toppingCls = df.getOWLClass(PREFERENCES.getPizzaToppingClassName());
        return reasoner.getSubClasses(toppingCls, true).getFlattened();
    }

    /**
     * Gets the main topping categories.  This actually returns
     * the direct named subclasses of the Pizza Topping class.
     *
     * @return A <code>Collection</code> of <code>OWLNamedClasses</code>
     */
    public Collection<OWLClass> getPizzaBaseCategories() {
        OWLClass toppingCls = df.getOWLClass(PREFERENCES.getBaseClassName());
        return reasoner.getSubClasses(toppingCls, true).getFlattened();
    }


    public String render(OWLEntity entity) {
        String shortForm = sfp.getShortForm(entity);
        if(shortForm.endsWith(TOPPING_SUFFIX)) {
            shortForm = shortForm.substring(0, shortForm.length() - TOPPING_SUFFIX.length());
        }
        StringBuilder sb = new StringBuilder();
        char last = 0;
        for(int i = 0; i < shortForm.length(); i++) {
            char ch = shortForm.charAt(i);
            if(Character.isUpperCase(ch) && last != 0 && last != ' ') {
               sb.append(" ");
            }
            sb.append(ch);
            last = ch;
        }
        return sb.toString();
    }

    public String render1(OWLEntity entity) {
        String shortForm = sfp.getShortForm(entity);
        if(shortForm.endsWith(BASE_SUFFIX)) {
            shortForm = shortForm.substring(0, shortForm.length() - BASE_SUFFIX.length());
        }
        StringBuilder sb = new StringBuilder();
        char last = 0;
        for(int i = 0; i < shortForm.length(); i++) {
            char ch = shortForm.charAt(i);
            if(Character.isUpperCase(ch) && last != 0 && last != ' ') {
                sb.append(" ");
            }
            sb.append(ch);
            last = ch;
        }
        return sb.toString();
    }

    /**
     * Tests to see if the specified class is a VegetarianPizza - i.e.
     * a subclass of the vegetarian pizza class.
     *
     * @param pizzaClass The class to be tested.
     * @return <code>true</code> if the specified class is a vegetarian pizza
     *         (subclass of the vegetarian pizza class), or <code>false</code> if the
     *         specified class is not a vegetarian pizza (not a subclass of the vegetarian
     *         pizza class).
     */
    public boolean isVegetarianPizza(OWLClass pizzaClass) {
        return this.filterClasses(reasoner.getSuperClasses(pizzaClass, false)).contains(getVegetarianPizzaClass());
    }

    /**
     * check to see if specified class is Named pizza
     *
     * @param pizzaClass
     * @return
     */
    public boolean isNamedPizza(OWLClass pizzaClass) {
        return this.filterClasses(reasoner.getSuperClasses(pizzaClass, true)).contains(getPizzaClass());
    }

    /**
     * Tests to see if the specified class is a SpicyPizza - i.e. a
     * subclass of the Spicy Pizza class.
     *
     * @param pizzaClass The class to be tested.
     * @return <code>true</code> if instances of the specified class are spciy pizzas
     *         (i.e. the specified class is a subclass of the spicy pizza class), or <code>false</code>
     *         if instances of the specified class are not spicy pizzas (i.e. the specified class cannot
     *         be determined to be a subclass of the hot pizza class).
     */
    public boolean isSpicyPizza(OWLClass pizzaClass) {
        return this.filterClasses(reasoner.getSuperClasses(pizzaClass, true)).contains(getSpicyPizzaClass());
    }

    /**
     * Tests to see if the specified class is a SpicyPizza - i.e. a
     * subclass of the Spicy Pizza class.
     *
     * @param pizzaClass The class to be tested.
     * @return <code>true</code> if instances of the specified class are spciy pizzas
     *         (i.e. the specified class is a subclass of the spicy pizza class), or <code>false</code>
     *         if instances of the specified class are not spicy pizzas (i.e. the specified class cannot
     *         be determined to be a subclass of the hot pizza class).
     */
    public boolean isBasePizza(OWLClass pizzaClass) {
        return this.filterClasses(reasoner.getSuperClasses(pizzaClass, true)).contains(getBasesClass());
    }

    /**
     * Gets the Vegetarian Pizza class.
     *
     * @return The named class that represents things that are
     *         vegetarian pizzas.
     */
    public OWLClass getVegetarianPizzaClass() {
        return df.getOWLClass(PREFERENCES.getVegetarianPizzaClassName());
    }

    /**
     * Gets the Spicy Pizza Class.
     *
     * @return The named class that represents things that are spicy pizzas
     */
    public OWLClass getSpicyPizzaClass() {
        return df.getOWLClass(PREFERENCES.getSpicyPizzaClassName());
    }

    /**
     * Gets the Pizza class.
     *
     * @return The named class that represents things that are pizzas.
     */
    public OWLClass getPizzaClass() {
        return df.getOWLClass(PREFERENCES.getPizzaClassName());
    }

    /**
     * Gets the Pizza Bases class.
     *
     * @return The named class that represents things that are pizzas.
     */
    public OWLClass getBasesClass() {
        return df.getOWLClass(PREFERENCES.getBaseClassName());
    }

    /**
     * Gets the pizzas that match the requirement for included and excluded
     * toppings.
     *
     * @param includeToppings The toppings that the pizza should have.
     * @param excludeToppings The toppings that the pizza should NOT have.
     * @return A <code>Collection</code> of classes that represent the pizza
     *         classes that statisfy the description of the required toppings.
     */
    public Collection getPizzas(Set<OWLClass> includeToppings, Set<OWLClass> excludeToppings) {
        Collection c;
        // Temporarily create a description (class) that will have the required
        // pizzas (the pizzas with the included toppings but not the excluded toppings).
        // OWLClassExpression toppingDesc = createPizzaDescription(includeToppings, excludeToppings,"7");
        OWLClassExpression toppingDesc = createPizzaDescription(includeToppings, excludeToppings);
        // Ask the reasoner for the subclasses of the temp description
        return filterClasses(reasoner.getSubClasses(toppingDesc, false));
    }
    public Collection getBases(Set<OWLClass> includeBases, Set<OWLClass> excludeBases) {
        Collection c;
        // Temporarily create a description (class) that will have the required
        // pizzas (the pizzas with the included toppings but not the excluded toppings).
        // OWLClassExpression toppingDesc = createPizzaDescription(includeToppings, excludeToppings,"7");
        OWLClassExpression toppingDesc = createPizzaBaseDescription(includeBases,excludeBases);
        // Ask the reasoner for the subclasses of the temp description
        return filterClasses(reasoner.getSubClasses(toppingDesc, false));
    }

    /**
     * Creates OWLClassExpression (query) by given inclided topping and excluded topping
     *
     * @param includeToppings
     * @param excludeToppings
     * @return
     */
    private OWLClassExpression createPizzaDescription(Set<OWLClass> includeToppings, Set<OWLClass> excludeToppings) {
        // Include means existential restrictions
        // Exclude means negated existential restrictions
        OWLObjectProperty prop = getToppingProperty();   //has_topping
        OWLObjectProperty propBase = getBaseProperty();   //has_base
        // Create a hash set to stor the components (existential restrictions)
        // of our description
        Set<OWLClassExpression> classes = new HashSet<OWLClassExpression>();
        // Everthing must be a pizza
        classes.add(getPizzaClass());        //get OWL class for Pizza
        // Create the existential restrictions that represent the toppings
        // that we want to include.
        OWLDataFactory df = manager.getOWLDataFactory();
        for (OWLClass topping : includeToppings) {
            classes.add(df.getOWLObjectSomeValuesFrom(prop, topping));         // e.g. hasTopping some top_A , hasTopping some top_B
        }
        // Create the negated existential restrictions of the toppings that we
        // want to exclude
        for (OWLClass excludeTopping : excludeToppings) {
            OWLClassExpression restriction = df.getOWLObjectSomeValuesFrom(prop, excludeTopping);  // has_topping some topping_A
            OWLObjectComplementOf neg = df.getOWLObjectComplementOf(restriction);    //not (has_topping some topping_A)
            classes.add(neg);
        }

        // Bind the whole thing up in an intersection class
        // to create a concept description of the pizza we
        // are looking for.
        return df.getOWLObjectIntersectionOf(classes);
    }

    /**
     * Creates OWLClassExpression (query) by given inclided topping and excluded topping
     *
     * @param includeBases
     * @param includeBases
     * @return
     */
    private OWLClassExpression createPizzaBaseDescription(Set<OWLClass> includeBases, Set<OWLClass> excludeBases) {
        // Include means existential restrictions
        // Exclude means negated existential restrictions
        OWLObjectProperty prop = getToppingProperty();   //has_topping
        OWLObjectProperty propBase = getBaseProperty();   //has_base
        // Create a hash set to stor the components (existential restrictions)
        // of our description
        Set<OWLClassExpression> classes = new HashSet<OWLClassExpression>();
        // Everthing must be a pizza
        classes.add(getPizzaClass());        //get OWL class for Pizza
        // Create the existential restrictions that represent the bases
        // that we want to include.
        for (OWLClass base : includeBases) {
            classes.add(df.getOWLObjectSomeValuesFrom(propBase, base));         // e.g. hasTopping some top_A , hasTopping some top_B
        }

        // Create the negated existential restrictions of the bases that we
        // want to exclude
        for (OWLClass excludeBase : excludeBases) {
            OWLClassExpression restriction = df.getOWLObjectSomeValuesFrom(propBase, excludeBase);  // has_topping some topping_A
            OWLObjectComplementOf neg = df.getOWLObjectComplementOf(restriction);    //not (has_topping some topping_A)
            classes.add(neg);
        }
        // Bind the whole thing up in an intersection class
        // to create a concept description of the pizza we
        // are looking for.
        return df.getOWLObjectIntersectionOf(classes);
    }

    /**
     * Gets the property that represents the topping relationship
     *
     * @return
     */
    public OWLObjectProperty getToppingProperty() {
        return df.getOWLObjectProperty(PREFERENCES.getToppingPropertyName());
    }

    /**
     * Gets the property that represents the base relationship
     *
     * @return
     */
    public OWLObjectProperty getBaseProperty() {
        return df.getOWLObjectProperty(PREFERENCES.getBasePropertyName());
    }


    /**
     * filters the result of e.g. getSubclasses which is  Set<Set<OWLClass>>  To  Set<OWLClass>
     *
     * @param original
     * @return
     */
    protected Set<OWLClass> filterClasses(NodeSet<OWLClass> original) {
        Set<OWLClass> result = new TreeSet<OWLClass>();
        for (OWLClass cls : original.getFlattened()) {
            if (reasoner.isSatisfiable(cls)) {
                result.add(cls);
            }
        }
        return result;
    }
}

