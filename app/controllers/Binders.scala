package controllers

import models.registro.TipoRegistro
import play.api.mvc.QueryStringBindable

object Binders {

  implicit def tipoRegistroQueryStringBindable = new QueryStringBindable[TipoRegistro] {
    def bind(key: String, params: Map[String, Seq[String]]): Option[Either[String, TipoRegistro]] = {
      params.get(key).flatMap(_.headOption).map { value =>
        Option(TipoRegistro.valueOf(value)) match {
          case Some(blockchain) => Right(blockchain)
          case _ => Left(s"tipo de registro invalido '$value'")
        }
      }
    }

    def unbind(key: String, tipoRegistro: TipoRegistro): String = {
      s"$key=${tipoRegistro.name()}"
    }
  }

}