package dashboard

import dashboard.UIElements.FunctionalityElements.LeftSplit.getLeftSplit
import dashboard.UIElements.FunctionalityElements.MenuElement.*
import dashboard.UIElements.FunctionalityElements.RightSplit.getRightSplit
import dashboard.UIElements.FunctionalityElements.ToolBar.*
import scalafx.application.JFXApp3
import scalafx.scene.Scene
import scalafx.scene.control.SplitPane
import scalafx.scene.layout.{Pane, VBox}

object UI extends JFXApp3:

  def start() =
    val menu = getMenuElement
    val toolBar = getToolBarElement

    val leftSplit = getLeftSplit
    val rightSplit = getRightSplit
    val splitPane = new SplitPane:
      items ++= Seq(leftSplit, rightSplit)
      dividerPositions = 0.3

    val mainVBox = new VBox:
      children = Array(menu, toolBar, splitPane)

    stage = new JFXApp3.PrimaryStage:
      title = "Personal Portfolio Dashboard"
      scene = new Scene(700, 500):
        root = mainVBox