package com.example.demo.cashcard;

import com.example.demo.cashcard.CashCard;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class CashCardJsonTest {

    @Autowired
    private JacksonTester<CashCard> jacksonTester;

    @Test
    public void cashSerializationTest() throws IOException {
        CashCard cashCard = new CashCard(99L, 123.45);
        assertThat(jacksonTester.write(cashCard)).isStrictlyEqualToJson("expected.json");
        assertThat(jacksonTester.write(cashCard)).hasJsonPathNumberValue("@.id");
        assertThat(jacksonTester.write(cashCard)).extractingJsonPathNumberValue("@.id")
                .isEqualTo(99);
        assertThat(jacksonTester.write(cashCard)).hasJsonPathNumberValue("@.amount");
        assertThat(jacksonTester.write(cashCard)).extractingJsonPathNumberValue("@.amount")
                .isEqualTo(123.45);
    }


    @Test
    void cashCardDeserializationTest() throws IOException {
        String expected = """
           {
               "id":99,
               "amount":123.45
           }
           """;
        assertThat(jacksonTester.parse(expected))
                .isEqualTo(new CashCard(99L, 123.45));
        assertThat(jacksonTester.parseObject(expected).id()).isEqualTo(99);
        assertThat(jacksonTester.parseObject(expected).amount()).isEqualTo(123.45);
    }

}
