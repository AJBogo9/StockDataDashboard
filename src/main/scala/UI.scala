import scalafx.application.JFXApp3
import scalafx.collections.ObservableBuffer
import scalafx.geometry.Side
import scalafx.scene.Scene
import scalafx.scene.chart.PieChart
import scalafx.scene.control.{Button, Label, Menu, MenuBar, MenuItem, ScrollPane, Separator, SplitPane, ToolBar}
import scalafx.scene.layout.{HBox, TilePane, VBox}

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

    val dataPairs = Seq(("hello", 200), ("world", 100), ("I", 50), ("am going to do sports", 44))
    val pieChart = new PieChart {
      title = "Pie Chart"
      data = ObservableBuffer.from(dataPairs.map({ case (x, y) =>
        PieChart.Data(x, y)
      }))
    }

    val pieChart1 = new PieChart {
      title = "Pie Chart"
      data = ObservableBuffer.from(dataPairs.map({ case (x, y) =>
        PieChart.Data(x, y)
      }))
    }

    val pieChart2 = new PieChart {
      title = "Pie Chart"
      data = ObservableBuffer.from(dataPairs.map({ case (x, y) =>
        PieChart.Data(x, y)
      }))
    }

    val pieChart3 = new PieChart {
      title = "Pie Chart"
      data = ObservableBuffer.from(dataPairs.map({ case (x, y) =>
        PieChart.Data(x, y)
      }))
    }

    val charts = new VBox:
      children = Array(pieChart, pieChart1, pieChart2, pieChart3)

    val scroll = new ScrollPane:
      content = charts

    val splitPane = new SplitPane:
      items ++= Seq(left, scroll)

    val rootVBox = new VBox:
      children = Array(menu, toolBar, splitPane)


    val root = rootVBox

    stage.scene = Scene(parent = root)