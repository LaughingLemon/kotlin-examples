package org.lemon.location.service.config

import com.fasterxml.jackson.databind.MappingIterator
import com.fasterxml.jackson.dataformat.csv.CsvMapper
import com.fasterxml.jackson.dataformat.csv.CsvSchema
import org.lemon.location.service.model.City
import org.lemon.location.service.model.CityRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class DataSetup {

    @Autowired
    private lateinit var repo: CityRepo

    @PostConstruct
    fun setupData() {
        importCities()
    }

    private fun <T> loadObjectList(type: Class<T>, fileName: String): List<T> {
        val boot = CsvSchema.emptySchema().withHeader()
        val mapper = CsvMapper()
        val readValues: MappingIterator<T> = mapper.readerFor(type).with(boot).readValues(ClassPathResource(fileName).file)
        return readValues.readAll()
    }

    private fun importCities() {
        repo.saveAll(loadObjectList(City::class.java, "cities.csv"))
    }
}