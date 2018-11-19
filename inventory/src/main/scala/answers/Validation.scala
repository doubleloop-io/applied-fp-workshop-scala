package inventory.answers

import cats._
import cats.data._
import cats.implicits._

object Validation {

  type ValidationResult[A] = ValidatedNel[ValidationError, A]

  def liftF[F[_]: Throwing, A](vr: ValidationResult[A]): F[A] =
    vr.leftMap(ValidationErrorException.apply)
      .fold(Throwing[F].raiseError, Throwing[F].pure)

  def liftF[F[_]: Throwing, A](f: A => ValidationResult[A]): A => F[A] =
    a => liftF[F, A](f(a))

  implicit val validationErrorShow = new Show[ValidationError] {
    def show(value: ValidationError): String = value match {
      case EmptyString(fieldName)               => s"The $fieldName cannot be empty."
      case InvalidCharsString(fieldName, value) => s"The $fieldName cannot contain special characters: $value."
      case NegativeNumber(fieldName, value)     => s"The $fieldName cannot be negative: $value."
      case NotTrue(fieldName)                   => s"The $fieldName cannot be false."
    }
  }

  final case class ValidationErrorException(errors: NonEmptyList[ValidationError])
      extends Exception("Error list:" + enter + errors.map("- " + _.show).toList.mkString(enter))

  sealed trait ValidationError
  case class EmptyString(fieldName: String)                       extends ValidationError
  case class InvalidCharsString(fieldName: String, value: String) extends ValidationError
  case class NegativeNumber(fieldName: String, value: Int)        extends ValidationError
  case class NotTrue(fieldName: String)                           extends ValidationError

  def valid[A](value: A): ValidationResult[A] =
    value.validNel

  def checkNotEmpty(value: String, fieldName: String): ValidationResult[String] =
    Either
      .cond(value.trim.nonEmpty, value, EmptyString(fieldName))
      .toValidatedNel

  def checkAlphanumeric(value: String, fieldName: String): ValidationResult[String] =
    Either
      .cond(value.matches("^[a-zA-Z0-9\\s]+$"), value, InvalidCharsString(fieldName, value))
      .toValidatedNel

  def checkPositive(value: Int, fieldName: String): ValidationResult[Int] =
    Either
      .cond(value >= 0, value, NegativeNumber(fieldName, value))
      .toValidatedNel

  def checkTrue(value: Boolean, fieldName: String): ValidationResult[Boolean] =
    Either
      .cond(value, value, NotTrue(fieldName))
      .toValidatedNel
}
