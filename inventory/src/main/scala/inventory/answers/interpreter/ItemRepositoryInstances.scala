package inventory.answers.interpreter

import cats.implicits._
import cats.effect._
import cats.mtl._

import inventory.answers.Models._
import inventory.answers.ItemRepository
import inventory.answers.ItemRepository.ItemNotFoundException

trait ItemRepositoryInstances {

  case class ItemRepositoryState(items: Map[ItemId, Item])

  type Stateful[F[_]] = MonadState[F, ItemRepositoryState]

  object Stateful {
    def apply[F[_]](implicit S: Stateful[F]): Stateful[F] =
      S
    def get[F[_]](implicit S: Stateful[F]): F[ItemRepositoryState] =
      S.get
    def modify[F[_]](f: ItemRepositoryState => ItemRepositoryState)(implicit S: Stateful[F]): F[Unit] =
      S.modify(f)
  }

  import Stateful._

  implicit def itemRepository[F[_]: Sync: Stateful]: ItemRepository[F] = new ItemRepository[F] {

    private val S = Sync[F]
    import S._

    def load(id: ItemId): F[Item] =
      for {
        s     <- get
        itemO = s.items.get(id)
        item  <- itemO.fold(raiseError[Item](new ItemNotFoundException(id)))(pure)
      } yield item

    def save(item: Item): F[Item] =
      modify(s => s.copy(items = s.items + (item.id -> item))) *> pure(item)
  }

  object redis {
    import cats.mtl._

    case class Config(redisHost: String, redisPort: Int)

    type Configful[F[_]] = ApplicativeAsk[F, Config]

    object Configful {
      def apply[F[_]](implicit C: Configful[F]): Configful[F] = C
      def ask[F[_]](implicit C: Configful[F]): F[Config]      = C.ask
    }

    import Configful._

    implicit def redisItemRepository[F[_]: Sync: Configful]: ItemRepository[F] =
      new ItemRepository[F] {

        private val S = Sync[F]
        import S._

        import com.redis._
        import com.redis._
        import serialization._
        import Parse.Implicits.parseByteArray

        private lazy val client: F[RedisClient] = mkClient()

        def load(id: ItemId): F[Item] =
          client.flatMap { cli =>
            cli
              .get[Array[Byte]](formatId(id))
              .map(bs => Serializer.deserialize[Item](bs))
              .fold(raiseError[Item](new ItemNotFoundException(id)))(pure)
          }

        def save(item: Item): F[Item] =
          client.flatMap { cli =>
            val isOK = cli.set(formatId(item.id), Serializer.serialize(item))
            if (isOK) pure(item) else raiseError[Item](new Exception(s"Can't write to redis: $item"))
          }

        private def formatId(id: ItemId): String =
          id.toString.replace("-", "")

        private def mkClient(): F[RedisClient] =
          for {
            conf   <- ask
            client <- delay(new RedisClient(conf.redisHost, conf.redisPort))
          } yield client

        object Serializer {
          import java.io._

          def serialize[T <: Serializable](obj: T): Array[Byte] = {
            val byteOut = new ByteArrayOutputStream()
            val objOut  = new ObjectOutputStream(byteOut)
            objOut.writeObject(obj)
            objOut.close()
            byteOut.close()
            byteOut.toByteArray
          }

          def deserialize[T <: Serializable](bytes: Array[Byte]): T = {
            val byteIn = new ByteArrayInputStream(bytes)
            val objIn  = new ObjectInputStream(byteIn)
            val obj    = objIn.readObject().asInstanceOf[T]
            byteIn.close()
            objIn.close()
            obj
          }
        }
      }
  }

}

object itemrepository extends ItemRepositoryInstances
