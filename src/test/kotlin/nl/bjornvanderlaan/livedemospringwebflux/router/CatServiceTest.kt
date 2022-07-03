package nl.bjornvanderlaan.livedemospringwebflux.router

import com.ninjasquad.springmockk.MockkBean
import io.kotest.matchers.equality.shouldBeEqualToComparingFields
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.every
import io.mockk.slot
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import nl.bjornvanderlaan.livedemospringwebflux.model.*
import nl.bjornvanderlaan.livedemospringwebflux.repository.CatRepository
import nl.bjornvanderlaan.livedemospringwebflux.repository.PersonRepository
import nl.bjornvanderlaan.livedemospringwebflux.service.CatService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class CatServiceTest {

    @MockkBean
    private lateinit var catRepository: CatRepository

    @MockkBean
    private lateinit var personRepository: PersonRepository

    @Autowired
    private lateinit var catService: CatService

    @Test
    fun `should retrieve all cats`() = runBlocking {
        val firstCat = CatDto(
            name = "Obi",
            type = "Dutch Ringtail",
            age = 3
        )
        val secondCat = CatDto(
            name = "Wan",
            type = "Japanese Bobtail",
            age = 5
        )

        every {
            catRepository.findAll()
        } returns flow {
            emit(firstCat.toEntity())
            emit(secondCat.toEntity())
        }

        val retrievedCats = catService.getAllCats().toList()

        retrievedCats.count() shouldBe 2
        retrievedCats[0] shouldBeEqualToComparingFields firstCat
    }

    @Test
    fun `retrieve Cat with Owner`() = runBlocking {
        val inputCat = Cat(id = 1, name = "Obi", type = "Dutch Ringtail", age = 3, ownerId = 1)
        val inputPerson = Person(id = 1, name = "Peter Katlover", age = 29)

        coEvery {
            catRepository.findById(1)
        } returns inputCat
        coEvery {
            personRepository.findById(1)
        } returns inputPerson

        val outputCat = catService.getCatById(1)

        outputCat.shouldNotBeNull()
        outputCat shouldBeEqualToComparingFields inputCat.toDto().copy(owner = inputPerson)
    }

    @Test
    fun `should add a new cat`() = runBlocking {
        val catSlot = slot<Cat>()
        coEvery {
            catRepository.save(capture(catSlot))
        } coAnswers {
            catSlot.captured
        }

        val inputCat = CatDto(
            name = "Obi",
            type = "Dutch Ringtail",
            age = 3
        )
        val outputCat = catService.addNewCat(inputCat)

        outputCat shouldBeEqualToComparingFields inputCat
    }
}