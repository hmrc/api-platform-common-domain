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

import scala.util.Random

import play.api.libs.json._

import uk.gov.hmrc.apiplatform.modules.common.domain.models.LaxEmailAddress.StringSyntax
import uk.gov.hmrc.apiplatform.modules.common.utils.BaseJsonFormattersSpec

class LaxEmailAddressSpec extends BaseJsonFormattersSpec {

  val bobSmithEmailAddress = LaxEmailAddress("bob@smith.com")

  "LaxEmailAddress" when {
    "toString exposes the inner value and not the default case class toString" in {
      bobSmithEmailAddress.toString() shouldBe "bob@smith.com"
    }

    "creating a lax email address" should {
      "creating a lax email address forces to lower case" in {
        LaxEmailAddress("BOB@smith.com").text shouldBe "bob@smith.com"
      }
    }
    "comparing case insensitive" should {
      "match" in {
        LaxEmailAddress("BOB@smith.com") == bobSmithEmailAddress shouldBe true
      }
    }
    "format a lax email address" should {
      "produce json" in {
        Json.toJson(LaxEmailAddress("quark")) shouldBe JsString("quark")
      }

      "read json" in {
        Json.parse(""" "quark" """).as[LaxEmailAddress] shouldBe LaxEmailAddress("quark")
      }

      "read json normalises any `bad` data" in {
        Json.parse(""" "BOB@smith.com" """).as[LaxEmailAddress] shouldBe LaxEmailAddress("bob@smith.com")
      }
    }

    "orders correctly" in {
      val emails = List("aa@bob.com", "ab@bob.com", "ab@cob.com", "b@bob.com", "c@bob.com").map(LaxEmailAddress(_))

      Random.shuffle(emails).sorted shouldBe emails
    }

    "StringSyntax" should {
      "convert as string to a laxEmail Address" in {
        "Bob@smith.com".toLaxEmail shouldBe bobSmithEmailAddress
      }
    }
  }
}
