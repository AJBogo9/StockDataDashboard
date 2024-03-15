package charts

import lib.Api.getTimeSeries
import lib.Utils.{getFirstDaysOfMonths, xySeriesScatter}
import scalafx.application.JFXApp3
import scalafx.collections.ObservableBuffer
import scalafx.geometry.Side
import scalafx.scene.Scene
import scalafx.scene.chart.{NumberAxis, ScatterChart}


object ReturnScatterPlot:

  def getScatterPlot(companies: Array[String]) =
    
    // TODO: Create scatter plot based on companies given as parameter
    
    val apiData = getTimeSeries("Nvidia")
    val dates = apiData.keys.toList
    // Get the first day of each month from the list of dates
    val firstDaysOfMonths = getFirstDaysOfMonths(dates)
    val dataPairs = firstDaysOfMonths.map(date => (date, apiData(date)("1. open")))

    var lastMonthPrice = dataPairs.head._2
    var usableData: Array[(Int, Double)] = Array()
    var month = 0
    for tuple <- dataPairs do
      usableData = usableData :+ (month, tuple(1) - lastMonthPrice)
      lastMonthPrice = tuple(1)
      month += 1

    val apiData1 = getTimeSeries("Apple")
    val dates1 = apiData1.keys.toList
    val firstDaysOfMonths1 = getFirstDaysOfMonths(dates1)
    val dataPairs1 = firstDaysOfMonths1.map(date => (date, apiData1(date)("1. open")))

    var lastMonthPrice1 = dataPairs1.head._2
    var usableData1: Array[(Int, Double)] = Array()
    var month1 = 0
    for tuple <- dataPairs1 do
      usableData1 = usableData1 :+ (month1, tuple(1) - lastMonthPrice1)
      lastMonthPrice1 = tuple(1)
      month1 += 1
    
    new ScatterChart(NumberAxis("Months held"), NumberAxis("Return per month ($)")):
      title = "Scatter Chart"
      legendSide = Side.Right
      data = ObservableBuffer(
        xySeriesScatter(
          "NVDA return",
          usableData
        ),
        xySeriesScatter(
          "AAPL return",
          usableData1
        )
      )