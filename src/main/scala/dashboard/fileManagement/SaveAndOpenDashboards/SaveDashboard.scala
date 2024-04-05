package dashboard.fileManagement.SaveAndOpenDashboards

import dashboard.UIElements.DataAnalysisTools.Tile.*
import dashboard.UIElements.FunctionalityElements.Alerts.DashboardOpeningAlert.updateDashboardOpeningChoiseBox
import dashboard.UIElements.FunctionalityElements.LeftSplit.getHiddenElements
import dashboard.UIElements.FunctionalityElements.RightSplit.getPane
import dashboard.fileManagement.SaveAndOpenDashboards.OpenDashboard.getSaveFileMap
import javafx.scene.Node
import javafx.scene.chart.{BarChart, LineChart, PieChart, ScatterChart}
import javafx.scene.layout.VBox
import scalafx.Includes.jfxVBox2sfx
import scalafx.collections.CollectionIncludes.observableList2ObservableBuffer
import scalafx.collections.ObservableBuffer
import scalafx.scene.SceneIncludes.jfxLabel2sfx
import ujson.Value

import java.io.FileWriter
import scala.collection.immutable.Map
import scala.io.Source

object SaveDashboard:

  val saveFilePath = "src/main/scala/dashboard/data/saveFiles/saveFile.json"


  /**
   * Saves current dashboard to a save file.
   * The function does the following steps:
   *    1) creates a new save map from the dashboard
   *    2) reads the previously saved dashboards from save file
   *    3) adds the new dashboard to the map of the previously saved dashboards
   *    4) overwrites the previous map in the save file with the new map
   * @param name the name of the dashboard
   */
  def saveDashboard(name: String) =
    val newData = Map(
      name -> createNewSaveMap
    )
    var savedData = getSaveFileMap
    savedData = savedData ++ newData

    // update file opening alert
    updateDashboardOpeningChoiseBox(savedData.keys)

    val file = saveFilePath
    val fw = new FileWriter(file, false)

    val JSONString = upickle.default.write(savedData)

    fw.write(JSONString)
    fw.close()

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

  