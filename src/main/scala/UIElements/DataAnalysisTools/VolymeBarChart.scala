package UIElements.DataAnalysisTools

import lib.Api.getTimeSeries
import lib.Utils.xySeries
import scalafx.application.JFXApp3
import scalafx.collections.ObservableBuffer
import scalafx.geometry.Side
import scalafx.scene.Scene
import scalafx.scene.chart.{BarChart, CategoryAxis, NumberAxis, XYChart}

import scala.util.Sorting.quickSort

object VolymeBarChart:
  def getVolymeBarChart(company: String): BarChart[String, Number] =
    val ApiData = getTimeSeries(company)
    val dates = ApiData.keys.toArray
    quickSort(dates)
    var datesAndVolyme: Seq[(String, Double)] = Seq()
    for date <- dates do
      datesAndVolyme = datesAndVolyme :+ (date, ApiData(date)("5. volume"))

    new BarChart(CategoryAxis("Date"), NumberAxis("Volyme")):
      title = s"$company trading volyme"
      legendSide = Side.Right
      data = ObservableBuffer(
        xySeries(company, datesAndVolyme)
      )