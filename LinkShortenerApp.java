
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class LinkShortenerApp {

    private static final String BASE_URL = "http://sho.rt/";
    private static final String CHAR_SET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int SHORT_URL_LENGTH = 6;

    private Map<String, String> shortToLong = new HashMap<>();
    private Map<String, String> longToShort = new HashMap<>();
    private Random random = new Random();

    public static void main(String[] args) {
        LinkShortenerApp app = new LinkShortenerApp();
        app.run();
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n=== Link Shortener ===");
            System.out.println("1. Shorten URL");
            System.out.println("2. Expand URL");
            System.out.println("3. Exit");
            System.out.print("Choose: ");
            while (!scanner.hasNextInt()) {
                System.out.print("Please enter a valid number: ");
                scanner.next();
            }
            choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter long URL: ");
                    String longUrl = scanner.nextLine();
                    if (!isValidURL(longUrl)) {
                        System.out.println("Invalid URL format.");
                        break;
                    }
                    String shortUrl = shortenURL(longUrl);
                    System.out.println("Shortened URL: " + shortUrl);
                    break;

                case 2:
                    System.out.print("Enter short URL: ");
                    String inputShort = scanner.nextLine();
                    String original = expandURL(inputShort);
                    System.out.println("Original URL: " + original);
                    break;

                case 3:
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        } while (choice != 3);

        scanner.close();
    }

    // Shorten the long URL
    public String shortenURL(String longURL) {
        if (longToShort.containsKey(longURL)) {
            return BASE_URL + longToShort.get(longURL);
        }

        String shortKey;
        do {
            shortKey = generateKey();
        } while (shortToLong.containsKey(shortKey));

        shortToLong.put(shortKey, longURL);
        longToShort.put(longURL, shortKey);
        return BASE_URL + shortKey;
    }

    // Expand the short URL
    public String expandURL(String shortURL) {
        if (!shortURL.startsWith(BASE_URL)) {
            return "Invalid short URL!";
        }
        String key = shortURL.replace(BASE_URL, "");
        return shortToLong.getOrDefault(key, "Short URL not found!");
    }

    // Generate random short key
    private String generateKey() {
        StringBuilder key = new StringBuilder();
        for (int i = 0; i < SHORT_URL_LENGTH; i++) {
            int index = random.nextInt(CHAR_SET.length());
            key.append(CHAR_SET.charAt(index));
        }
        return key.toString();
    }

    // Basic URL format validation
    private boolean isValidURL(String url) {
        return url.startsWith("http://") || url.startsWith("https://");
    }
}
