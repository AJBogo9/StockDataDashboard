import UIElements.FunctionalityElements.MenuElement.getMenuElement
import scalafx.application.JFXApp3
import scalafx.collections.ObservableBuffer
import scalafx.geometry.{Orientation, Pos, Side}
import scalafx.scene.Scene
import scalafx.scene.chart.PieChart
import scalafx.scene.control.{Alert, Button, ChoiceBox, Label, Menu, MenuBar, MenuItem, ScrollPane, Separator, SplitPane, ToolBar}
import scalafx.scene.layout.{BorderPane, FlowPane, HBox, TilePane, VBox}
import UIElements.DataAnalysisTools.PortfolioPieChart.getPieChart
import UIElements.DataAnalysisTools.VolymeBarChart.getVolymeBarChart
import UIElements.DataAnalysisTools.ReturnScatterPlot.getScatterPlot
import UIElements.DataAnalysisTools.Tile.getTile
import UIElements.DataAnalysisTools.TimeSeriesChart.getTimeSeriesChart
import scalafx.event.ActionEvent
import scalafx.scene.control.Alert.AlertType
import UIElements.FunctionalityElements.MenuElement.*
import UIElements.FunctionalityElements.ToolBar.*

object UI extends JFXApp3:

  def start() =
    stage = new JFXApp3.PrimaryStage:
      title = "Personal Portfolio Dashboard"

    val menu = getMenuElement
    val toolBar = getToolBarElement
    
    // Charts
    val pieChart = getPieChart
    val volymeBarChart = getVolymeBarChart("Nvidia")
    val scatterPlot = getScatterPlot(Array("Nvidia", "Apple"), 2023)
    val lineChart = getTimeSeriesChart("Nvidia")
    val tile = getTile("Testing")

    val charts = new VBox:
      children = Array(pieChart, volymeBarChart, scatterPlot, lineChart, tile)

    val rightSplit = new ScrollPane:
      content = charts

    val leftSplit = new VBox:
      children = Array(Label("Hidden components:"), Button("More"), Label("Components"))

    val splitPane = new SplitPane:
      items ++= Seq(leftSplit, rightSplit)

    val root = new VBox:
      children = Array(menu, toolBar, splitPane)
      

    stage.scene = Scene(root, 700, 500)


    // ALERTS

    val choices = ObservableBuffer("Apple", "Nvidia", "Microsoft")

    val choiceBox = new ChoiceBox[String]:
      items = choices
      value = choices.head

    val alertContent = new VBox:
      children = Array(choiceBox)
      alignment = Pos.Center

    // Create and display the alert box with the choice box
    var alert = new Alert(AlertType.Confirmation):
      initOwner(stage)
      title = "Chart customization"
      headerText = "Choose company"
      // Set the custom content to be the choice box
      dialogPane().setContent(alertContent)

    
    
    barChartMenuItem.onAction = (event) =>
      val result = alert.showAndWait()
      val company = choiceBox.value.value
      result match
        case Some(_) => charts.children += getVolymeBarChart(company)
        case None =>

    xyChartMenuItem.onAction = (event) =>
      val result = alert.showAndWait()
      val company = choiceBox.value.value
      result match
        case Some(_) => charts.children += getTimeSeriesChart(company)
        case None =>

    pieChartMenuItem.onAction = (event) =>
      val result = alert.showAndWait()
      val company = choiceBox.value.value
      result match
        case Some(_) => charts.children += getVolymeBarChart(company)
        case None =>

    scatterPlotMenuItem.onAction = (event) =>
      val result = alert.showAndWait()
      val company = choiceBox.value.value
      result match
        case Some(_) => charts.children += getVolymeBarChart(company)
        case None =>

    tileMenuItem.onAction = (event) =>
      val result = alert.showAndWait()
      val company = choiceBox.value.value
      result match
        case Some(_) => charts.children += getVolymeBarChart(company)
        case None =>


