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
sealed trait Actor

object Actors {

  /** A third party developer who is a collaborator on the application for the event this actor is responsible for triggering
    *
    * @param email
    *   the developers email address at the time of the event
    */
  case class AppCollaborator(email: LaxEmailAddress) extends Actor

  /** A gatekeeper stride user (typically SDST)
    *
    * @param user
    *   the stride user fullname of the gatekeeper user who triggered the event on which they are the actor
    */
  case class GatekeeperUser(user: String) extends Actor

  /** An automated job
    *
    * @param jobId
    *   the job name or instance of the job possibly as a UUID
    */
  case class ScheduledJob(jobId: String) extends Actor

  /** Unknown source - probably 3rd party code such as PPNS invocations
    */
  case object Unknown extends Actor
}

object Actor {
  import play.api.libs.json._
  import uk.gov.hmrc.play.json.Union
  import play.api.libs.functional.syntax._
  import play.api.libs.json.Reads._

  implicit val actorsUnknownJF: OFormat[Actors.Unknown.type] = Json.format[Actors.Unknown.type]

  implicit val actorsCollaboratorWrites: OWrites[Actors.AppCollaborator]  = Json.writes[Actors.AppCollaborator]
  implicit val actorsGatekeeperUserWrites: OWrites[Actors.GatekeeperUser] = Json.writes[Actors.GatekeeperUser]
  implicit val actorsScheduledJobWrites: OWrites[Actors.ScheduledJob]     = Json.writes[Actors.ScheduledJob]

  implicit val actorsCollaboratorReads: Reads[Actors.AppCollaborator]  =
    ((JsPath \ "id").read[String] or (JsPath \ "email").read[String]).map(s => Actors.AppCollaborator(LaxEmailAddress(s)))
  implicit val actorsGatekeeperUserReads: Reads[Actors.GatekeeperUser] = ((JsPath \ "id").read[String] or (JsPath \ "user").read[String]).map(Actors.GatekeeperUser(_))
  implicit val actorsScheduledJobReads: Reads[Actors.ScheduledJob]     = ((JsPath \ "id").read[String] or (JsPath \ "jobId").read[String]).map(Actors.ScheduledJob(_))

  implicit val formatNewStyleActor: OFormat[Actor] = Union.from[Actor]("actorType")
    .and[Actors.AppCollaborator](ActorType.COLLABORATOR.toString)
    .and[Actors.GatekeeperUser](ActorType.GATEKEEPER.toString)
    .and[Actors.ScheduledJob](ActorType.SCHEDULED_JOB.toString)
    .and[Actors.Unknown.type](ActorType.UNKNOWN.toString)
    .format
}
