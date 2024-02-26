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

import scala.collection.immutable.ListMap

import play.api.libs.json._

import uk.gov.hmrc.apiplatform.modules.common.utils.BaseJsonFormattersSpec

sealed trait Mark
case object Fail extends Mark
case object Pass extends Mark

object Mark {

  implicit val markWrites: Writes[Mark] = Writes {
    case Fail => JsString("fail")
    case Pass => JsString("pass")
  }

  implicit val markReads: Reads[Mark] = Reads {
    case JsString("fail") => JsSuccess(Fail)
    case JsString("pass") => JsSuccess(Pass)
    case _                => JsError("Failed to parse Mark value")
  }
}

case class PossibleAnswer(value: String) extends AnyVal

object PossibleAnswer {
  implicit val keyReadsPossibleAnswer: KeyReads[PossibleAnswer]   = KeyReads(key => JsSuccess(PossibleAnswer(key)))
  implicit val keyWritesPossibleAnswer: KeyWrites[PossibleAnswer] = KeyWrites(_.value)
}

class MapJsonFormatterSpec extends BaseJsonFormattersSpec {
  import MapJsonFormatters._

  "MapJsonFormatters" should {

    val sample: ListMap[PossibleAnswer, Mark] = ListMap(PossibleAnswer("a") -> Pass, PossibleAnswer("b") -> Pass, PossibleAnswer("c") -> Fail)

    "writing json" in {
      println(Json.toJson[ListMap[PossibleAnswer, Mark]](sample))
      Json.toJson[ListMap[PossibleAnswer, Mark]](sample) shouldBe JsArray(Seq(
        JsObject(Seq(("a", JsString("pass")))),
        JsObject(Seq(("b", JsString("pass")))),
        JsObject(Seq(("c", JsString("fail"))))
      ))
    }

    "reading json" in {
      Json.parse(""" [ {"a": "pass"}, {"b": "pass"}, {"c": "fail"} ] """).as[ListMap[PossibleAnswer, Mark]] shouldBe sample
    }

    "fail to read non JSObject" in {
      intercept[Exception] {
        Json.parse(""" [ {"a": "pass"}, {"b": "pass"}, "bogus data" ] """).as[ListMap[PossibleAnswer, Mark]]
      }
    }
  }
}
