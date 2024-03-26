package dashboard.UIElements.FunctionalityElements

import dashboard.UIElements.DataAnalysisTools.Tile.getPortfolioTile
import scalafx.application.JFXApp3
import scalafx.scene.Scene
import scalafx.scene.layout.Pane
import scalafx.scene.paint.Color
import scalafx.scene.shape.Rectangle
import dashboard.UIElements.DataAnalysisTools.PortfolioPieChart.getPieChart
import dashboard.UIElements.DataAnalysisTools.Tile.getPortfolioTile
import dashboard.UIElements.FunctionalityElements.MenuElement.getMenuElement
import dashboard.UIElements.FunctionalityElements.ToolBar.selectButton
import dashboard.lib.Utils.borderedElement
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


object RightSplit:

  private val pane = new Pane

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


    val buttonFarAway = borderedElement(new Button("Congratulations, you found me :)"))
    pane.children.addAll(selectionRectangle, buttonFarAway)
    buttonFarAway.layoutX = 9999
    buttonFarAway.layoutY = 9999

    /*
    // Add some sample buttons to the pane
    val button1 = new Button("Button 1")
    val button2 = new Button("Button 2")
    val button3 = new Button("Button 3")
    val tile = getPortfolioTile("portfolio1")
    val pieChart = getPieChart("portfolio1")
    pane.children.addAll(button2, button3, tile, pieChart, buttonFarAway, selectionRectangle)
    pane.children += button1
    button1.layoutX = 100
    button1.layoutY = 100
    button2.layoutX = 200
    button2.layoutY = 200
    button3.layoutX = 300
    button3.layoutY = 300
    tile.layoutX = 400
    tile.layoutY = 400
    pieChart.layoutX = 400
    pieChart.layoutY = 0

     */

    pane

  def removeSelectedComponents() =
    val selectedComponents = pane.children.filter(_.style().contains("blue"))
    selectedComponents.removeAll(selectionRectangle)
    pane.children.removeAll(selectedComponents)
    selectedComponents

  def addElementToPane(element: javafx.scene.Node) =
    pane.children.prepend(element)
    element.layoutX = 0.0
    element.layoutY = 0.0
