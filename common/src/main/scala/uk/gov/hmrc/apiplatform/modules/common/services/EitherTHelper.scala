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

import scala.concurrent.{ExecutionContext, Future}

import cats.data.{EitherT, Validated}
import cats.instances.future.catsStdInstancesForFuture

trait EitherTHelper[E] {
  outer =>

  implicit val ec: ExecutionContext

  def pure[A](in: A)   = EitherT.pure[Future, E](in)
  def leftT[R](err: E) = EitherT.leftT[Future, R](err)

  def liftF[A](in: Future[A]): EitherT[Future, E, A] = EitherT.liftF[Future, E, A](in)

  def fromOption[A](in: Option[A], error: => E): EitherT[Future, E, A]                  = EitherT.fromOption(in, error)
  def fromOptionF[A](in: Future[Option[A]], error: => E): EitherT[Future, E, A]         = EitherT.fromOptionF(in, error)
  def fromOptionM[A](in: Future[Option[A]], error: => Future[E]): EitherT[Future, E, A] = EitherT.fromOptionM(in, error)

  def fromEither[A](in: Either[E, A]): EitherT[Future, E, A]          = EitherT.fromEither(in)
  def fromEitherF[A](in: Future[Either[E, A]]): EitherT[Future, E, A] = EitherT.apply(in)

  def cond[A](in: => Boolean, right: => A, left: => E) = EitherT.cond[Future](in, right, left)

  def fromValidatedF[A](in: Future[Validated[E, A]]): EitherT[Future, E, A] = EitherT(in.map(_.toEither))
  def fromValidated[A](in: Validated[E, A]): EitherT[Future, E, A]          = EitherT.fromEither(in.toEither)
}

object EitherTHelper {

  def make[E](implicit outerEC: ExecutionContext) = new EitherTHelper[E] {
    implicit val ec: ExecutionContext = outerEC
  }
}
