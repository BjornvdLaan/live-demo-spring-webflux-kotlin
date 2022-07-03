//package nl.bjornvanderlaan.livedemospringwebflux.router
//
//import nl.bjornvanderlaan.livedemospringwebflux.model.Cat
//import nl.bjornvanderlaan.livedemospringwebflux.model.CatDto
//import nl.bjornvanderlaan.livedemospringwebflux.model.toDto
//import nl.bjornvanderlaan.livedemospringwebflux.repository.CatRepository
//import org.junit.jupiter.api.Test
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.boot.test.context.SpringBootTest
//import org.springframework.boot.web.server.LocalServerPort
//import org.springframework.http.MediaType
//import org.springframework.test.annotation.DirtiesContext
//import org.springframework.test.web.reactive.server.WebTestClient
//import org.springframework.test.web.reactive.server.expectBody
//import org.springframework.test.web.reactive.server.expectBodyList
//
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
//class CatIntegrationTest(
//    @Autowired private val repository: CatRepository,
//    @LocalServerPort private val port: Int
//) {
//    val client = WebTestClient.bindToServer().baseUrl("http://localhost:$port").build()
//
//    private fun aCat(
//        id: Long? = null,
//        name: String = "Obi",
//        type: String = "Dutch Ringtail",
//        age: Int = 3
//    ) =
//        Cat(
//            id = id,
//            name = name,
//            type = type,
//            age = age
//        )
//
//    private fun anotherCat(
//        id: Long? = null,
//        name: String = "Wan",
//        type: String = "Japanese Bobtail",
//        age: Int = 5
//    ) =
//        aCat(
//            id = id,
//            name = name,
//            type = type,
//            age = age
//        )
//
//    @Test
//    fun `Retrieve all cats`() {
//        repository.save(aCat()).block()
//        repository.save(anotherCat()).block()
//
//        client
//            .get()
//            .uri("/api/cats")
//
//            .exchange()
//
//            .expectStatus().isOk
//            .expectBodyList<CatDto>().hasSize(2).contains(aCat().toDto(), anotherCat().toDto())
//    }
//
//    @Test
//    fun `Add a new cat`() {
//        client
//            .post()
//            .uri("/api/cats/")
//            .accept(MediaType.APPLICATION_JSON)
//            .contentType(MediaType.APPLICATION_JSON)
//            .bodyValue(aCat())
//
//            .exchange()
//
//            .expectStatus().isOk
//            .expectBody<CatDto>().isEqualTo(aCat().toDto())
//    }
//}
