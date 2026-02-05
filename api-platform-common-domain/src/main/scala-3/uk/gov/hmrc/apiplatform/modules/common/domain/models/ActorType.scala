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

import uk.gov.hmrc.apiplatform.modules.common.domain.services.SealedTraitJsonFormatting

enum ActorType:
  case Collaborator, Gatekeeper, Scheduled_Job, Process, Unknown

object ActorType {
  val displayText: ActorType => String = _ match {
    case ActorType.Collaborator  => "Application Collaborator"
    case ActorType.Gatekeeper    => "Gatekeeper User"
    case ActorType.Scheduled_Job => "Scheduled Job"
    case ActorType.Process       => "Process"
    case ActorType.Unknown       => "Unknown"
  }

  def apply(text: String): Option[ActorType] = ActorType.values.find(_.toString.equalsIgnoreCase(text))

  def unsafeApply(text: String): ActorType = {
    apply(text).getOrElse(throw new RuntimeException(s"$text is not a valid Actor Type"))
  }

  import play.api.libs.json.Format

  given Format[ActorType] = SealedTraitJsonFormatting.createFormatFor[ActorType]("Actor Type", apply(_), at => at.toString.toUpperCase())

}
