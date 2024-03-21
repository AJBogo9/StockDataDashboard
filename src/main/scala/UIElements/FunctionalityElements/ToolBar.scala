package UIElements.FunctionalityElements

import scalafx.scene.control.{Button, Separator, ToolBar}

object ToolBar:
  def getToolBarElement: ToolBar = 
  
    val toolBar = new ToolBar:
      items = Array(
        Button("Select"),
        new Separator,
        Button("Duplicate"),
        Button("Hide"),
        Button("Remove"),
        new Separator,
        Button("Update")
      )
    
    toolBar