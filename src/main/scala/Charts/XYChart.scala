package Charts


import lib.Api.getTimeSeries
import Charts.CategoryLineChart.stage
import scalafx.application.JFXApp3
import scalafx.collections.ObservableBuffer
import scalafx.geometry.Side
import scalafx.scene.Scene
import scalafx.scene.chart.*

object CategoryLineChart extends JFXApp3 {
  override def start(): Unit = {
    val timeSeriesNVDA = getTimeSeries("Nvidia")
    var dateValuePairsNVDA = timeSeriesNVDA.map(date => (date._1, date._2("1. open"))).toSeq.sortBy(_._1)
    val timeSeriesAAPL = getTimeSeries("Apple")
    var dateValuePairsAAPL = timeSeriesAAPL.map(date => (date._1, date._2("1. open"))).toSeq.sortBy(_._1)


    stage = new JFXApp3.PrimaryStage {
      title = "CategoryLineChartDemo"
      scene = new Scene {
        root = new LineChart(CategoryAxis("Date"), NumberAxis("Price")) {
          title = "LineChart with Category Axis"
          legendSide = Side.Right
          data = ObservableBuffer(
            xySeries("Nvidia", dateValuePairsNVDA),
            xySeries("Apple", dateValuePairsAAPL)
          )
        }
      }
    }

    def xySeries(name: String, data: Seq[(String, Double)]) =
      XYChart.Series[String, Number](
        name,
        ObservableBuffer.from(
          data.map({ case (x, y) => XYChart.Data[String, Number](x, y) }))
      )
  }
}