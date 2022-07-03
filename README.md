# Live demo - Spring Webflux and Kotlin

The branches in this repository for live codings and workshops.
Each branch represents a checkpoint in our effort to eradicate Reactor abstractions from our codebase using coroutines.

- 0 - [Start situation](https://github.com/BjornvdLaan/live-demo-spring-webflux-kotlin/tree/0-start)
  - We start with a simple Spring Webflux that uses Reactor abstractions (Mono, Flux) throughout the codebase.
- 1 - [Repositories are rewritten](https://github.com/BjornvdLaan/live-demo-spring-webflux-kotlin/tree/1-repository)
  - The repositories now implement CoroutineCrudRepository instead of ReactiveCrudRepository and Reactor abstractions are removed.
- 2 - [Services are rewritten](https://github.com/BjornvdLaan/live-demo-spring-webflux-kotlin/tree/2-service)
  - The services are rewritten to use the updated repositories and Reactor abstractions are also removed here.
- 3 - [Service test is rewritten](https://github.com/BjornvdLaan/live-demo-spring-webflux-kotlin/tree/3-service-test)
  - Because the services do not use Reactor anymore, we can get rid of the StepVerifier in our tests.
- 4 - [Controller is rewritten](https://github.com/BjornvdLaan/live-demo-spring-webflux-kotlin/tree/4-controller)
  - Finally, the controller is rewritten and does not need to use methods like switchIfEmpty.