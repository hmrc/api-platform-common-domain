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

import org.scalatest.OptionValues

import play.api.libs.json._

import uk.gov.hmrc.apiplatform.modules.common.utils.BaseJsonFormattersSpec

class ActorSpec extends BaseJsonFormattersSpec with OptionValues {

  val bobSmithEmailAddress = LaxEmailAddress("bob@smith.com")
  val bobSmithUserName     = "bob smith"

  "Actor JsonFormatters" when {

    "given a gatekeeper user" should {
      "produce json" in {
        testToJson[Actor](Actors.GatekeeperUser(bobSmithUserName))(
          ("actorType" -> "GATEKEEPER"),
          ("user"      -> bobSmithUserName)
        )
      }

      "produce type only json" in {
        testToJson[Actors.GatekeeperUser](Actors.GatekeeperUser(bobSmithUserName))(
          ("user" -> bobSmithUserName)
        )
      }

      "read json" in {
        testFromJson[Actor]("""{"actorType":"GATEKEEPER","user":"bob smith"}""")(Actors.GatekeeperUser(bobSmithUserName))
      }

      "read old style json" in {
        testFromJson[Actor]("""{"actorType":"GATEKEEPER","id":"bob smith"}""")(Actors.GatekeeperUser(bobSmithUserName))
      }

      "read as just a gatekeeper user" in {
        testFromJson[Actors.GatekeeperUser]("""{"id":"bob smith"}""")(Actors.GatekeeperUser(bobSmithUserName))
      }

      "return correct actor type" in {
        ActorType.actorType(Actors.GatekeeperUser(bobSmithUserName)) shouldBe ActorType.GATEKEEPER
      }
    }

    "given a collaborator actor" should {
      "produce json" in {
        testToJson[Actor](Actors.AppCollaborator(bobSmithEmailAddress))(
          ("actorType" -> "COLLABORATOR"),
          ("email"     -> "bob@smith.com")
        )
      }

      "produce type only json" in {
        testToJson[Actors.AppCollaborator](Actors.AppCollaborator(bobSmithEmailAddress))(
          ("email" -> "bob@smith.com")
        )
      }

      "read json" in {
        testFromJson[Actor]("""{"actorType":"COLLABORATOR","email":"bob@smith.com"}""")(Actors.AppCollaborator(bobSmithEmailAddress))
      }

      "read old style json" in {
        testFromJson[Actor]("""{"actorType":"COLLABORATOR","id":"bob@smith.com"}""")(Actors.AppCollaborator(bobSmithEmailAddress))
      }

      "read as just an app collaborator" in {
        testFromJson[Actors.AppCollaborator]("""{"id":"bob@smith.com"}""")(Actors.AppCollaborator(bobSmithEmailAddress))
      }

      "return correct actor type" in {
        ActorType.actorType(Actors.AppCollaborator(bobSmithEmailAddress)) shouldBe ActorType.COLLABORATOR
      }
    }

    "given a scheduled job actor" should {
      "produce json" in {
        testToJson[Actor](Actors.ScheduledJob("DeleteAllAppsBwaHaHa"))(
          ("actorType" -> "SCHEDULED_JOB"),
          ("jobId"     -> "DeleteAllAppsBwaHaHa")
        )
      }

      "read json" in {
        testFromJson[Actor]("""{"actorType":"SCHEDULED_JOB","jobId":"DeleteAllAppsBwaHaHa"}""")(Actors.ScheduledJob("DeleteAllAppsBwaHaHa"))
      }

      "read old style json" in {
        testFromJson[Actor]("""{"actorType":"SCHEDULED_JOB","id":"DeleteAllAppsBwaHaHa"}""")(Actors.ScheduledJob("DeleteAllAppsBwaHaHa"))
      }

      "return correct actor type" in {
        ActorType.actorType(Actors.ScheduledJob("DeleteAllAppsBwaHaHa")) shouldBe ActorType.SCHEDULED_JOB
      }
    }

    "given a process actor" should {
      "produce json" in {
        testToJson[Actor](Actors.Process("Publisher"))(
          ("actorType" -> "PROCESS"),
          ("name"      -> "Publisher")
        )
      }

      "read json" in {
        testFromJson[Actor]("""{"actorType":"PROCESS","name":"Publisher"}""")(Actors.Process("Publisher"))
      }

      "return correct actor type" in {
        ActorType.actorType(Actors.Process("Publisher")) shouldBe ActorType.PROCESS
      }
    }

    "given an unknown actor" should {
      "produce json" in {
        testToJson[Actor](Actors.Unknown)(
          ("actorType" -> "UNKNOWN")
        )
      }

      "read json" in {
        testFromJson[Actor]("""{"actorType":"UNKNOWN"}""")(Actors.Unknown)
      }

      "return correct actor type" in {
        ActorType.actorType(Actors.Unknown) shouldBe ActorType.UNKNOWN
      }
    }

    "given bad json" should {
      "fail accordingly" in {
        val text = """{"actorType":"NOT_VALID","jobId":"DeleteAllAppsBwaHaHa"}"""
        Json.parse(text).validate[Actor] match {
          case JsError(errs) =>
            errs.size shouldBe 1
            errs.map(_._1).headOption.value.toString shouldBe "/actorType"
            errs.map(_._2).headOption.value.toString should include("NOT_VALID is not a recognised actorType")
          case _             => fail()
        }
      }
    }
  }
}
