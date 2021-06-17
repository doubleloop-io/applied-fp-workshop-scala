package marsroverkata

import scala.util._
import marsroverkata.Version2._

class Version2Tests extends munit.FunSuite {

// +-----+-----+-----+-----+-----+
// | 0,3 |     |     |     | 4,3 |
// +-----+-----+-----+-----+-----+
// |     |     |     |     |     |
// +-----+-----+-----+-----+-----+
// |     |     |     |     |     |
// +-----+-----+-----+-----+-----+
// | 0,0 |     |     |     | 4,0 |
// +-----+-----+-----+-----+-----+

  test("go to opposite angle") {
    /*
        Plant: 5 4
        Rover: 0 0 N
        Commands: LFRB
        --
        Rover: 4 3 N
   */
  }

  test("hit obstacle during commands execution") {
    /*
        Plant: 5 4
        Obstacles: 2 0; 0 3; 3 3
        Rover: 0 0 N
        Commands: RFF
        --
        Rover: STOP 1 0 E
   */
  }
}
