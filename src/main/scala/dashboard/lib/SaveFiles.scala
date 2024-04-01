package dashboard.lib

import dashboard.UIElements.FunctionalityElements.LeftSplit.{clearLeftSplit, getHiddenElements, getLeftVbox, hideComponent}
import dashboard.UIElements.FunctionalityElements.RightSplit.{addElementToPane, clearRightSplit, getPane}
import javafx.scene.Node
import javafx.scene.Node
import javafx.scene.chart.{BarChart, LineChart, PieChart, ScatterChart}
import javafx.scene.layout.VBox
import scalafx.collections.ObservableBuffer
import scalafx.geometry.Orientation
import scalafx.scene.chart.XYChart
import scalafx.scene.control.Separator
import scalafx.scene.layout.BorderPane
import scalafx.Includes.observableList2ObservableBuffer
import scalafx.collections.CollectionIncludes.observableList2ObservableBuffer
import scalafx.Includes.jfxVBox2sfx
import scalafx.scene.SceneIncludes.jfxVBox2sfx
import scalafx.scene.layout.LayoutIncludes.jfxVBox2sfx
import scalafx.Includes.jfxPane2sfx
import scalafx.scene.SceneIncludes.jfxPane2sfx
import scalafx.scene.layout.LayoutIncludes.jfxPane2sfx
import scalafx.Includes.jfxLabel2sfx
import scalafx.scene.SceneIncludes.jfxLabel2sfx
import scalafx.scene.control.ControlIncludes.jfxLabel2sfx
import scalafx.Includes.jfxLabeled2sfx
import scalafx.scene.SceneIncludes.jfxLabeled2sfx
import scalafx.scene.control.ControlIncludes.jfxLabeled2sfx
import dashboard.UIElements.DataAnalysisTools.PortfolioPieChart.getPieChart
import dashboard.UIElements.DataAnalysisTools.Tile.{getPortfolioTile, getStockTile}
import dashboard.UIElements.DataAnalysisTools.TimeSeriesChart.getTimeSeriesChart
import dashboard.UIElements.DataAnalysisTools.ReturnScatterPlot.getScatterPlot
import dashboard.UIElements.DataAnalysisTools.VolumeBarChart.getVolumeBarChart
import dashboard.UIElements.DataAnalysisTools.Tile.*
import ujson.Value

import java.io.FileWriter
import scala.{+:, :+}
import scala.collection.immutable.Map
import scala.collection.mutable
import scala.collection.mutable.{ArrayBuffer, Buffer}
import scala.io.Source

object SaveFiles:

  private val saveFilePath = "src/main/scala/dashboard/data/saveFiles/saveFile.json"

  def getSaveFileMap =
    val src = saveFilePath
    val bufferedSource = Source.fromFile(src)
    val rawText = bufferedSource.mkString
    bufferedSource.close()

    val data = ujson.read(rawText).obj
    var retMap: Map[String, Map[String, Array[Map[String, String]]]] = Map()

    for key <- data.keys do
      val shownComponents = data(key)("shown").arr.map(
        component => component.obj.map(
          (key, value) => key -> value.str
        ).toMap
      ).toArray

      val hiddenComponents = data(key)("hidden").arr.map(
        component => component.obj.map(
          (key, value) => key -> value.str
        ).toMap
      ).toArray

      val dashboardMap = Map(
        key -> Map(
          "shown" -> shownComponents,
          "hidden" -> hiddenComponents
        )
      )

      retMap = retMap ++ dashboardMap


    retMap


  def saveDashboard(name: String) =
    val newData = Map(
      name -> createNewSaveMap
    )
    var savedData = getSaveFileMap
    savedData = savedData ++ newData

    val file = saveFilePath
    val fw = new FileWriter(file, false)

    val JSONString = upickle.default.write(savedData)

    fw.write(JSONString)
    fw.close()

  def openDashboard(name: String) =
    val data = getSaveFileMap(name)

    clearLeftSplit()
    clearRightSplit()

    val shownComponents = data("shown")
    for component <- shownComponents do
      val element = createComponent(component)
      addElementToPane(element)

    val hiddenComponents = data("hidden")
    for component <- hiddenComponents do
      val element = createComponent(component)
      hideComponent(element)

  def createComponent(data: Map[String, String]) =
    data("type") match
      case "pie chart" => getPieChart(data("portfolio"))
      case "xy chart" => getTimeSeriesChart(data("company"), data("color"))
      case "scatter plot" => getScatterPlot(Array(data("company1"), data("company2")), data("year").toInt)
      case "portfolio tile" => getPortfolioTile(data("portfolio"))
      case "stock tile" => getStockTile(data("company"))
      case "bar chart" => getVolumeBarChart(data("company"), data("color"))


  def createNewSaveMap: Map[String, Array[Map[String, String]]] =
    val shownComponents = getShownComponentsMap
    val hiddenComponents = getHiddenComponentsMap

    Map(
      "shown" -> shownComponents,
      "hidden" -> hiddenComponents
    )


  def getShownComponentsMap: Array[Map[String, String]] =
    val shownComponents = getPane.children
    var shownComponentsArray: Array[Map[String, String]] = Array()

    for component <- shownComponents do
      shownComponentsArray = shownComponentsArray :+ getComponentData(component)

    shownComponentsArray.dropRight(2) // this drops the selection rectangle and the button far away


  def getHiddenComponentsMap =
    val hiddenComponents = getHiddenElements
    var hiddenComponentsArray: Array[Map[String, String]] = Array()

    for component <- hiddenComponents do
      hiddenComponentsArray = hiddenComponentsArray :+ getComponentData(component)

    hiddenComponentsArray


  def getComponentData(node: Node): Map[String, String] =
    var componentData: Map[String, String] = Map()

    node match
      case pieChart: PieChart =>
        val name = pieChart.getTitle.split(" ")(3)
        componentData += "type" -> "pie chart"
        componentData += "portfolio" -> name

      case scatterPlot: ScatterChart[Number, Number] =>
        val year = scatterPlot.getTitle.split(" ")(5)
        var companyNames = Array[String]()
        val companyData = scatterPlot.getData
        for serie <- companyData do
          val companyName = serie.getName.split(" ").head
          companyNames = companyNames :+ companyName
        componentData += "type" -> "scatter plot"
        componentData += "company1" -> companyNames(0)
        componentData += "company2" -> companyNames(1)
        componentData += "year" -> year

      case xyChart: LineChart[String, Number] =>
        val name = xyChart.getTitle.split(" ")(0)
        val color = xyChart.getStylesheets.getFirst.split("#")(1).take(8)
        componentData += "type" -> "xy chart"
        componentData += "company" -> name
        componentData += "color" -> color

      case barChart: BarChart[String, Number] =>
        val name = barChart.getTitle.split(" ")(0)
        val color = barChart.getStylesheets.getFirst.split("#")(1).take(8)
        componentData += "type" -> "bar chart"
        componentData += "company" -> name
        componentData += "color" -> color

      case tile: VBox =>
        val name = tile.children.head.asInstanceOf[javafx.scene.control.Label].text()
        val tileElementCount = tile.children.length
        if tileElementCount == 2 then
          componentData += "type" -> "portfolio tile"
          componentData += "portfolio" -> name
        else if tileElementCount == 5 then
          componentData += "type" -> "stock tile"
          componentData += "company" -> name
      case _ =>

    componentData


  def getDashboardNames = getSaveFileMap.keys.toArray










