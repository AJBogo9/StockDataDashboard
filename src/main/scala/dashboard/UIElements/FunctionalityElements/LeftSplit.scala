package dashboard.UIElements.FunctionalityElements

import dashboard.UIElements.DataAnalysisTools.Tile.*
import dashboard.UIElements.DataAnalysisTools.PortfolioPieChart.getPieChart
import dashboard.UIElements.DataAnalysisTools.ReturnScatterPlot.getScatterPlot
import dashboard.UIElements.DataAnalysisTools.Tile.*
import dashboard.UIElements.DataAnalysisTools.TimeSeriesChart.getTimeSeriesChart
import dashboard.UIElements.DataAnalysisTools.VolymeBarChart.getVolymeBarChart
import javafx.scene.chart.{BarChart, Chart, LineChart, PieChart, ScatterChart}
import javafx.scene.Node
import javafx.scene.layout.BorderPane
import scalafx.scene.chart.Chart
import scalafx.scene.control.{Button, Label, Separator}
import scalafx.scene.layout.{HBox, Pane, VBox}
import scalafx.scene.text.{Font, FontWeight}
import scalafx.Includes.jfxChart2sfx
import scalafx.scene.SceneIncludes.jfxChart2sfx
import scalafx.scene.chart.ChartIncludes.jfxChart2sfx
import scalafx.Includes.jfxBorderPane2sfx
import scalafx.scene.SceneIncludes.jfxBorderPane2sfx
import scalafx.scene.layout.LayoutIncludes.jfxBorderPane2sfx
import scalafx.Includes.jfxVBox2sfx
import scalafx.scene.SceneIncludes.jfxVBox2sfx
import scalafx.scene.layout.LayoutIncludes.jfxVBox2sfx
import scalafx.Includes.jfxPane2sfx
import scalafx.geometry.Orientation
import scalafx.scene.SceneIncludes.jfxPane2sfx
import scalafx.scene.layout.LayoutIncludes.jfxPane2sfx
import scalafx.Includes.jfxLabel2sfx
import scalafx.scene.SceneIncludes.jfxLabel2sfx
import scalafx.scene.control.ControlIncludes.jfxLabel2sfx
import scalafx.Includes.jfxLabeled2sfx
import scalafx.scene.SceneIncludes.jfxLabeled2sfx
import scalafx.scene.control.ControlIncludes.jfxLabeled2sfx
import dashboard.UIElements.FunctionalityElements.RightSplit.addElementToPane
import dashboard.lib.Api.getTimeSeries
import javafx.scene.paint.Color
import scalafx.Includes.jfxNode2sfx
import scalafx.scene.SceneIncludes.jfxNode2sfx
import scalafx.Includes.observableList2ObservableBuffer
import scalafx.collections.CollectionIncludes.observableList2ObservableBuffer

import scala.collection.mutable.Buffer

object LeftSplit:

  private val leftSideVBox = new VBox


  def getLeftSplit =

    val leftSplitHeader = new Label(s"Hidden elements:"):
      font = Font("System", FontWeight.ExtraBold, 20)

    leftSideVBox.children += leftSplitHeader

    leftSideVBox


  def hideComponent(element: Node): Unit =
    element.style = ""
    leftSideVBox.children += hiddenElementHBox(element)


  private def hiddenElementHBox(element: Node): HBox =
    val hiddenElement = new HBox
    val addButton = new Button("Add")
    val duplicateButton = new Button("Duplicate")

    val separator = new Separator:
      orientation = Orientation.Vertical

    element match
      case chart: javafx.scene.chart.Chart =>
        hiddenElement.children = Array(
          Label(s" - ${chart.title.value} "),
          separator,
          addButton,
          duplicateButton
        )
      case tile: javafx.scene.layout.VBox =>
        val name = tile.children.head.asInstanceOf[javafx.scene.control.Label].text()
        hiddenElement.children = Array(
          Label(s" - $name label "),
          separator,
          addButton,
          duplicateButton
        )



    addButton.onAction = (event) =>
      addElementToPane(element)
      leftSideVBox.children.removeAll(hiddenElement)

    duplicateButton.onAction = (event) =>
      duplicateElement(element)

    hiddenElement


  private def duplicateElement(element: Node) =
    var duplicate = element

    element match
      case pieChart: PieChart =>
        val name = pieChart.getTitle.split(" ")(3)
        duplicate = getPieChart(name)

      case scatterPlot: ScatterChart[Number, Number] =>
        val year = scatterPlot.getTitle.split(" ")(5).toInt
        var companyNames = Array[String]()
        val companyData = scatterPlot.getData
        for serie <- companyData do
            val companyName = serie.getName.split(" ").head
            companyNames = companyNames :+ companyName
        duplicate = getScatterPlot(companyNames, year)

      case xyChart: LineChart[String, Number] =>
        val name = xyChart.getTitle.split(" ")(0)
        val color = xyChart.getStylesheets.getFirst.split("#")(1).take(8)
        duplicate = getTimeSeriesChart(name, color)

      case barChart: BarChart[String, Number] =>
        val name = barChart.getTitle.split(" ")(0)
        val color = barChart.getStylesheets.getFirst.split("#")(1).take(8)
        duplicate = getVolymeBarChart(name, color)

      case tile: javafx.scene.layout.VBox =>
        val name = tile.children.head.asInstanceOf[javafx.scene.control.Label].text()
        val tileElementCount = tile.children.length
        if tileElementCount == 2 then
          duplicate = getPortfolioTile(name)
        else if tileElementCount == 5 then
          duplicate = getStockTile(name)

    hideComponent(duplicate)
