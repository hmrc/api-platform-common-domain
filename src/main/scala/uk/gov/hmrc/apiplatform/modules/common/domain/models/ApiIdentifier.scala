/*
 * Copyright 2023 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.apiplatform.modules.common.domain.models

final case class ApiIdentifier(context: ApiContext, versionNbr: ApiVersionNbr) {
  def asText(separator: String): String = s"${context.value}$separator${versionNbr.value}"
}

object ApiIdentifier {
  import play.api.libs.json._
  import play.api.libs.functional.syntax._

  private val readsApiIdentifier: Reads[ApiIdentifier] = (
    (JsPath \ "context").read[ApiContext] and
      (
        (JsPath \ "version").read[ApiVersionNbr] or   // Existing field name
          (JsPath \ "versionNbr").read[ApiVersionNbr] // Future aim to be this field name
      )
  )(ApiIdentifier.apply _)

  private val writesApiIdentifier: OWrites[ApiIdentifier] = (
    (JsPath \ "context").write[ApiContext] and
      (JsPath \ "version").write[ApiVersionNbr] // TODO - change to versionNbr once all readers are safe
  )(unlift(ApiIdentifier.unapply))

  implicit val formatApiIdentifier: OFormat[ApiIdentifier]    = OFormat[ApiIdentifier](readsApiIdentifier, writesApiIdentifier)
  implicit val keyReadsApiVersionNbr: KeyReads[ApiVersionNbr] = key => JsSuccess(ApiVersionNbr(key))
  implicit val keyWritesApiVersion: KeyWrites[ApiVersionNbr]  = _.value
  implicit val ordering: Ordering[ApiIdentifier]              = Ordering.by[ApiIdentifier, String](_.context.value).orElseBy(_.versionNbr.value)

// $COVERAGE-OFF$
  def random: ApiIdentifier = ApiIdentifier(ApiContext.random, ApiVersionNbr.random)
// $COVERAGE-ON$
}
