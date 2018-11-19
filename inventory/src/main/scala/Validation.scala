package inventory

import cats._
import cats.data._
import cats.implicits._

object Validation {

  lazy val enter = System.getProperty("line.separator")

  type ValidationResult[A] = ValidatedNel[ValidationError, A]

  implicit class ValidatedOps[A](actual: Validated[Throwable, A]) {

    def toThrowing[F[_]: Throwing]: F[A] =
      actual.fold(Throwing[F].raiseError, Throwing[F].pure)
  }

  implicit val validationErrorShow = new Show[ValidationError] {
    def show(value: ValidationError): String = value match {
      case EmptyString(fieldName)               => s"The $fieldName cannot be empty."
      case InvalidCharsString(fieldName, value) => s"The $fieldName cannot contain special characters: $value."
      case NegativeNumber(fieldName, value)     => s"The $fieldName cannot be negative: $value."
    }
  }

  final case class ValidationErrorException(errors: ValidationError*)
      extends Exception("Error list:" + enter + errors.map("- " + _.show).mkString(enter))

  sealed trait ValidationError
  case class EmptyString(fieldName: String)                       extends ValidationError
  case class InvalidCharsString(fieldName: String, value: String) extends ValidationError
  case class NegativeNumber(fieldName: String, value: Int)        extends ValidationError

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
}
