package marsroverkata.answers

import cats.effect._
import marsroverkata.answers.Version5._
import scala.Console._

class Version5Tests extends munit.FunSuite {

  test("go to opposite angle, system test") {
    val result = execute("RBBLBRF") {
      createApplication("planet.txt", "rover.txt")
    }

    assertEquals(result, s"$GREEN[OK] 4:3:E$RESET")
  }

  test("invalid planet data, system test") {
    val result = execute("RBBLBRF") {
      createApplication("invalid_planet.txt", "rover.txt")
    }

    assertEquals(result, s"$RED[ERROR] InvalidPlanet(ax4,InvalidSize)$RESET")
  }

  def execute[A](commands: String)(app: => IO[A]): String = {
    import java.io.{ByteArrayOutputStream, StringReader}

    val input = new StringReader(commands)
    val out   = new ByteArrayOutputStream
    Console.withIn(input) {
      Console.withOut(out) {
        app.unsafeRunSync()
      }
    }
    out.toString.replace("\r", "").split('\n').last
  }

}
