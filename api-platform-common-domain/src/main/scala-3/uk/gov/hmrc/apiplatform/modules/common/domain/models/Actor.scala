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

/** Actor refers to actors that triggered an event
  */
type Actor = Actors.AppCollaborator | Actors.GatekeeperUser | Actors.ScheduledJob | Actors.Process | Actors.Unknown.type

object Actors {

  /** A third party developer who is a collaborator on the application for the event this actor is responsible for triggering
    *
    * @param email
    *   the developers email address at the time of the event
    */
  case class AppCollaborator(email: LaxEmailAddress)

  /** A gatekeeper stride user (typically SDST)
    *
    * @param user
    *   the stride user fullname of the gatekeeper user who triggered the event on which they are the actor
    */
  case class GatekeeperUser(user: String)

  /** An automated job
    *
    * @param jobId
    *   the job name or instance of the job possibly as a UUID
    */
  case class ScheduledJob(jobId: String)

  /** A process that has been triggered by something other than a schedule
    *
    * @param name
    *   the process name
    */
  case class Process(name: String)

  /** Unknown source - probably 3rd party code such as PPNS invocations
    */
  case object Unknown
}

extension (a: Actor) {

  def actorType: ActorType = a match {
    case _: Actors.GatekeeperUser  => ActorType.Gatekeeper
    case _: Actors.AppCollaborator => ActorType.Collaborator
    case _: Actors.ScheduledJob    => ActorType.ScheduledJob
    case _: Actors.Process         => ActorType.Process
    case _                         => ActorType.Unknown
  }
}

object Actor {
  import play.api.libs.json._
  import uk.gov.hmrc.play.json.Union
  import play.api.libs.functional.syntax._
  import play.api.libs.json.Reads._

  given OWrites[Actors.AppCollaborator] = Json.writes[Actors.AppCollaborator]
  given OWrites[Actors.GatekeeperUser]  = Json.writes[Actors.GatekeeperUser]
  given OWrites[Actors.ScheduledJob]    = Json.writes[Actors.ScheduledJob]
  given OWrites[Actors.Process]         = Json.writes[Actors.Process]

  given Reads[Actors.AppCollaborator] =
    ((JsPath \ "id").read[String] or (JsPath \ "email").read[String]).map(s => Actors.AppCollaborator(LaxEmailAddress(s)))
  given Reads[Actors.GatekeeperUser]  = ((JsPath \ "id").read[String] or (JsPath \ "user").read[String]).map(Actors.GatekeeperUser(_))
  given Reads[Actors.ScheduledJob]    = ((JsPath \ "id").read[String] or (JsPath \ "jobId").read[String]).map(Actors.ScheduledJob(_))
  given Reads[Actors.Process]         = Json.reads[Actors.Process]

  import uk.gov.hmrc.apiplatform.modules.common.domain.services.EnumJsonHelper.asScreamingSnakeCase

  given OFormat[Actor] = Union.from[Actor]("actorType")
    .and[Actors.AppCollaborator](ActorType.Collaborator.asScreamingSnakeCase)
    .and[Actors.GatekeeperUser](ActorType.Gatekeeper.asScreamingSnakeCase)
    .and[Actors.ScheduledJob](ActorType.ScheduledJob.asScreamingSnakeCase)
    .and[Actors.Process](ActorType.Process.asScreamingSnakeCase)
    .andType[Actors.Unknown.type](ActorType.Unknown.asScreamingSnakeCase, () => Actors.Unknown)
    .format
}
