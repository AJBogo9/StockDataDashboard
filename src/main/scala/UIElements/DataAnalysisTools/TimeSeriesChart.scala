package UIElements.DataAnalysisTools

import lib.Api.getTimeSeries
import lib.Utils.xySeries
import scalafx.application.JFXApp3
import scalafx.collections.ObservableBuffer
import scalafx.geometry.Side
import scalafx.scene.Scene
import scalafx.scene.chart.*

object TimeSeriesChart:
  def getTimeSeriesChart(company: String) =
    val timeSeries = getTimeSeries(company)
    var dateValuePairs = timeSeries.map(date => (date._1, date._2("1. open"))).toSeq.sortBy(_._1)
    
    new LineChart(CategoryAxis("Date"), NumberAxis("Price")):
      title = s"$company stock value"
      legendSide = Side.Right
      data = ObservableBuffer(
        xySeries(company, dateValuePairs)
      )