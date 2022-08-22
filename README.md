# Applied Functional Programming Workshop - Scala Edition

## SBT Commands

Enter SBT interactive shell

```sh
$ sbt
```

Common useful operations:

- `projects`: List all projects and mark the current one
- `project <NAME>`: Switch the current project
- `clean`: Deletes all generated files from compilation
- `compile`: Compiles the main sources (in src/main/scala) of the current project
- `console`: Open the Scala REPL with the current project loaded
- `test`: Compiles (main and test sources) and runs all tests
- `testOnly`: Compiles (main and test sources) and runs matching tests
- `reload`: Reloads the build definition

Run multiple commands:

```sh
>;clean;compile;test
```

Prepend `~` to any command to run it in watch mode. It can be used with one command:

```sh
>~test
```

or with many commands:

```sh
>~;clean;compile;test
```

## Documentation

### Scala 3

- [Scala Reference](https://docs.scala-lang.org/scala3/reference/index.html)
- [Scala API](https://www.scala-lang.org/api/3.1.3/)
- [Scala Online Compiler](https://scastie.scala-lang.org/)

## Test

- [MUnit](https://scalameta.org/munit/)

## Cats

- [Cats](https://typelevel.org/cats/)
- [Cats API](https://typelevel.org/cats/api/cats/index.html)
- [Cats Type Classes](https://github.com/tpolecat/cats-infographic)

## Cats Effect

- [Cats-Effect](https://typelevel.org/cats-effect/docs/getting-started)
- [Cats-Effect API](https://typelevel.org/cats-effect/api/3.x/cats/effect/index.html)
