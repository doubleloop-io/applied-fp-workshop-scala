package marsroverkata.answers

class Version3Tests extends munit.FunSuite {

  // Planet layout
  // +-----+-----+-----+-----+-----+
  // | 0,3 |     |     |     | 4,3 |
  // +-----+-----+-----+-----+-----+
  // |     |     |     |     |     |
  // +-----+-----+-----+-----+-----+
  // |     |     |     |     |     |
  // +-----+-----+-----+-----+-----+
  // | 0,0 |     |     |     | 4,0 |
  // +-----+-----+-----+-----+-----+

  import marsroverkata.answers.Version3._
  import marsroverkata.answers.Version3.Rotation._, Orientation._, Movement._, Command._, ExecutionError._

  test("go to opposite angle") {
    val rover = Rover(Position(0, 0), N)
    val planet = Planet(Size(5, 4), List(Obstacle(Position(2, 0)), Obstacle(Position(0, 3)), Obstacle(Position(3, 2))))
    val commands = List(Turn(OnLeft), Move(Forward), Turn(OnRight), Move(Backward))
    val result = executeAll(planet, rover, commands)
    assertEquals(result, Right(Rover(Position(4, 3), N)))
  }

  test("hit obstacle during commands execution") {
    val rover = Rover(Position(0, 0), N)
    val planet = Planet(Size(5, 4), List(Obstacle(Position(2, 0)), Obstacle(Position(0, 3)), Obstacle(Position(3, 2))))
    val commands = List(Turn(OnRight), Move(Forward), Move(Forward))
    val result = executeAll(planet, rover, commands)
    assertEquals(result, Left(HitObstacle(Rover(Position(1, 0), E))))
  }
}
