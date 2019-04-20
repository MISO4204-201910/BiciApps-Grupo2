package controllers.kilometraje

import play.api.mvc._
import javax.inject.Inject
import play.api.Configuration

class HomeController @Inject()(
  val controllerComponents: ControllerComponents,
  config: Configuration
  ) extends BaseController {

  val isModuleOn = config.get[Boolean]("gamification.kilometraje")

  def validateModuleRoute(status: Result)(implicit request: Request[_]) = {
    if (isModuleOn) {
      status
    } else {
      NotFound
    }
  }

  def index = Action { implicit request =>
    validateModuleRoute(
      Ok("kilometraje")
    )
  }
}