import scalafx.application.JFXApp3
import scalafx.collections.ObservableBuffer
import scalafx.scene.Scene
import scalafx.scene.chart.{PieChart, XYChart}
import scalafx.scene.chart.PieChart.Data

object PieChart extends JFXApp3 {
  override def start(): Unit = {
    val dataPairs = Seq(("Sun", 25), ("IBM", 17), ("HP", 25), ("Dell", 27), ("Apple", 5))

    stage = new JFXApp3.PrimaryStage {
      title = "PieChartDemo"
      scene = new Scene {
        root = new PieChart {
          title = "Pie Chart"
          clockwise = false
          data = ObservableBuffer.from(dataPairs.map({ case (x, y) => PieChart.Data(x, y) }))
        }
      }

      def xySeries(name: String, data: Seq[(String, Int)]) =
        XYChart.Series[String, Number](
          name,
          ObservableBuffer.from(
            data.map({ case (x, y) => XYChart.Data[String, Number](x, y) }))
        )
    }
  }
}