package nl.bjornvanderlaan.livedemospringwebflux.configuration

import io.r2dbc.spi.ConnectionFactory
import kotlinx.coroutines.runBlocking
import nl.bjornvanderlaan.livedemospringwebflux.model.Person
import nl.bjornvanderlaan.livedemospringwebflux.repository.PersonRepository
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator

@Configuration
class H2DatabaseConfiguration {
    @Bean
    fun initializeTable(@Qualifier("connectionFactory") connectionFactory: ConnectionFactory): ConnectionFactoryInitializer {
        val initializer = ConnectionFactoryInitializer()
        initializer.setConnectionFactory(connectionFactory)
        initializer.setDatabasePopulator(ResourceDatabasePopulator(ClassPathResource("migrations/V1__init.sql")))
        return initializer
    }

    @Bean
    fun insertData(personRepository: PersonRepository): ApplicationRunner =
        ApplicationRunner {}
}