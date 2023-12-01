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

class ApiVersionNbrSpec extends BaseJsonFormattersSpec {

  "ApiVersionNbr" should {
    val example = ApiVersionNbr("1.5")

    "sort accordingly" in {
      val sorted = List("1.0", "1.1", "2.0", "2.1", "3", "10", "10.1")

      val unsorted = Random.shuffle(sorted)

      unsorted.map(ApiVersionNbr(_)).sorted shouldBe sorted.map(ApiVersionNbr(_))
    }

    "convert to string" in {
      example.toString() shouldBe "1.5"
    }

    "cover random call" in {
      val rand = ApiVersionNbr.random

      rand.toString() shouldBe rand.value
    }

    "read from Json" in {
      testFromJson[ApiVersionNbr](s""""1.5"""")(example)
    }

    "write to Json" in {
      Json.toJson[ApiVersionNbr](example) shouldBe JsString("1.5")
    }

    val rawText                         = """{"version1":"text1","version2":"text2"}"""
    val map: Map[ApiVersionNbr, String] = Map((ApiVersionNbr("version1") -> "text1"), (ApiVersionNbr("version2") -> "text2"))

    "key read from Json" in {
      testFromJson[Map[ApiVersionNbr, String]](rawText)(map)
    }

    "key write to Json" in {
      Json.toJson[Map[ApiVersionNbr, String]](map).toString() shouldBe rawText
    }
  }
}
