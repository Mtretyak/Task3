import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Application {

    public static void main(String... args) {
        Scanner in = new Scanner(System.in);
        String input = in.nextLine();

        System.out.println(translate("en-ru", input));
    }

    private static String translate(String lang, String input) {
        String myUrl = "https://translate.yandex.net/api/v1.5/tr.json/translate?key=trnsl." +
                "1.1.20170206T091812Z.774ed592e0b9fc82.6a46feab308c193316c8ddd5e4b2f12750708fe8";
        String parameters = "text=" + input + "&lang=" + lang;
        String resultString = null;
        try {
            URL url = new URL(myUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);

            DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream());
            dataOutputStream.writeBytes(parameters);
            InputStream response = connection.getInputStream();
            resultString = new java.util.Scanner(response).nextLine();
            int responseCode = connection.getResponseCode();

            if (responseCode == 200) {
                int start = resultString.indexOf("[");
                int end = resultString.lastIndexOf("]"); // can get input = "bad]"
                resultString = resultString.substring(start + 2, end - 1);
            } else {
                System.out.println("http error:" + responseCode);
            }

        } catch (MalformedURLException e) {
            System.out.println("MalformedURLException");
            e.printStackTrace();
        } catch (ProtocolException e) {
            System.out.println("ProtocolException");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("IOException");
            e.printStackTrace();
        }
        return resultString;
    }
}
