package uk.ac.man.cs.mig.coode.pizzafinder.ui;

import org.semanticweb.owlapi.model.OWLClass;
import uk.ac.man.cs.mig.coode.pizzafinder.model.PizzaOntology;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Collection;

public class PizzaApplication extends JFrame {

	private PizzaOntology ontology;

	private ResultsPanel resultsPanel;

	private JPanel cardPanel;

	private JPanel mainPanel;

	public String tipo;

	public PizzaApplication() {
		setupMenuBar();
		setupFrame();
		setupMainPanel();
		final LoaderPanel loaderPanel = new LoaderPanel();
		loaderPanel.startLoadingAnimation(mainPanel, BorderLayout.CENTER);
		Runnable r = new Runnable() {
			public void run() {
				// Create and load the Pizza Ontology
				ontology = new PizzaOntology();
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						setupCardPanel(tipo);
						loaderPanel.stopLoadingAnimation();
					}
				});
			}
		};
		Thread t = new Thread(r);
        t.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable throwable) {
                loaderPanel.displayError(throwable.getMessage());
            }
        });
		t.start();
	}

	private Action tipoCobertura = new AbstractAction("Tipo Cobertura") {
		public void actionPerformed(ActionEvent e) {
			tipo = "Cobertura";
		}
	};

	private Action tipoBase= new AbstractAction("Tipo Base") {
		public void actionPerformed(ActionEvent e) {
			tipo = "Base";
		}
	};

	protected void setupMainPanel() {
		mainPanel = new JPanel(new BorderLayout(7, 7));
		mainPanel.add(new BannerPanel(), BorderLayout.NORTH);
        getContentPane().add(mainPanel);
	}

	protected void setupCardPanel(String tipo) {
		cardPanel = new JPanel();
		cardPanel.setLayout(new CardLayout());
		if(tipo=="Cobertura"){
			cardPanel.add(new ToppingsChooserPanel(ontology, this), "ToppingsChooserPanel");
		}else {
			cardPanel.add(new BasesChooserPanel(ontology, this), "BaseChooserPanel");
		}
		cardPanel.add(resultsPanel = new ResultsPanel(ontology, this), "ResultsPanel");
		mainPanel.add(cardPanel);
	}

	protected void setupMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		JMenu menu = new JMenu("Recomendador de Pizzas");
		menuBar.add(menu);
		JMenuItem menuItem = new JMenuItem(tipoCobertura);
		menu.add(menuItem);
		JMenuItem menuItem1 = new JMenuItem(tipoBase);
		menu.add(menuItem1);
	}


	protected void setupFrame() {
		setSize(800, 600);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(screenSize.width / 2 - getWidth() / 2, screenSize.height / 2 - getHeight() / 2);
		getContentPane().setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}


	public void showToppingsPanel() {
		((CardLayout)cardPanel.getLayout()).first(cardPanel);
	}

	public void showBasesPanel() {
		((CardLayout)cardPanel.getLayout()).first(cardPanel);
	}

	public void showResultsPanel(Collection<OWLClass> pizzas) {
		resultsPanel.setPizzas(pizzas);
		((CardLayout)cardPanel.getLayout()).last(cardPanel);
	}
	public void showResultsBasePanel(Collection<OWLClass> bases) {
		resultsPanel.setBases(bases);
		((CardLayout)cardPanel.getLayout()).last(cardPanel);
	}

	public static void main(String [] args) {
		System.out.println("Starting app...");
		PizzaApplication panel = new PizzaApplication();
		System.out.println("...created pizza app.");
		panel.setVisible(true);

	}
}

