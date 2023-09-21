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

class ApiContextSpec extends BaseJsonFormattersSpec {

  "ApiContext" should {
    val example = ApiContext("misc/blah")

    "provide the top level context" in {
      example.topLevelContext() shouldBe ApiContext("misc")
    }

    "convert toString" in {
      example.toString() shouldBe "misc/blah"
    }

    "cover random call" in {
      val rand = ApiContext.random

      rand.toString() shouldBe rand.value
    }

    "provide segment count" in {
      example.segments() shouldBe Array("misc", "blah")
    }

    "read from Json" in {
      testFromJson[ApiContext](s""""misc/blah"""")(example)
    }

    "write to Json" in {
      Json.toJson[ApiContext](example) shouldBe JsString("misc/blah")
    }

    "order correctly" in {
      val contexts = List("a", "b", "c").map(ApiContext(_))

      Random.shuffle(contexts).sorted shouldBe contexts
    }
  }
}
