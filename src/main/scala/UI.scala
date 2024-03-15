import scalafx.application.JFXApp3
import scalafx.collections.ObservableBuffer
import scalafx.geometry.Side
import scalafx.scene.Scene
import scalafx.scene.chart.PieChart
import scalafx.scene.control.{Button, Label, Menu, MenuBar, MenuItem, ScrollPane, Separator, SplitPane, ToolBar}
import scalafx.scene.layout.{HBox, TilePane, VBox}
import charts.PortfolioPieChart.getPieChart
import charts.VolymeBarChart.getVolymeBarChart
import charts.ReturnScatterPlot.getScatterPlot
import charts.TimeSeriesChart.getTimeSeriesChart

object UI extends JFXApp3:

  def start() =
    stage = new JFXApp3.PrimaryStage:
      title = "Personal Portfolio Dashboard"


    val fileOperations = new Menu("File"):
      items = Array(
        MenuItem("Open"),
        MenuItem("Save")
      )

    val newChart = new Menu("New"):
      items = Array(
        MenuItem("Bar Chart"),
        MenuItem("Pie Chart"),
        MenuItem("Scatter Plot"),
        MenuItem("Tile"),
        MenuItem("XY chart")
      )



    val menu = new MenuBar:
      menus = Array(fileOperations, newChart)

    val toolBar = new ToolBar:
      items = Array(
        Button("Select"),
        new Separator,
        Button("Duplicate"),
        Button("Hide"),
        Button("Remove"),
        new Separator,
        Button("Update")
      )

    val left = new VBox:
      children = Array(Label("Hidden components:"), Button("More"), Label("Components"))
    

    val pieChart = getPieChart
    val volymeBarChart = getVolymeBarChart("Nvidia")
    val scatterPlot = getScatterPlot(Array("Nvidia", "Apple"))
    val lineChart = getTimeSeriesChart("Nvidia")

    val charts = new VBox:
      children = Array(pieChart, volymeBarChart, scatterPlot, lineChart)

    val scroll = new ScrollPane:
      content = charts

    val splitPane = new SplitPane:
      items ++= Seq(left, scroll)

    val rootVBox = new VBox:
      children = Array(menu, toolBar, splitPane)


    val root = rootVBox

    stage.scene = Scene(parent = root)