package praktikum;

import org.apache.commons.lang3.RandomStringUtils;

public class UserGenerator {
    public static User random() {
        return new User(RandomStringUtils.randomAlphabetic(6) + "@gmail.com", "9999", "Ivan");
    }
}
