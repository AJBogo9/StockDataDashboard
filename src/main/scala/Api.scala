import ujson.Value
import java.io.FileWriter
import java.nio.file.{Files, Paths}
import java.time.LocalDate
import scala.collection.mutable.Map
import scala.io.Source




object Api:

  private val filesRefreshedToday: Boolean = LocalDate.parse(getMetaData("src/main/scala/data/TIME_SERIES_DAILY_AAPL.json")("3. Last Refreshed")) == LocalDate.now().minusDays(1)
  private val dstFolder = "src/main/scala/data"
  private val apiKey: String =
    val apiKeyFile = "apiKey.txt"
    val source = Source.fromFile(apiKeyFile)
    val key = source.getLines().mkString
    source.close()
    key

  def getDataFromWebAndSaveToFile(url: String, dst: String): String =
    if !filesRefreshedToday then // does not use up API calls if data is up to date
      // get data from api
      val html = Source.fromURL(url)
      val text = html.mkString

      // Write to File
      val fw = new FileWriter(dst, false)
      fw.write(text)
      fw.close()

      return text

    ""


  def readTextFromFile(src: String): String =

    val bufferedSource = Source.fromFile(src)
    val text = bufferedSource.mkString
    bufferedSource.close()
    text

  def getMetaData(src: String) =

    val rawText = readTextFromFile(src)
    val metaData: Value = ujson.read(rawText).obj("Meta Data")
    val usableMetaData = metaData.obj
    val ret: Map[String, String] = Map()
    for key <- usableMetaData.keys do
      ret += key -> usableMetaData(key).str
    ret

  def getTimeSeries(src: String) =

    val rawText = readTextFromFile(src)
    val timeSeries: Map[String, Value] = ujson.read(rawText).obj("Time Series (Daily)").obj
    var retMap: Map[String, Map[String, Double]] = Map()
    for date <- timeSeries.keys do

      // converts Value type to Double
      val numbers: Map[String, Value] = timeSeries(date).obj
      val newNumbers: Map[String, Double] = Map()
      for key <- numbers.keys do
        newNumbers += key -> numbers(key).str.toDouble
      retMap += date -> newNumbers

    retMap



  def test(): Unit =

    val companies = Map("Apple" -> "AAPL", "Nvidia" -> "NVDA", "Microsoft" -> "MSFT")


    val symbol = companies("Apple")
    val function = "TIME_SERIES_DAILY"

    val url1 = s"https://www.alphavantage.co/query?function=$function&symbol=$symbol&apikey=$apiKey"

    val fileName = s"${function}_$symbol.json"
    val src = s"$dstFolder/$fileName"

    Files.createDirectories(Paths.get(dstFolder))

    // src/main/scala/data/TIME_SERIES_DAILY_AAPL.json

    // getDataFromWebAndSaveToFile(url1, src)


    getTimeSeries(src)

