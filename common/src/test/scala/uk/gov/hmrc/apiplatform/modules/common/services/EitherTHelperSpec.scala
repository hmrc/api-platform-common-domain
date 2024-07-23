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

package uk.gov.hmrc.apiplatform.modules.common.services

import java.util.concurrent.TimeUnit
import scala.concurrent.Future.successful
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

import cats.data.{EitherT, Validated}
import cats.implicits._

import uk.gov.hmrc.apiplatform.modules.common.utils.HmrcSpec

class EitherTHelperSpec extends HmrcSpec {
  import scala.concurrent.ExecutionContext.Implicits.global

  val atMost = Duration(1, TimeUnit.SECONDS)

  type ERR = String
  val ET    = EitherTHelper.make[ERR]
  val error = "Bang".asLeft[Int]

  def unwrap(et: EitherT[Future, ERR, Int]): Either[ERR, Int] = Await.result(et.value, atMost)

  "EitherTHelper" should {
    "wrap simple object" in {
      unwrap(ET.pure(1)) shouldBe 1.asRight[ERR]
    }

    "wrap left object" in {
      unwrap(ET.leftT("Bang")) shouldBe error
    }

    "wrap future of simple object" in {
      unwrap(ET.liftF(successful(1))) shouldBe 1.asRight[ERR]
    }

    "wrap some of simple object" in {
      unwrap(ET.fromOption(Some(1), "Bang")) shouldBe 1.asRight[ERR]
    }

    "wrap none of simple object" in {
      unwrap(ET.fromOption(None, "Bang")) shouldBe error
    }

    "wrap future some of simple object" in {
      unwrap(ET.fromOptionF(successful(Some(1)), "Bang")) shouldBe 1.asRight[ERR]
    }

    "wrap future none of simple object" in {
      unwrap(ET.fromOptionF(successful(None), "Bang")) shouldBe error
    }

    "wrap future some of simple object where error is also a future" in {
      unwrap(ET.fromOptionM(successful(Some(1)), successful("Bang"))) shouldBe 1.asRight[ERR]
    }

    "wrap future none of simple object where error is also a future" in {
      unwrap(ET.fromOptionM(successful(None), successful("Bang"))) shouldBe error
    }

    "wrap right of simple object" in {
      unwrap(ET.fromEither(Right(1))) shouldBe 1.asRight[ERR]
    }

    "wrap left of simple object" in {
      unwrap(ET.fromEither(Left("Bang"))) shouldBe error
    }

    "wrap future right of simple object" in {
      unwrap(ET.fromEitherF(successful(Right(1)))) shouldBe 1.asRight[ERR]
    }

    "wrap future left of simple object" in {
      unwrap(ET.fromEitherF(successful(Left("Bang")))) shouldBe error
    }

    "wrap validated valid of simple object" in {
      unwrap(ET.fromValidated(Validated.Valid(1))) shouldBe 1.asRight[ERR]
    }

    "wrap validated invalid of simple object" in {
      unwrap(ET.fromValidated(Validated.Invalid("Bang"))) shouldBe error
    }

    "wrap future validated valid of simple object" in {
      unwrap(ET.fromValidatedF(successful(Validated.Valid(1)))) shouldBe 1.asRight[ERR]
    }

    "wrap future validated invalid of simple object" in {
      unwrap(ET.fromValidatedF(successful(Validated.Invalid("Bang")))) shouldBe error
    }

    "cond true of simple object" in {
      unwrap(ET.cond(true, 1, "Bang")) shouldBe 1.asRight[ERR]
    }

    "cond false of simple object" in {
      unwrap(ET.cond(false, 1, "Bang")) shouldBe error
    }
  }
}
