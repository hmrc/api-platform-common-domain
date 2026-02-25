/*
 * Copyright 2025 HM Revenue & Customs
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

import java.util.UUID
import java.{util => ju}

import play.api.libs.json.{Format, Json}

final case class OrganisationId(value: UUID) extends AnyVal {
  override def toString: String = value.toString
}

object OrganisationId {
  implicit val orgNameFormat: Format[OrganisationId] = Json.valueFormat[OrganisationId]

  def unsafeApply(raw: String): OrganisationId = OrganisationId(UUID.fromString(raw))

  // $COVERAGE-OFF$
  def random: OrganisationId = OrganisationId(ju.UUID.randomUUID)
  // $COVERAGE-ON$
}
