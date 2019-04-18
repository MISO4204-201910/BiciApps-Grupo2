package controllers.kilometraje

import play.api.mvc._
import javax.inject.Inject

class HomeController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  def index = Action { implicit request =>
    Ok("kilometraje")
  }
}