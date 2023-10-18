import assignment9.Pesel;
import assignment9.Sex;
import org.testng.annotations.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;

import static org.testng.Assert.assertThrows;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

public class PeselTest {

    @Test
    public void shouldDetermineIfPeselIsValid() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<Pesel> peselClass = Pesel.class;
        Method isValid = peselClass.getDeclaredMethod("isValid", String.class);
        isValid.setAccessible(true);

        Object peselValid = isValid.invoke(null, "53020155371");

        assertTrue((boolean) peselValid);
    }

    @Test
    public void shouldReturnMultiplier() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<Pesel> peselClass = Pesel.class;
        Method getMultiplier = peselClass.getDeclaredMethod("getMultiplier", int.class);
        getMultiplier.setAccessible(true);

        assertEquals(1, (int) getMultiplier.invoke(null,1));
        assertEquals(3, (int) getMultiplier.invoke(null,2));
        assertEquals(7, (int) getMultiplier.invoke(null,3));
        assertEquals(9, (int) getMultiplier.invoke(null,4));
        assertEquals(1, (int) getMultiplier.invoke(null,5));
        assertEquals(3, (int) getMultiplier.invoke(null,6));
        assertEquals(7, (int) getMultiplier.invoke(null,7));
        assertEquals(9, (int) getMultiplier.invoke(null,8));
        assertEquals(1, (int) getMultiplier.invoke(null,9));
    }

    @Test
    public void shouldExtractDate() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<Pesel> peselClass = Pesel.class;
        Method extractBirthdate = peselClass.getDeclaredMethod("extractBirthDate", String.class);
        extractBirthdate.setAccessible(true);

        assertEquals(LocalDate.of(1953, 2, 1), extractBirthdate.invoke(null, "53020155371"));
    }

    @Test
    public void shouldExtractSex() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<Pesel> peselClass = Pesel.class;
        Method extractSex = peselClass.getDeclaredMethod("extractSex", String.class);
        extractSex.setAccessible(true);

        assertEquals(Sex.MALE, extractSex.invoke(null, "53020155371"));
    }

    @Test
    public void invalidPeselShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> new Pesel("71082707730"));
    }
}
