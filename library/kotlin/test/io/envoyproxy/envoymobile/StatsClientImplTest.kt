package io.envoyproxy.envoymobile

import io.envoyproxy.envoymobile.engine.EnvoyEngine
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class StatsClientImplTest {
  private var envoyEngine: EnvoyEngine = mock(EnvoyEngine::class.java)

  @Test
  fun `counter delegates to engine`() {
    val statsClient = StatsClientImpl(envoyEngine)
    val counter = statsClient.counter(Element("test"), Element("stat"))
    counter.increment()
    val elementsCaptor = ArgumentCaptor.forClass(String::class.java)
    val countCaptor = ArgumentCaptor.forClass(Int::class.java)
    verify(envoyEngine).recordCounterInc(elementsCaptor.capture(), countCaptor.capture())
    assertThat(elementsCaptor.getValue()).isEqualTo("test.stat")
    assertThat(countCaptor.getValue()).isEqualTo(1)
  }

  @Test
  fun `counter delegates to engine with count`() {
    val statsClient = StatsClientImpl(envoyEngine)
    val counter = statsClient.counter(Element("test"), Element("stat"))
    counter.increment(5)
    val elementsCaptor = ArgumentCaptor.forClass(String::class.java)
    val countCaptor = ArgumentCaptor.forClass(Int::class.java)
    verify(envoyEngine).recordCounterInc(elementsCaptor.capture(), countCaptor.capture())
    assertThat(elementsCaptor.getValue()).isEqualTo("test.stat")
    assertThat(countCaptor.getValue()).isEqualTo(5)
  }

  @Test
  fun `gauge delegates to engine with value for set`() {
    val statsClient = StatsClientImpl(envoyEngine)
    val gauge = statsClient.gauge(Element("test"), Element("stat"))
    gauge.set(5)
    val elementsCaptor = ArgumentCaptor.forClass(String::class.java)
    val valueCaptor = ArgumentCaptor.forClass(Int::class.java)
    verify(envoyEngine).recordGaugeSet(elementsCaptor.capture(), valueCaptor.capture())
    assertThat(elementsCaptor.getValue()).isEqualTo("test.stat")
    assertThat(valueCaptor.getValue()).isEqualTo(5)
  }

  @Test
  fun `gauge delegates to engine with amount for add`() {
    val statsClient = StatsClientImpl(envoyEngine)
    val gauge = statsClient.gauge(Element("test"), Element("stat"))
    gauge.add(5)
    val elementsCaptor = ArgumentCaptor.forClass(String::class.java)
    val amountCaptor = ArgumentCaptor.forClass(Int::class.java)
    verify(envoyEngine).recordGaugeAdd(elementsCaptor.capture(), amountCaptor.capture())
    assertThat(elementsCaptor.getValue()).isEqualTo("test.stat")
    assertThat(amountCaptor.getValue()).isEqualTo(5)
  }

  @Test
  fun `gauge delegates to engine with amount for sub`() {
    val statsClient = StatsClientImpl(envoyEngine)
    val gauge = statsClient.gauge(Element("test"), Element("stat"))
    gauge.add(5)
    gauge.sub(5)
    val elementsCaptor = ArgumentCaptor.forClass(String::class.java)
    val amountCaptor = ArgumentCaptor.forClass(Int::class.java)
    verify(envoyEngine).recordGaugeSub(elementsCaptor.capture(), amountCaptor.capture())
    assertThat(elementsCaptor.getValue()).isEqualTo("test.stat")
    assertThat(amountCaptor.getValue()).isEqualTo(5)
  }
}
