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

import uk.gov.hmrc.apiplatform.modules.common.utils.BaseJsonFormattersSpec

class ApiIdentifierSpec extends BaseJsonFormattersSpec {

  "ApiIdentifier" should {
    val exampleVersionNbr = ApiVersionNbr("1.5")
    val exampleContext    = ApiContext("misc/blah")
    val example           = ApiIdentifier(exampleContext, exampleVersionNbr)

    "convert toString" in {
      example.toString() shouldBe "ApiIdentifier(misc/blah,1.5)"
    }

    "read from Json with version" in {
      testFromJson[ApiIdentifier]("""{"context":"misc/blah","version":"1.5"}""")(example)
    }

    "read from Json with version number" in {
      testFromJson[ApiIdentifier]("""{"context":"misc/blah","versionNbr":"1.5"}""")(example)
    }

    "write to Json" in {
      Json.toJson[ApiIdentifier](example) shouldBe Json.obj(
        "context" -> "misc/blah",
        "version" -> "1.5"
      )
    }

    "sort accordingly" in {
      val sorted = List(example.copy(context = ApiContext("angel")), example, example.copy(versionNbr = ApiVersionNbr("2.0")), example.copy(context = ApiContext("zoom")))

      val unsorted = Random.shuffle(sorted)

      unsorted.sorted shouldBe sorted
    }
  }
}
