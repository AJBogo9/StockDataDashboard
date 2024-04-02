package dashboard.UIElements.DataAnalysisTools

import dashboard.UIElements.FunctionalityElements.RightSplit.componentWidthAndHeigth
import dashboard.lib.Api.getTimeSeries
import scalafx.application.JFXApp3
import scalafx.collections.ObservableBuffer
import scalafx.geometry.Side
import scalafx.scene.Scene
import scalafx.scene.chart.XYChart.Series
import scalafx.scene.chart.{NumberAxis, ScatterChart, XYChart}

import java.time.LocalDate
import scala.:+
import scala.util.Sorting.quickSort


object ReturnScatterPlot:

  def getScatterPlot(companies: Array[String], year: Int) =

    var companyData: Array[javafx.scene.chart.XYChart.Series[Number, Number]] = Array()
    for company <- companies do
      val apiData = getTimeSeries(company)
      val dates = apiData.keys.toList

      val datesOfYear = dates.filter(_.contains(year.toString)).sorted
      val dataPairs = datesOfYear.map( date => (date, apiData(date)("1. open")))

      var monthAndReturn: Array[(Int, Double)] = Array((1, 0.0))
      for i <- 1 until dataPairs.length do
        val (lastMonthPrice, thisMonthPrice) = (dataPairs(i - 1)._2, dataPairs(i)._2)
        val difference = thisMonthPrice - lastMonthPrice
        val month = dataPairs(i)._1.split("-")(1).toInt
        monthAndReturn = monthAndReturn :+ (month, difference)


      companyData = companyData :+ xySeriesScatter(s"$company return", monthAndReturn)



    val chartData = ObservableBuffer.from(companyData)

    val (chartWidth, chartHeigth) = componentWidthAndHeigth
    
    val chart = new ScatterChart(NumberAxis("Month", 1.0, 12.0, 1.0), NumberAxis(s"Return per month ($$)")):
      title = s"Stock Growth Per Month In $year"
      legendSide = Side.Bottom
      prefWidth = chartWidth
      prefHeight = chartHeigth
      data = chartData

    chart


  def xySeriesScatter(name: String, data: Seq[(Int, Double)]) =
    val dataInObservableBuffer = ObservableBuffer.from(
      data.map((x, y) => XYChart.Data[Number, Number](x, y))
    )
    XYChart.Series[Number, Number](name, dataInObservableBuffer)