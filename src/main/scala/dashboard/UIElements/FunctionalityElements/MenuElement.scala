package dashboard.UIElements.FunctionalityElements

import dashboard.UIElements.FunctionalityElements.Alerts.{getBarChartAlert, getPieChartAlert, getPortfolioTileAlert, getScatterPlotAlert, getStockTileAlert, getXYChartAlert}
import scalafx.scene.control.{Menu, MenuBar, MenuItem}
import dashboard.UI.charts
import dashboard.UIElements.DataAnalysisTools.PortfolioPieChart.getPieChart
import dashboard.UIElements.DataAnalysisTools.ReturnScatterPlot.getScatterPlot
import dashboard.UIElements.DataAnalysisTools.Tile.{getPortfolioTile, getStockTile}
import dashboard.UIElements.DataAnalysisTools.TimeSeriesChart.getTimeSeriesChart
import dashboard.UIElements.DataAnalysisTools.VolymeBarChart.getVolymeBarChart
import dashboard.UIElements.FunctionalityElements.RightSplit.addElementToPane

object MenuElement:

  val (
    barChartMenuItem,
    pieChartMenuItem,
    scatterPlotMenuItem,
    portfolioTileMenuItem,
    stockTileMenuItem,
    xyChartMenuItem
    ) = (
    MenuItem("Bar Chart"),
    MenuItem("Pie Chart"),
    MenuItem("Scatter Plot"),
    MenuItem("Portfolio tile"),
    MenuItem("Stock tile"),
    MenuItem("XY chart")
  )

  private val (barChartAlert, barChartChoiceBox) = getBarChartAlert
  barChartMenuItem.onAction = (event) =>
    val result = barChartAlert.showAndWait()
    val company = barChartChoiceBox.value.value
    val barChart = getVolymeBarChart(company)
    result match
      case Some(_) => addElementToPane(barChart)
      case None =>

  private val (xyChartAlert, xyChartChoiceBox) = getXYChartAlert
  xyChartMenuItem.onAction = (event) =>
    val result = xyChartAlert.showAndWait()
    val company = xyChartChoiceBox.value.value
    val xyChart = getTimeSeriesChart(company)
    result match
      case Some(_) => addElementToPane(xyChart)
      case None =>

  private val (pieChartAlert, portfolioChoiseBoxPieChart) = getPieChartAlert
  pieChartMenuItem.onAction = (event) =>
    val result = pieChartAlert.showAndWait()
    val portfolio = portfolioChoiseBoxPieChart.value.value
    val pieChart = getPieChart(portfolio)
    result match
      case Some(_) => addElementToPane(pieChart)
      case None =>

  private val (portfolioTileAlert, portfolioChoiceBoxTile) = getPortfolioTileAlert
  portfolioTileMenuItem.onAction = (event) =>
    val result = portfolioTileAlert.showAndWait()
    val portfolio = portfolioChoiceBoxTile.value.value
    val portfolioTile = getPortfolioTile(portfolio)
    result match
      case Some(_) => addElementToPane(portfolioTile) 
      case None =>

  private val (stockTileAlert, stockTileChoiseBox) = getStockTileAlert
  stockTileMenuItem.onAction = (event) =>
    val result = stockTileAlert.showAndWait()
    val company = stockTileChoiseBox.value.value
    val stockTile = getStockTile(company)
    result match
      case Some(_) => addElementToPane(stockTile) 
      case None =>

  private val (scatterPlotAlert, scatterPlotChoiceBox1, scatterPlotChoiceBox2, yearChoiceBox) =
    getScatterPlotAlert
  scatterPlotMenuItem.onAction = (event) =>
    val result = scatterPlotAlert.showAndWait()
    val companies = Array(scatterPlotChoiceBox1.value.value, scatterPlotChoiceBox2.value.value)
    val year = yearChoiceBox.value.value
    val scatterPlot = getScatterPlot(companies, year)
    result match
      case Some(_) => addElementToPane(scatterPlot)
      case None =>

  def getMenuElement: MenuBar =

    val fileOperations = new Menu("File"):
      items = Array(
        MenuItem("Open"),
        MenuItem("Save")
      )

    val createChart = new Menu("New"):
      items = Array(
        barChartMenuItem,
        pieChartMenuItem,
        scatterPlotMenuItem,
        portfolioTileMenuItem,
        stockTileMenuItem,
        xyChartMenuItem
      )

    val menu = new MenuBar:
      menus = Array(fileOperations, createChart)

    menu