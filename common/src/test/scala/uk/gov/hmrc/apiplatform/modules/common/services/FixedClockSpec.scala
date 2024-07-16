/*
 * Copyright 2024 HM Revenue & Customs
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

package uk.gov.hmrc.apiplatform.modules.common.services

import org.scalatest.prop.TableDrivenPropertyChecks
import uk.gov.hmrc.apiplatform.modules.common.utils.HmrcSpec
import uk.gov.hmrc.apiplatform.modules.common.utils.FixedClock
import java.time.Instant

class FixedClockSpec extends HmrcSpec with TableDrivenPropertyChecks with FixedClock {

  "FixedClock" should {
    val values =
      Table(
        ("Times", "Texts"),
        (Instants.fiveMinsAgo, Texts.fiveMinsAgo),
        (Instants.anHourAgo, Texts.anHourAgo),
        (Instants.aDayAgo, Texts.aDayAgo),
        (Instants.aWeekAgo, Texts.aWeekAgo),
        (Instants.aMonthAgo, Texts.aMonthAgo),
        (Instants.aYearAgo, Texts.aYearAgo)
      )

    "match when time is converted to json text" in {
      import play.api.libs.json._
      forAll(values) { (time, text) =>
        Json.toJson(time) shouldBe JsString(text)
      }
    }
    "match when time is converted from json text" in {
      import play.api.libs.json._
      forAll(values) { (time, text) =>
        Json.fromJson[Instant](JsString(text)) shouldBe JsSuccess(time)
      }
    }
  }
}
