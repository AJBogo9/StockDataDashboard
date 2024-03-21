package UIElements.FunctionalityElements

import scalafx.scene.control.{Menu, MenuBar, MenuItem}

object MenuElement:

  val (
    barChartMenuItem,
    pieChartMenuItem,
    scatterPlotMenuItem,
    tileMenuItem,
    xyChartMenuItem
    ) = (
    MenuItem("Bar Chart"),
    MenuItem("Pie Chart"),
    MenuItem("Scatter Plot"),
    MenuItem("Tile"),
    MenuItem("XY chart")
  )

  def getMenuElement: MenuBar =

    val fileOperations = new Menu("File"):
      items = Array(
        MenuItem("Open"),
        MenuItem("Save")
      )

    val createChart = new Menu("New"):
      items = Array(barChartMenuItem, pieChartMenuItem, scatterPlotMenuItem, tileMenuItem, xyChartMenuItem)

    val menu = new MenuBar:
      menus = Array(fileOperations, createChart)

    menu