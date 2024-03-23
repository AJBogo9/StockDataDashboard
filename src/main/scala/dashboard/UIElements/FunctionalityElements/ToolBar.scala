package dashboard.UIElements.FunctionalityElements

import scalafx.scene.control.{Button, Label, Separator, ToolBar}

object ToolBar:

  val (
    selectButton,
    duplicateButton,
    hideButton,
    removeButton,
    refreshButton,
    lastRefreshed
  ) = (
    Button("Select"),
    Button("Duplicate"),
    Button("Hide"),
    Button("Remove"),
    Button("Refresh"),
    Label("Last Refreshed: DUNNO??")
  )

  def getToolBarElement: ToolBar =

    val toolBar = new ToolBar:
      items = Array(
        selectButton,
        new Separator,
        duplicateButton,
        hideButton,
        removeButton,
        new Separator,
        refreshButton,
        lastRefreshed
      )

    toolBar