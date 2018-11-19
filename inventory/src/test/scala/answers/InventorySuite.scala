package inventory.answers

import java.util.UUID
import minitest._

import cats.data._

import inventory.answers._
import inventory.answers.Models._
import inventory.answers.ItemRepository.ItemNotFoundException

trait InventorySuite extends SimpleTestSuite {

  case class TestState(
      generatedId: UUID,
      items: Map[ItemId, Item] = Map()
  )

  type Test[A]       = State[TestState, A]
  type TestResult[A] = EitherT[Test, Throwable, A]

  def runTestResult[A](tr: TestResult[A], init: TestState): Either[Throwable, TestState] = {
    val (ts, result) = tr.value.run(init).value
    result.map(_ => ts)
  }

  def assertRight(value: Either[Throwable, TestState])(assert: TestState => Unit) =
    value match {
      case Right(ts) => assert(ts)
      case Left(ex)  => fail("assertRight gets a Left: " + enter + ex.getMessage)
    }

  def assertLeft(value: Either[Throwable, TestState])(assert: Throwable => Unit) =
    value match {
      case Right(ts) => fail("assertLeft gets a Right: " + ts.toString)
      case Left(ve)  => assert(ve)
    }

  def fakeRandomId(): RandomId[TestResult] = new RandomId[TestResult] {
    def nextId(): TestResult[UUID] =
      EitherT.right(State.get.map(_.generatedId))
  }

  def fakeItemRepository(): ItemRepository[TestResult] = new ItemRepository[TestResult] {

    def load(id: ItemId): TestResult[Item] =
      EitherT.right(State.get.map(s => s.items(id)))

    def save(item: Item): TestResult[Item] =
      EitherT.right(
        State
          .modify[TestState](s => s.copy(items = s.items + (item.id -> item)))
          .flatMap(_ => State.pure(item))
      )
  }

  def fakeItemRepository_ItemNotFoundException(): ItemRepository[TestResult] = new ItemRepository[TestResult] {

    def load(id: ItemId): TestResult[Item] =
      EitherT.leftT(ItemNotFoundException(id))

    def save(item: Item): TestResult[Item] =
      EitherT.leftT(new Exception("Why you call the save function?"))
  }
}
