package dashboard.UIElements.DataAnalysisTools

import dashboard.UIElements.FunctionalityElements.RightSplit.componentWidthAndHeigth
import dashboard.lib.Api.{getPortfolioData, getTimeSeries}
import scalafx.collections.ObservableBuffer
import scalafx.geometry.Side
import scalafx.scene.chart.PieChart
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control.{Alert, ButtonType}

import scala.collection.mutable.Map

object PortfolioPieChart:

  def getPieChart(portfolioName: String) =
    val portfolioData: Map[String, Map[String, String]] = 
      getPortfolioData(portfolioName)
    val stockNames = portfolioData.keys
    var dataPairs: Seq[(String, Double)] = Seq()
    for key <- stockNames do
      val timeSeriesData: Map[String, Map[String, Double]] = getTimeSeries(key)
      val dates: Array[String] = timeSeriesData.keys.toArray.sorted
      val latestDate = dates.last
      val price: Double = timeSeriesData(latestDate)("1. open")
      dataPairs = dataPairs :+ (key, (portfolioData(key)("Quantity").toInt * price))

    val (chartWidth, chartHeigth) = componentWidthAndHeigth

    val pieChart = new PieChart:
      title = s"Portfolio pie chart $portfolioName"
      clockwise = false
      legendSide = Side.Bottom
      prefWidth = chartWidth
      prefHeight = chartHeigth
      data = ObservableBuffer.from(dataPairs.map({ 
        case (x, y) => PieChart.Data(x, y)
      }))

    pieChart