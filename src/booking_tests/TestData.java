package booking_tests;

import java.util.Random;

public class TestData {
	static Random rand = new Random();

	public static String firstName = "Abdullrahman";
	public static String lastName = "Ghazal";
	public static String CardHolderName = firstName + " " + lastName;
	public static String phoneNumber = "69657155";
	public static String guestEmail = firstName + lastName + rand.nextInt(7000) + "@gmail.com";
	public static String VisaNumber = "4111111111111111";
	public static String VisaexpiryDate = "12/29";
	public static String VisaCVC = "123";

	public static String LoginPage = "https://account.booking.com/sign-in?op_token=EgVvYXV0aCL6AgoUdk8xS2Jsazd4WDl0VW4yY3BaTFMSCWF1dGhvcml6ZRo1aHR0cHM6Ly9zZWN1cmUuYm9va2luZy5jb20vbG9naW4uaHRtbD9vcD1vYXV0aF9yZXR1cm4qmQJVck1CN01PUXpwVGtvejVXeUlQMldURXJWUDV5SlI1NHJNUDZUeTAxUHZZYmNmZFppWUgyN2MwekNzdjdjS0lZU19uUmtjdVZjR183ZGRWc1FMaGx1dVF2aXlwbVdzLUtZeERVOXBpYXNkT0I0cGlHWVZMak9WR0NNX0VjX09PVHJGbW1mR1RtY1dUU1ZuTno2ZzBTcmtMb2kyY21Wd2NybWRHQVNkN3EzVlozeHhnMUZTRVFxMmNrUDN6dHJzUEZPMjBCM2pWUlFEY2l6aG02UndVVGZpZkZoVUpsRGJlWlhkdlFkNDE2WlFXQmVjQzl0bzQ9KmV5SnBaQ0k2SW5SeVlYWmxiR3hsY2w5b1pXRmtaWElpZlE9PUIEY29kZSoyCI7IEjD9v-G9hv0nOgBCAFiH4pjplTOSARB0cmF2ZWxsZXJfaGVhZGVymgEFaW5kZXg";
	public static String LoginEmail = "abodghazal172002@gmail.com";
}
