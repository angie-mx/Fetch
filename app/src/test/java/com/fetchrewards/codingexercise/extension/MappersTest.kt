package com.fetchrewards.codingexercise.extension

import com.fetchrewards.codingexercise.data.Hiring
import com.fetchrewards.codingexercise.repository.remoteDataSource.ApiHiring
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test

class MappersTest {

    @Test
    fun `toHiring converts correctly to data object`() {
        val apiHiring = ApiHiring(510, 4, "Item 173")
        val expected = Hiring("4", "Item 173")

        apiHiring.toHiring() shouldBeEqualTo expected
    }

    @Test
    fun `toHiring replaces correctly null names to empty strings`() {
        val apiHiring = ApiHiring(510, 4, null)
        val expected = Hiring("4", "")

        apiHiring.toHiring() shouldBeEqualTo expected
    }

    @Test
    fun `formatResult should return the expected formatted map`() {
        rawApiResponse.formatResult().toString() shouldBeEqualTo formattedResponse().toString()
    }
}

private val rawApiResponse: List<ApiHiring> = listOf(
    ApiHiring(755, 3, ""),
    ApiHiring(203, 2, null),
    ApiHiring(684, 1, "Item 48"),
    ApiHiring(276, 2, "Item 32"),
    ApiHiring(736, 1, "Item 100"),
    ApiHiring(926, 3, "Item 135"),
    ApiHiring(808, 3, "Item 200"),
    ApiHiring(599, 2, "Item 7"),
    ApiHiring(424, 1, "Item 34"),
    ApiHiring(510, 3, "Item 12"),
    ApiHiring(680, 2, "Item 78"),
    ApiHiring(99, 2, "Invalid name"),
    ApiHiring(231, 1, null),
)

private fun formattedResponse(): Map<String, List<Hiring>> {
    val one = listOf(Hiring("1", "Item 34"), Hiring("1", "Item 48"), Hiring("1", "Item 100"))
    val two = listOf(Hiring("2", "Item 7"), Hiring("2", "Item 32"), Hiring("2", "Item 78"))
    val three = listOf(Hiring("3", "Item 12"), Hiring("3", "Item 135"), Hiring("3", "Item 200"))
    return mapOf("1" to one, "2" to two, "3" to three)
}
