import ujson.Value

import java.io.FileWriter
import java.nio.file.{Files, Paths}
import java.time.LocalDate
import scala.collection.mutable.Map
import scala.io.Source




object Api:

  val lastRefreshed = LocalDate.parse(readJsonFromFile("src/main/scala/data/TIME_SERIES_DAILY_AAPL.json")("Meta Data")("3. Last Refreshed").toString)
  // if lastRefreshed.isEqual(LocalDate.now) then

  def apiKey: String =
    val apiKeyFile = "apiKey.txt"
    val source = Source.fromFile(apiKeyFile)
    val key = source.getLines().mkString
    source.close()
    key

  def getJSONStringFromWeb(url: String): String =
    val html = Source.fromURL(url)
    html.mkString

  def saveDatatoFile(dstPath: String, text: String) =
    val fw = new FileWriter(dstPath, false)
    fw.write(text)
    fw.close()

  def getDataAndSave(url: String, dst: String): String  =
    val text = getJSONStringFromWeb(url)
    saveDatatoFile(dst, text)
    text

  def readTextFromFile(src: String): String =
    val bufferedSource = Source.fromFile(src)
    val text = bufferedSource.mkString
    bufferedSource.close()
    text

  def readJsonFromFile(src: String): Map[String, Map[String, Value]] =
    val rawText = readTextFromFile(src)
    val data: Map[String, ujson.Value] = ujson.read(rawText).obj

    var mappedData: Map[String, Map[String, ujson.Value]] = Map()
    for key <- data.keys do
      mappedData += key -> data(key).obj

    mappedData


  def test(): Unit =

    val companies = Map("Apple" -> "AAPL", "Nvidia" -> "NVDA", "Microsoft" -> "MSFT")


    val symbol = companies("Apple")
    val function = "TIME_SERIES_DAILY"
    val key: String = apiKey


    val url1 = s"https://www.alphavantage.co/query?function=$function&symbol=$symbol&apikey=$key"
    val url2 = "https://catfact.ninja/fact"
    val url3 = "https://www.fruityvice.com/api/fruit/all"


    val dstFolder = "src/main/scala/data"
    val fileName = s"${function}_$symbol.json"
    val dst = s"$dstFolder/$fileName"

    println(dst)

    Files.createDirectories(Paths.get(dstFolder))
    // getDataAndSave(url1, dst)


    val retData = readJsonFromFile(dst)

    println(retData("Meta Data")("3. Last Refreshed"))
