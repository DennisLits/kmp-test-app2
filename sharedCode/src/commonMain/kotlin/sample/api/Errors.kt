package sample.api


sealed class Error : Throwable() {
    class Generic : Error()
    class UpdateProblem : Error()
    class Unauthorized : Error()

    class Specific() : Error()

}