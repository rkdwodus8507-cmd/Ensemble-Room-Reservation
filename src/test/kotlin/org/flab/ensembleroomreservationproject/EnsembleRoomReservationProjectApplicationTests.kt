package org.flab.ensembleroomreservationproject

import org.flab.ensembleroomreservationproject.support.TestContainersConfig
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import

@SpringBootTest
@Import(TestContainersConfig::class)
class EnsembleRoomReservationProjectApplicationTests {

    @Test
    fun contextLoads() {
    }
}
