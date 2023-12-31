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

package uk.gov.hmrc.apiplatform.modules.common.domain.services

import cats.data.{NonEmptySet => NES}

import play.api.libs.json._

import uk.gov.hmrc.apiplatform.modules.common.utils.BaseJsonFormattersSpec

class NonEmptySetFormatterSpec extends BaseJsonFormattersSpec {
  import NonEmptySetFormatters._

  "NonEmptySetFormatter" should {

    val sample = NES.of("a", "b", "c")

    "writing json" in {
      Json.toJson(sample) shouldBe JsArray(Seq(JsString("a"), JsString("b"), JsString("c")))
    }

    "reading json a good Set" in {
      Json.parse(""" [ "a", "b", "c" ] """).as[NES[String]] shouldBe sample
    }

    "fail to read an empty Set" in {
      intercept[Exception] {
        Json.parse(""" [] """).as[NES[String]]
      }
    }
  }
}
