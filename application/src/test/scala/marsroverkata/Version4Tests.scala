package application

import munit.CatsEffectSuite

// TODO: Remove IgnoreSuite annotation

@munit.IgnoreSuite
class Version4Tests extends CatsEffectSuite {

  import application.Version4._
  import application.Version4.Rotation._, Orientation._, Movement._, Command._, ParseError._
  import cats.effect.IO
  import scala.Console.{ GREEN, RED, RESET }

  test("load planet (integration test)") {
    val result = loadPlanet("planet.txt")
    val expected = Planet(Size(5, 4), List(Obstacle(Position(2, 0)), Obstacle(Position(0, 3))))
    assertIO(result, expected)
  }

  test("load rover (integration test)") {
    val result = loadRover("rover.txt")
    val expected = Rover(Position(0, 0), N)
    assertIO(result, expected)
  }

  test("load commands (integration test)") {
    val result = runConsole("RRF", loadCommands())
    assertIO(result, List(Turn(OnRight), Turn(OnRight), Move(Forward)))
  }

  test("go to opposite angle (integration test)") {
    val app = createApplication("planet.txt", "rover.txt")
    val result = runCaptureOutput("RBBLBRF", app)
    assertIO(result, s"$GREEN[OK] 4:3:E$RESET")
  }

  test("hit obstacle during commands execution (integration test)") {
    val app = createApplication("planet.txt", "rover.txt")
    val result = runCaptureOutput("RFF", app)
    assertIO(result, s"$GREEN[OK] O:1:0:E$RESET")
  }

  test("invalid planet data") {
    val app = createApplication("planet_invalid_data.txt", "rover.txt")
    val result = runCaptureOutput("RBBLBRF", app)
    assertIO(result, s"$RED[ERROR] Planet parsing: invalid size: ax4$RESET")
  }

  test("invalid planet file") {
    val app = createApplication("planet_invalid_content.txt", "rover.txt")
    val result = runCaptureOutput("RBBLBRF", app)
    assertIO(result, s"$RED[ERROR] Invalid file content: planet_invalid_content.txt$RESET")
  }

  def runConsole[A](commands: String, program: IO[A]): IO[A] = IO {
    import java.io.{ ByteArrayInputStream, ByteArrayOutputStream, PrintStream }

    val originalIn = System.in
    val originalOut = System.out
    try {
      val in = new ByteArrayInputStream(s"$commands\n".getBytes())
      val out = new ByteArrayOutputStream

      System.setIn(in)
      System.setOut(new PrintStream(out))
      program.unsafeRunSync()
    } finally {
      System.setIn(originalIn)
      System.setOut(originalOut)
    }
  }

  def runCaptureOutput[A](commands: String, program: IO[Unit]): IO[String] = IO {
    import java.io.{ ByteArrayInputStream, ByteArrayOutputStream, PrintStream }

    val originalIn = System.in
    val originalOut = System.out
    try {
      val in = new ByteArrayInputStream(s"$commands\n".getBytes())
      val out = new ByteArrayOutputStream

      System.setIn(in)
      System.setOut(new PrintStream(out))
      program.unsafeRunSync()
      out.toString.replace("\r", "").split('\n').last
    } finally {
      System.setIn(originalIn)
      System.setOut(originalOut)
    }
  }

}
