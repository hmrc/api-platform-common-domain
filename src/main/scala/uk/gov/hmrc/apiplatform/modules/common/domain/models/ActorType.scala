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

import uk.gov.hmrc.apiplatform.modules.common.domain.models.Actor
import uk.gov.hmrc.apiplatform.modules.common.domain.services.SealedTraitJsonFormatting

sealed abstract class ActorType {
  final lazy val displayText: String = ActorType.displayText(this)
}

object ActorType {
  case object COLLABORATOR extends ActorType

  case object GATEKEEPER extends ActorType

  case object SCHEDULED_JOB extends ActorType

  case object UNKNOWN extends ActorType

  val values: Set[ActorType] = Set(COLLABORATOR, GATEKEEPER, SCHEDULED_JOB, UNKNOWN)

  val displayText: ActorType => String = _ match {
    case COLLABORATOR  => "Application Collaborator"
    case GATEKEEPER    => "Gatekeeper User"
    case SCHEDULED_JOB => "Scheduled Job"
    case UNKNOWN       => "Unknown"
  }

  def apply(text: String): Option[ActorType] = ActorType.values.find(_.toString == text.toUpperCase)

  def unsafeApply(text: String): ActorType = {
    apply(text).getOrElse(throw new RuntimeException(s"$text is not a valid Actor Type"))
  }

  import play.api.libs.json.Format

  implicit val formatActorType: Format[ActorType] = SealedTraitJsonFormatting.createFormatFor[ActorType]("Actor Type", apply(_))

  def actorType(a: Actor): ActorType = a match {
    case _: Actors.GatekeeperUser  => GATEKEEPER
    case _: Actors.AppCollaborator => COLLABORATOR
    case _: Actors.ScheduledJob    => SCHEDULED_JOB
    case _                         => UNKNOWN
  }

}
