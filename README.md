# Proyecto_Recomendador de Pizzas
_Este proyecto presenta la adaptación de un recomendador de pizzas desarrollado por la Universidad de Manchester, utilizando la lógica de programación de la universidad se obtuvo la búsqueda de pizzas por Topping y se agrego el componente de búsqueda de pizzas por Base. Y finalmente se presento los resultados de las pizzas que contienen las Bases o Topping que se selecciono._

## Integrantes ✒️

* **Stalin Carrión** 
* **Karen Torres** 
* **María Paula Jaramillo** 
* **Erika Vásquez** 
## Manchester ontología Pizzas📋
## Módulo Recomendador(Erika Vázquez, Karen Torres)📋
## Módulo Visualizador(Stalin Carrión, María Paula Jaramillo)📋
### Stalin Carrión
Gracias a la estructura de programación otorgada por Manchester, y de acuerdo al Model que han establecido se aportó en la codificación de las basesPanel, con el fin de poder visualizar las bases que optenemos por parte del modulo de recomendador de nuestras compañeras.
#### Script BannerPanel.java
![s1](https://user-images.githubusercontent.com/14815092/89477629-667dcf80-d753-11ea-9398-f2f8664d55bc.jpg)
Para este script llamado BannerPanel.java, mediante un enlace a la carpeta del proyecto se muestra la imagen de la pizza, la cual será la que se muestra en el momento de ejecutar la aplicación, el metodo BannerPanel(), obtiene dicha imagen y establece una dimensión de la misma, esto, con el fin de mostrar un tamaño del banner. El método getPreferredSize(), es proporcionado por Manchester el cual nos ayuda a obtener el tamaño del panel general. Mientras que el paintComponent(Graphics g), plasma la imagen gracias a la funcionalidad Graphics.
#### Script BaseListPanel.java
![s2](https://user-images.githubusercontent.com/14815092/89477710-97f69b00-d753-11ea-9621-dd5a00daa588.jpg)
En el script BaseListPanel, se implemento la lista de las bases en un panel, de acuerdo a los metodos ya creados de JList y JScrollPanel. Se añade buttonPanel para llamar al addAction, removeAction y en el metodo valueChanged se establece BaseListPanel para activar y desctivar los paneles de acuerdo al evento seleccionado.
#### Script BasePanel.java
![s3](https://user-images.githubusercontent.com/14815092/89477747-ac3a9800-d753-11ea-8693-fb681644bbcc.jpg)
Se crea un if el cual detecta si se escoge esa ontologia se muestra toda la información por parte del razonar del basePizza, y se le añade a esta informacion un tamaño y una configuración de la fuente.
#### Script BasesPanel.java
![s4](https://user-images.githubusercontent.com/14815092/89477787-c07e9500-d753-11ea-95fb-dfca80ff274c.jpg)
 Con este método se llama a la ontología  de la clase PizzaOntology y se manda a llamar al metodo createUI para que pueda renderizar la interfaz diseñada anteriormente.
 #### Script ExcludeBaseListPanel.java
![s5](https://user-images.githubusercontent.com/14815092/89477821-d68c5580-d753-11ea-8744-6a8e20b88574.jpg)
En este script se añadio la accion de removeAction con el fin de que en el momento de seleccionar el topping o las bases se pueda eliminar las acciones del anterior evento, ya que los demás metodo se encuentran implementados solo para mostrar los toppings de una pizza.
### María Paula Jaramillo

## Capturas 📖
### Pantalla Principal del Recomendador de pizzas
![](https://github.com/etvasquez/finderpizza/blob/master/PantallaPrincipal.JPG)
### Pantalla: Panel de selección de bases
![](https://github.com/etvasquez/finderpizza/blob/master/PanelBases.JPG)
### Pantalla: Visualizar pizzas
![](https://github.com/etvasquez/finderpizza/blob/master/PresentarPizzas.JPG)
