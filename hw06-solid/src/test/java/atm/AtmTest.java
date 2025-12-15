package atm;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.atm.Atm;
import ru.atm.AtmImpl;
import ru.atm.Banknote;

class AtmTest {

    private Atm atm;

    @BeforeEach
    void setUp() {
        Set<Banknote> acceptedMoney =
                new HashSet<>(Arrays.asList(Banknote.RUB_100, Banknote.RUB_500, Banknote.RUB_1000, Banknote.RUB_5000));
        atm = new AtmImpl(acceptedMoney);
    }

    @Test
    void testPutMoney() {
        List<Banknote> money = Arrays.asList(Banknote.RUB_100, Banknote.RUB_500, Banknote.RUB_1000, Banknote.RUB_5000);

        List<Banknote> rejected = atm.putMoney(money);

        assertEquals(0, rejected.size());
        assertEquals(6600, atm.getBalance());
    }

    @Test
    void testPutMoneyWithIncorrectBanknote() {
        List<Banknote> money = Arrays.asList(Banknote.RUB_50, Banknote.RUB_100, Banknote.RUB_500, Banknote.RUB_5000);

        List<Banknote> rejected = atm.putMoney(money);

        assertEquals(1, rejected.size());
        assertEquals(Banknote.RUB_50, rejected.getFirst());
        assertEquals(5600, atm.getBalance());
    }

    @Test
    void testGetMoney() {
        atm.putMoney(Arrays.asList(Banknote.RUB_5000, Banknote.RUB_1000, Banknote.RUB_500, Banknote.RUB_100));

        List<Banknote> result = atm.getMoney(6000);

        assertEquals(2, result.size());
        assertTrue(result.contains(Banknote.RUB_5000));
        assertTrue(result.contains(Banknote.RUB_1000));
        assertEquals(600, atm.getBalance());
    }

    @Test
    void testGetMoneyWithCorrectBanknote() {
        atm.putMoney(Arrays.asList(
                Banknote.RUB_5000,
                Banknote.RUB_1000,
                Banknote.RUB_1000,
                Banknote.RUB_500,
                Banknote.RUB_100,
                Banknote.RUB_100));

        List<Banknote> result = atm.getMoney(6200);

        Map<Banknote, Long> countByBanknote =
                result.stream().collect(Collectors.groupingBy(b -> b, Collectors.counting()));

        assertEquals(1, countByBanknote.getOrDefault(Banknote.RUB_5000, 0L));
        assertEquals(1, countByBanknote.getOrDefault(Banknote.RUB_1000, 0L));
        assertEquals(2, countByBanknote.getOrDefault(Banknote.RUB_100, 0L));
        assertEquals(4, result.size());
        assertEquals(1500, atm.getBalance());
    }

    @Test
    void testGetMoneyNotEnoughMoney() {
        atm.putMoney(List.of(Banknote.RUB_1000));

        assertThrows(IllegalStateException.class, () -> atm.getMoney(2000));
    }

    @Test
    void testGetMoneyNotRightAmount() {
        atm.putMoney(Arrays.asList(Banknote.RUB_1000, Banknote.RUB_1000));

        assertThrows(IllegalStateException.class, () -> atm.getMoney(500));
    }
}
