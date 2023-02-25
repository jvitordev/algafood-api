//package com.algaworks.algafood;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//
//@SpringBootTest
//class AlgafoodApiApplicationTests {
//
//	@Test
//	void contextLoads() {
//	}
//
//}

package com.algaworks.algafood;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AlgafoodApiApplicationTests {

	@Test
	public void contextLoads() {
		assertFalse(2 + 2 == 4);
	}

}