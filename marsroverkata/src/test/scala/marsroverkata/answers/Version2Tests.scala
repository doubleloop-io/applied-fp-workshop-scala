package marsroverkata.answers

import scala.util._
import marsroverkata.answers.Version2._

class Version2Tests extends munit.FunSuite {

  test("go to opposite angle") {
    val planet   = Planet(Size(5, 4), List(Obstacle(Position(2, 0)), Obstacle(Position(0, 3)), Obstacle(Position(3, 2))))
    val mission  = Mission(planet, Rover(Position(0, 0), N))
    val commands = List(Turn(OnLeft), Move(Forward), Turn(OnRight), Move(Backward))
    val result   = execute(mission, commands)
    assertEquals(result, Right(Mission(planet, Rover(Position(4, 3), N))))
  }

  test("hit obstacle during commands execution") {
    val planet   = Planet(Size(5, 4), List(Obstacle(Position(2, 0)), Obstacle(Position(0, 3)), Obstacle(Position(3, 2))))
    val mission  = Mission(planet, Rover(Position(0, 0), N))
    val commands = List(Turn(OnRight), Move(Forward), Move(Forward))
    val result   = execute(mission, commands)
    assertEquals(result, Left(Mission(planet, Rover(Position(1, 0), E))))
  }
}
