package dashboard.UIElements.FunctionalityElements

import dashboard.UIElements.DataAnalysisTools.Tile.getPortfolioTile
import scalafx.application.JFXApp3
import scalafx.scene.Scene
import scalafx.scene.layout.Pane
import scalafx.scene.paint.Color
import scalafx.scene.shape.Rectangle
import dashboard.UIElements.DataAnalysisTools.PortfolioPieChart.getPieChart
import dashboard.UIElements.DataAnalysisTools.Tile.getPortfolioTile
import dashboard.UIElements.FunctionalityElements.LeftSplit.hideComponent
import dashboard.UIElements.FunctionalityElements.MenuElement.getMenuElement
import dashboard.UIElements.FunctionalityElements.ToolBar.selectButton
import scalafx.Includes.{jfxMouseEvent2sfx, jfxNode2sfx}
import scalafx.application.{JFXApp, JFXApp3}
import scalafx.collections.ObservableBuffer
import scalafx.scene.Scene
import scalafx.scene.SceneIncludes.jfxNode2sfx
import scalafx.scene.control.Button
import scalafx.scene.input.InputIncludes.jfxMouseEvent2sfx
import scalafx.scene.input.MouseEvent
import scalafx.scene.layout.{Pane, VBox}
import scalafx.scene.paint.Color
import scalafx.scene.shape.Rectangle
import javafx.scene.Node

import scala.Console.println
import scala.collection.mutable.Buffer
import scala.util.boundary.break
import scala.util.control.Breaks.breakable


object RightSplit:

  private val pane = new Pane
  private var paneSlotsOccupied = Buffer(
    Buffer(false, false, false, false),
    Buffer(false, false, false, false)
  )
  private val (width, heigth) = (300.0, 290.0)

  private val selectionRectangle = new Rectangle:
    stroke = Color.Black
    fill = Color.Transparent
    strokeDashArray = List(3d, 3d)
    visible = false

  def getRightSplit =

    // Create a rectangle to represent the selection area


    var startX: Double = 0
    var startY: Double = 0

    pane.onMousePressed = (event) =>
      if selectButton.selected() then
        selectionRectangle.visible = true
        startX = event.x
        startY = event.y
        selectionRectangle.x = startX
        selectionRectangle.y = startY
        selectionRectangle.width = 0
        selectionRectangle.height = 0

      for (child <- pane.children) do
        child match
          case node: javafx.scene.Node =>
            node.style = ""


    pane.onMouseDragged = (event) =>
      if selectButton.selected() then

        var currentX = event.x
        var currentY = event.y
        val width = currentX - startX
        val height = currentY - startY

        selectionRectangle.width = Math.abs(width)
        selectionRectangle.height = Math.abs(height)

        selectionRectangle.x = if (width < 0) currentX else startX
        selectionRectangle.y = if (height < 0) currentY else startY


        // Check for intersection with buttons in the pane and mark them as selected
        for (child <- pane.children) do
          child match
            case button: javafx.scene.Node =>
              button.style =
                if (button.getBoundsInParent.intersects(selectionRectangle.getBoundsInParent))
                  "-fx-background-color: blue;"
                else ""

    pane.onMouseReleased = (event) =>
      if selectButton.selected() then
        selectionRectangle.visible = false


    // this button is used to ensure that pane reaches over the whole window.
    // if this button is not in place and the pane is empty, the pane will only be the
    // size of its componets sizes
    val buttonFarAway = new Button("Congratulations, you found me :)")
    pane.children.addAll(selectionRectangle, buttonFarAway)
    buttonFarAway.layoutX = 9999
    buttonFarAway.layoutY = 9999


    pane

  def removeSelectedComponents() =
    val selectedComponents = pane.children.filter(_.style().contains("blue"))
    selectedComponents.removeAll(selectionRectangle)
    for component <- selectedComponents do
      val row = (component.getLayoutY / heigth).toInt
      val column = (component.getLayoutX / width).toInt
      paneSlotsOccupied(row)(column) = false
    pane.children.removeAll(selectedComponents)
    selectedComponents

  def componentWidthAndHeigth = (width, heigth)

  def addElementToPane(element: javafx.scene.Node) =

    var firstFreeSlot: Option[(Int, Int)] = None

    var row = 0
    while row < 2 do
      var column = 0
      while column < 4 do
        if !paneSlotsOccupied(row)(column) then
          firstFreeSlot = Some((row, column))
          row = 2
          column = 4
        column += 1
      row += 1

    firstFreeSlot match
      case Some((row, column)) =>
        pane.children.prepend(element)
        element.layoutX = column * width
        element.layoutY = row * heigth
        paneSlotsOccupied(row)(column) = true
      case None =>
        hideComponent(element)

