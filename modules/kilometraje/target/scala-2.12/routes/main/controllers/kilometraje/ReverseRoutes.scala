// @GENERATOR:play-routes-compiler
// @SOURCE:/Users/davidortiz/Documents/LPProyecto/biciapps/modules/kilometraje/conf/kilometraje.routes
// @DATE:Wed Apr 17 23:06:05 COT 2019

import play.api.mvc.Call


import _root_.controllers.Assets.Asset

// @LINE:1
package controllers.kilometraje {

  // @LINE:1
  class ReverseHomeController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:1
    def index(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "index")
    }
  
  }


}
