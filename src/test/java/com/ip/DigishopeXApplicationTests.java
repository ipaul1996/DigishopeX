package com.ip;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class DigishopeXApplicationTests {

	@Test
	void contextLoads() {
	}
	
	@Test
	void testSum() {
		assertThat((2 + 2)).isEqualTo(4);
	}

}
