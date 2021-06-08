package marsroverkata

// TODO: uncomment import
// import marsroverkata.Version1._

class Version1Tests extends munit.FunSuite {

// +-----+-----+-----+-----+-----+
// | 0,3 |     |     |     | 4,3 |
// +-----+-----+-----+-----+-----+
// |     |     |     |     |     |
// +-----+-----+-----+-----+-----+
// |     |     |     |     |     |
// +-----+-----+-----+-----+-----+
// | 0,0 |     |     |     | 4,0 |
// +-----+-----+-----+-----+-----+

  test("three times right".ignore) {
    /*
        Planet: 5 4
        Rover: 0 0 N
        Commands: RRR
        --
        Rover: 0 0 W
     */
  }

  test("two times left".ignore) {
    /*
        Planet: 5 4
        Rover: 0 0 N
        Commands: LL
        --
        Rover: 0 0 S
     */
  }

  test("wrap on North".ignore) {
    /*
        Planet: 5 4
        Rover: 0 0 N
        Commands: FFFFFF
        --
        Rover: 0 2 N
     */
  }

  test("wrap on South".ignore) {
    /*
        Planet: 5 4
        Rover: 0 0 S
        Commands: F
        --
        Rover: 0 3 S
     */
  }

  test("wrap on Est".ignore) {
    /*
        Planet: 5 4
        Rover: 0 0 E
        Commands: FFFFF
        --
        Rover: 0 0 E
     */
  }

  test("wrap on West".ignore) {
    /*
        Planet: 5 4
        Rover: 0 0 W
        Commands: FF
        --
        Rover: 3 0 W
     */
  }

  test("opposite angle".ignore) {
    /*
        Planet: 5 4
        Rover: 0 0 N
        Commands: RBBLBRF
        --
        Rover: 4 3 E
     */
  }
}
