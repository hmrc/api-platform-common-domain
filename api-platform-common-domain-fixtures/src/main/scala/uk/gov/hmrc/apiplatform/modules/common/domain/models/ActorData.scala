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

object ActorData extends LaxEmailAddressFixtures {

  object Collaborators {
    val one   = Actors.AppCollaborator(emailOne)
    val two   = Actors.AppCollaborator(emailTwo)
    val three = Actors.AppCollaborator(emailThree)
  }

  object Gatekeepers {
    val one   = Actors.GatekeeperUser("A surname")
    val two   = Actors.GatekeeperUser("B surname")
    val three = Actors.GatekeeperUser("C surname")
  }
}

trait ActorFixtures {
  val collaboratorActorOne   = ActorData.Collaborators.one
  val collaboratorActorTwo   = ActorData.Collaborators.two
  val collaboratorActorThree = ActorData.Collaborators.three

  val gatekeeperActorOne   = ActorData.Gatekeepers.one
  val gatekeeperActorTwo   = ActorData.Gatekeepers.two
  val gatekeeperActorThree = ActorData.Gatekeepers.three
}
