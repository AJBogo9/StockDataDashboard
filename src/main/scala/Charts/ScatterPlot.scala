package Charts

import Charts.ScatterPlot.stage
import lib.Api.getTimeSeries
import scalafx.application.JFXApp3
import scalafx.collections.ObservableBuffer
import scalafx.geometry.Side
import scalafx.scene.Scene
import scalafx.scene.chart.{NumberAxis, ScatterChart, XYChart}

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import scala.util.Sorting.quickSort


object ScatterPlot extends JFXApp3 {
  override def start(): Unit = {
    def parseDate(dateString: String): LocalDate = {
      LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
    }
    // Function to convert LocalDate objects into strings
    def formatDate(date: LocalDate): String = {
      date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
    }
    // Function to get the first day of each month from a list of dates
    def getFirstDaysOfMonths(dates: List[String]): Array[String] = {
      val localDates = dates.map(parseDate)
      val firstDaysOfMonths = localDates.groupBy(date => (date.getYear, date.getMonthValue))
        .mapValues(_.minBy(_.getDayOfMonth))
        .values.toArray
      val ret = firstDaysOfMonths.map(formatDate)
      quickSort(ret)
      ret
    }
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

    stage = new JFXApp3.PrimaryStage {
      title = "ScatterChartDemo"
      scene = new Scene {
        root = new ScatterChart(NumberAxis("Months held"), NumberAxis("Return per month ($)")) {
          title = "Scatter Chart"
          legendSide = Side.Right
          data = ObservableBuffer(
            xySeries(
              "NVDA return",
              usableData.toSeq
            ),
          )
        }
      }
    }
    def xySeries(name: String, data: Seq[(Int, Double)]) =
      XYChart.Series[Number, Number](
        name,
        ObservableBuffer.from(
          data.map({ case (x, y) => XYChart.Data[Number, Number](x, y) }))
    )
  }
}