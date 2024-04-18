package com.example.demo.cashcard;

import com.example.demo.cashcard.CashCard;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
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

    @Autowired
    private JacksonTester<CashCard[]> jsonList;

    private CashCard[] cashCards;

    @BeforeEach
    void setUp() {
        cashCards = Arrays.array(
                new CashCard(99L, 123.45, "sarah1"),
                new CashCard(100L, 1.00, "sarah1"),
                new CashCard(101L, 150.00, "sarah1"));
    }

    @Test
    public void cashSerializationTest() throws IOException {
        CashCard cashCard = new CashCard(99L, 123.45, "sarah1");
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
               "amount":123.45,
               "owner": "sarah1"
           }
           """;
        assertThat(jacksonTester.parse(expected))
                .isEqualTo(new CashCard(99L, 123.45, "sarah1"));
        assertThat(jacksonTester.parseObject(expected).id()).isEqualTo(99);
        assertThat(jacksonTester.parseObject(expected).amount()).isEqualTo(123.45);
    }

    @Test
    void cashCardListSerializationTest() throws IOException {
        cashCards = Arrays.array(
                new CashCard(99L, 123.45, "sarah1"),
                new CashCard(100L, 1.00, "sarah1"),
                new CashCard(101L, 150.00, "sarah1"));
        assertThat(jsonList.write(cashCards)).isStrictlyEqualToJson("list.json");
    }


    @Test
    void cashCardListDeserializationTest() throws IOException {
        String expected="""
         [
            { "id": 99, "amount": 123.45, "owner": "sarah1" },
            { "id": 100, "amount": 1.00 , "owner": "sarah1"},
            { "id": 101, "amount": 150.00 , "owner": "sarah1"}
         ]
         """;
        assertThat(jsonList.parse(expected)).isEqualTo(cashCards);
    }

}
