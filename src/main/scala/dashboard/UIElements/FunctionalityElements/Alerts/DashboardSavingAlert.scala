package dashboard.UIElements.FunctionalityElements.Alerts


import dashboard.UI.stage
import scalafx.geometry.Pos
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control.{Alert, TextField}
import scalafx.scene.layout.VBox

object DashboardSavingAlert:
  def getDashboardSavingAlert =
    val fileNameTextField = new TextField

    // TODO: be able to validate text

    val alertContent = new VBox:
      children = Array(fileNameTextField)
      alignment = Pos.Center

    var alert = new Alert(AlertType.Confirmation):
      initOwner(stage)
      title = "Save dashboard"
      headerText = "Enter dashboard name"
      dialogPane().setContent(alertContent)

    (alert, fileNameTextField)