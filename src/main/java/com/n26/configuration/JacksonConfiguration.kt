package com.n26.configuration

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.core.Version
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.module.SimpleModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.io.IOException
import java.math.BigDecimal


@Configuration
open class JacksonConfiguration {
    @Bean
    open fun objectMapper(): ObjectMapper {

        val bigDecimalSerializerModule = SimpleModule("BigDecimalSerializer",
                Version(1, 1, 1, "RC", "com.n26", "big-decimal-serializer"))
                .addSerializer(BigDecimal::class.java, BigDecimalSerializer())

        return ObjectMapper().registerModule(bigDecimalSerializerModule).findAndRegisterModules()
    }
}

class BigDecimalSerializer : JsonSerializer<BigDecimal>() {
    @Throws(IOException::class, JsonProcessingException::class)
    override fun serialize(value: BigDecimal, jgen: JsonGenerator, provider: SerializerProvider) {
        jgen.writeString(value.setScale(2, BigDecimal.ROUND_HALF_UP).toString())
    }
}


