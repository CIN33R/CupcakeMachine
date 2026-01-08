import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

public class CupcakeMachineTest {

    // --- Reflection helpers to verify private state updates ---
    private static int getIntField(Object obj, String fieldName) {
        try {
            Field f = obj.getClass().getDeclaredField(fieldName);
            f.setAccessible(true);
            return f.getInt(obj);
        } catch (Exception e) {
            fail("Could not access int field '" + fieldName + "'.");
            return -1;
        }
    }

    private static double getDoubleField(Object obj, String fieldName) {
        try {
            Field f = obj.getClass().getDeclaredField(fieldName);
            f.setAccessible(true);
            return f.getDouble(obj);
        } catch (Exception e) {
            fail("Could not access double field '" + fieldName + "'.");
            return Double.NaN;
        }
    }

    @Test
    void constructor_initializesFieldsCorrectly() {
        CupcakeMachine m = new CupcakeMachine(12, 2.50);

        assertEquals(12, getIntField(m, "cupcakesInMachine"),
                "cupcakesInMachine should start at numCupcakes");
        assertEquals(1, getIntField(m, "nextOrderNumber"),
                "nextOrderNumber should start at 1");
        assertEquals(2.50, getDoubleField(m, "costPerCupcake"), 0.0000001,
                "costPerCupcake should be set from the constructor parameter");
    }

    @Test
    void takeOrder_rejectsWhenNotEnoughCupcakes() {
        CupcakeMachine m = new CupcakeMachine(3, 2.00);

        String msg = m.takeOrder(4);
        assertEquals("Order cannot be filled", msg);

        // State should NOT change when rejected
        assertEquals(3, getIntField(m, "cupcakesInMachine"));
        assertEquals(1, getIntField(m, "nextOrderNumber"));
    }

    @Test
    void takeOrder_acceptsAndReturnsCorrectMessage() {
        CupcakeMachine m = new CupcakeMachine(10, 2.50);

        String msg = m.takeOrder(3);
        assertEquals("Order number 1, cost $7.5", msg);

        assertEquals(7, getIntField(m, "cupcakesInMachine"),
                "cupcakesInMachine should decrease by cupcakesOrdered");
        assertEquals(2, getIntField(m, "nextOrderNumber"),
                "nextOrderNumber should increment after a successful order");
    }

    @Test
    void takeOrder_multipleOrdersIncrementOrderNumbersAndDecrementInventory() {
        CupcakeMachine m = new CupcakeMachine(8, 1.25);

        assertEquals("Order number 1, cost $2.5", m.takeOrder(2));
        assertEquals(6, getIntField(m, "cupcakesInMachine"));
        assertEquals(2, getIntField(m, "nextOrderNumber"));

        assertEquals("Order number 2, cost $3.75", m.takeOrder(3));
        assertEquals(3, getIntField(m, "cupcakesInMachine"));
        assertEquals(3, getIntField(m, "nextOrderNumber"));

        // This one should fail (trying to order 4 with only 3 left)
        assertEquals("Order cannot be filled", m.takeOrder(4));
        assertEquals(3, getIntField(m, "cupcakesInMachine"),
                "Inventory should not change after a rejected order");
        assertEquals(3, getIntField(m, "nextOrderNumber"),
                "Order number should not change after a rejected order");
    }

    @Test
    void takeOrder_orderExactlyRemainingCupcakesIsAllowed() {
        CupcakeMachine m = new CupcakeMachine(5, 2.00);

        assertEquals("Order number 1, cost $10.0", m.takeOrder(5));
        assertEquals(0, getIntField(m, "cupcakesInMachine"));
        assertEquals(2, getIntField(m, "nextOrderNumber"));
    }
}
