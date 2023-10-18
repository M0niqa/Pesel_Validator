package assignment9;

import java.time.LocalDate;
import java.util.Objects;

public class Pesel {
    private final String pesel;
    private final Sex sex;
    private final LocalDate birthDate;

    public Pesel(String pesel) {
        if (pesel == null || pesel.length() != 11 || !isValid(pesel)) {
            throw new IllegalArgumentException("This pesel is invalid");
        }
        this.pesel = pesel;
        sex = extractSex(pesel);
        birthDate = extractBirthDate(pesel);
    }

    public String getPesel() {
        return pesel;
    }

   private static boolean isValid(String pesel) {
       String[] arrPesel = pesel.split("");
       int sum = 0;
       for (int i = 0; i < arrPesel.length-1; i++) {
           sum = sum + Integer.parseInt(arrPesel[i]) * getMultiplier(i+1);
       }
       int modulo = sum%10;
       int lastDigit = Character.getNumericValue(pesel.charAt(10));

       return ((modulo == 0 && lastDigit == 0) || lastDigit == 10 - modulo);
    }

    private static int getMultiplier(int index) {
        return switch (index % 4) {
            case 2 -> 3;
            case 3 -> 7;
            case 0 -> 9;
            default -> 1;
        };
    }

    public LocalDate birthDate() {
        return birthDate;
    }

    private static LocalDate extractBirthDate(String pesel) {
        int birthYear = Integer.parseInt(pesel.substring(0, 2));
        int birthMonth = Integer.parseInt(pesel.substring(2, 4));
        int birthDay =  Integer.parseInt(pesel.substring(4, 6));

        if (birthMonth > 0 && birthMonth < 13) {
            birthYear += 1900;
        }
        else if (birthMonth > 20 && birthMonth < 33) {
            birthYear += 2000;
            birthMonth -= 20;
        }
        else if (birthMonth > 40 && birthMonth < 53) {
            birthYear += 2100;
            birthMonth -= 40;
        }
        else if (birthMonth > 60 && birthMonth < 73) {
            birthYear += 2200;
            birthMonth -= 60;
        }
        if (birthMonth > 80 && birthMonth < 93) {
            birthYear += 1800;
            birthMonth -= 80;
        }
        return LocalDate.of(birthYear, birthMonth, birthDay);
    }

    public Sex sex() {
        return sex;
    }

    private static Sex extractSex(String pesel1) {
        int sexDigit = Character.getNumericValue(pesel1.charAt(9));
        if ((sexDigit%2) == 0) {
            return Sex.FEMALE;
        }
        return Sex.MALE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pesel pesel1)) return false;
        return Objects.equals(pesel, pesel1.pesel) && sex == pesel1.sex && Objects.equals(birthDate, pesel1.birthDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pesel, sex, birthDate);
    }

    @Override
    public String toString() {
        return "Pesel{" +
                "pesel='" + pesel + '\'' +
                ", sex=" + sex +
                ", birthDate=" + birthDate +
                '}';
    }
}
