//package Logic
//
//import Gui.KeyPolling
//import com.edencoding.animation.GameLoopTimer
//import com.edencoding.models.Entity
//import com.edencoding.rendering.Renderer
//import scalafx.fxml.Initializable
//import scalafx.scene.canvas.Canvas
//import scalafx.scene.image.Image
//import scalafx.scene.input.KeyCode
//import scalafx.scene.layout.AnchorPane
//import scala.net.URL
//import scala.util.ResourceBundle
//import scalafx.Includes._
//
///**
// * Based on java code:
// * author: Ed Eden-Rump
// * project: EdenCoding Space Shooter
// * */
//class GameController extends Initializable {
//  var gameCanvas: Canvas = _
//  var gameAnchor: AnchorPane = _
//  private[controllers] val keys = KeyPolling.getInstance
//  private val player = new Nothing(new Image(getClass.getResourceAsStream("/img/ship.png")))
//
//  override def initialize(location: URL, resources: ResourceBundle): Unit = {
//    initialiseCanvas()
//    player.setDrawPosition(350, 200)
//    player.setScale(0.5f)
//    val renderer = new Nothing(this.gameCanvas)
//    renderer.addEntity(player)
//    renderer.setBackground(new Image(getClass.getResourceAsStream("/img/SpaceBackground.jpg")))
//    val timer: Nothing = new Nothing() {
//      def tick(secondsSinceLastFrame: Float): Unit = {
//        renderer.prepare
//        updatePlayerMovement(secondsSinceLastFrame)
//        renderer.render
//      }
//    }
//    timer.start
//  }
//
//  private def initialiseCanvas(): Unit = {
//    gameCanvas.widthProperty.bind(gameAnchor.widthProperty)
//    gameCanvas.heightProperty.bind(gameAnchor.heightProperty)
//  }
//
//  private def updatePlayerMovement(frameDuration: Float): Unit = {
//    if (keys.isDown(KeyCode.Up)) player.addThrust(20 * frameDuration)
//    else if (keys.isDown(KeyCode.Down)) player.addThrust(-20 * frameDuration)
//    if (keys.isDown(KeyCode.Right)) player.addTorque(120f * frameDuration)
//    else if (keys.isDown(KeyCode.Left)) player.addTorque(-120f * frameDuration)
//    player.update
//  }
//}