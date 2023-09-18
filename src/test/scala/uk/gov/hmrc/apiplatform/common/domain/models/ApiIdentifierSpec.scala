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

package uk.gov.hmrc.apiplatform.common.domain.models

import play.api.libs.json._

import uk.gov.hmrc.apiplatform.common.utils.BaseJsonFormattersSpec

class ApiIdentifierSpec extends BaseJsonFormattersSpec {

  "ApiIdentfier" should {
    val exampleVersionNbr = ApiVersionNbr("1.5")
    val exampleContext = ApiContext("misc/blah")
    val example = ApiIdentifier(exampleContext, exampleVersionNbr)


    "convert toString" in {
      example.toString() shouldBe "ApiIdentifier(misc/blah,1.5)"
    }

    "read from Json" in {
      testFromJson[ApiIdentifier]("""{"context":"misc/blah","version":"1.5"}""")(example)
    }

    "write to Json" in {
      Json.toJson[ApiIdentifier](example) shouldBe Json.obj(
        "context" -> "misc/blah",
        "version" -> "1.5"
      )
    }


  }
}