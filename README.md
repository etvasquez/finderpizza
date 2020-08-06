# Proyecto_Recomendador de Pizzas
_Este proyecto presenta la adaptación de un recomendador de pizzas desarrollado por la Universidad de Manchester(https://github.com/owlcs/pizzafinder), utilizando la lógica de programación de la universidad se obtuvo la búsqueda de pizzas por Topping y se agrego el componente de búsqueda de pizzas por Base. Y finalmente se presento los resultados de las pizzas que contienen las Bases o Topping que se selecciono._
_Mediante este proyecto se presenta una aplicación de la ontología desarrollada por la Universidad de Standford utilizando Protege, actividad que se realizó en el proyecto anterior._

## Integrantes ✒️

* **Stalin Carrión** 
* **Karen Torres** 
* **María Paula Jaramillo** 
* **Erika Vásquez** 
## Manchester ontología Pizzas📋
## Módulo Recomendador(Erika Vázquez, Karen Torres)📋
## Ontología
![](https://github.com/etvasquez/finderpizza/blob/master/Ontologia.JPG)
La Ontología que se ha utilizado para el projecto recomendador de pizza es tomada de la Universidad de Manchester.
## Método loadOntology() y getOntology()
![](https://github.com/etvasquez/finderpizza/blob/master/loadOntology.JPG)
Este método es para cargar la ontología desde la clase Preference en donde se encuentran los elementos de la Clase. Además llama al método getOntologyDocumentIRI() en el cual se encuentra la dirección de la ontología. Y en caso que no la encuentra a la ontología presenta un mensaje de error. En el método getOntology corre el resoner.
## Método render()
![](https://github.com/etvasquez/finderpizza/blob/master/render.JPG)
La función render y render 1 se encargan de obtener todas las coberturas y bases que existen utilizando el prefijo BASE_SUFFIX con la  finalidad de trasformarlos a un solo formato y almacenarlos en un String para ser procesados por el razonador.  
## Método reasoner()
![](https://github.com/etvasquez/finderpizza/blob/master/resoner.JPG)
El metodo reasoner se encarga de obtener todas las bases y coberturas utilizando la librería OWL que devuelve una coleccion de clases con las bases o coberturas resultantes del razonamiento. 
## Método resonerClassPizza()
![](https://github.com/etvasquez/finderpizza/blob/master/resonerClassPizza.JPG)
Aquí lo que hace el resoner es hacer un test para ver si la clase especificada es VegetarianPizza, es decir si una subclase de la clase de pizza es vegetariana el parametro pizzaClass es true.
## Método getPizza(),getBase() o getTopping()
![](https://github.com/etvasquez/finderpizza/blob/master/CollectionPizzaClass.JPG)
Obtiene las pizzas que coinciden con el requisito de incluido y excluido.
## Módulo Visualizador(Stalin Carrión, María Paula Jaramillo)📋
### Stalin Carrión
voy a documentar los scripts BannerPanel, BaseListPanel, BasePanel, BasesPanel, ExcludeBaseListPanel
### María Paula Jaramillo

## Capturas 📖
### Pantalla Principal del Recomendador de pizzas
![](https://github.com/etvasquez/finderpizza/blob/master/PantallaPrincipal.JPG)
### Pantalla: Panel de selección de bases
![](https://github.com/etvasquez/finderpizza/blob/master/PanelBases.JPG)
### Pantalla: Visualizar pizzas
![](https://github.com/etvasquez/finderpizza/blob/master/PresentarPizzas.JPG)
