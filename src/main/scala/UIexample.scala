import scalafx.application.JFXApp3
import scalafx.geometry.{Insets, Orientation, Pos}
import scalafx.scene.Scene
import scalafx.scene.canvas.Canvas
import scalafx.scene.control.{Button, ColorPicker, Label, Menu, MenuBar, MenuItem, RadioButton, Separator, Spinner, ToggleGroup}
import scalafx.scene.layout.{BorderPane, HBox, VBox}
import scalafx.scene.paint.Color
import scalafx.scene.text.{Font, FontWeight}

object simpleGUI extends JFXApp3:

  def start() =
    stage = new JFXApp3.PrimaryStage:
      title = "Drawing program"
      width = 800
      height = 700
      resizable = false

    // Top menu element. Contains 3 items
    val menu = new Menu("File"):
      items = Array(MenuItem("New"), MenuItem("Open"), MenuItem("Save"))

    // The MenuBar itself. In this case, it only contains one menu
    val top = new MenuBar:
      menus = Array(menu)

    val label = new Label("Added so far:"):
      font = Font("System", FontWeight.ExtraBold, 16)

    // Bottom container. Contains labels and a Separator (= Horizontal line)
    val bottom = new VBox:
      prefHeight = 200
      margin = Insets(10)
      children = Array(Separator(Orientation.Horizontal), label, Label("Rectangle at: (50, 50)"), Label("Circle at: (350, 270)"))

    // The group that our radio buttons belong to. Only one can be selected at a time
    val shapeSelector = ToggleGroup()

    val circleSelector = new RadioButton("Circle"):
      toggleGroup = shapeSelector
      // By default a RadioButton isn't selected
      selected = true

    val rectangleSelector = new RadioButton("Rectangle"):
      toggleGroup = shapeSelector

    val sizeSelector = new HBox:
      spacing = 5
      alignment = Pos.BaselineCenter
      // Spinner's definition: (minValue, maxValue, initialValue, incrementSize)
      children = Array(Label("Size:"), Spinner(10, 500, 50, 10))

    val left = new VBox:
      prefHeight = 700
      prefWidth = 200
      // Large top margin is for the menuBar not to block anything in the picture
      // If it wasn't for instructive purposes, the top margin could be 10.
      margin = Insets(100, 0, 0, 10)
      spacing = 10
      // ColorPicker allows the user to select a color
      // The default color is given as a parameter
      children = Array(ColorPicker(Color.Blue), circleSelector, rectangleSelector, sizeSelector, Button("Add shape"))

    val canvas = Canvas(800, 500)

    canvas.graphicsContext2D.fill = Color.Blue
    canvas.graphicsContext2D.fillRect(50, 50, 90, 90)
    canvas.graphicsContext2D.fill = Color.ForestGreen
    canvas.graphicsContext2D.fillOval(350, 270, 50, 50)

    val center = new HBox:
      children = Array(Separator(Orientation.Vertical), canvas)

    // There is no right-element, so it can be left null
    val root = BorderPane(center, top, null, bottom, left)

    stage.scene = Scene(parent = root)
  end start
end simpleGUI