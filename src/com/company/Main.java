package com.company;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Main {



    public static void main(String[] args) {
	// write your code here
        String uri = "https://hp.beget.ru/rasp";
        URL url;
        ScriptEngineManager engine = new ScriptEngineManager();
        ScriptEngine jsHandler = engine.getEngineByName("nashorn");
        try {
            url = new URL(uri);
            URLConnection connection = url.openConnection();
            connection.setRequestProperty("Cookie",
                    "_ym_uid=1546548892181907466; _ym_d=1546548892; searchParametersOnSearchClientPage={\"results_amount\":\"10\"}; begetInnerJWT=eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1aWQiOjMzMiwidW5hbWUiOiJlYXJ0ZW1vdiIsImV4cCI6MTU2MjAyOTgyNywiaWF0IjoxNTYxNDI1MDI3LCJpc3MiOiJiZWdldEVudGVyIn0.rjVwKFtdx3SYGlTGpkeymGk9aO7jLi4GlNG5vSkE4T9SWlOKeFeU0jprxY3SjCxP2ZDNtlRRxiBes0ZvjRU00q_Fm7SzPsobTqDlqDUUQjQ6MJv22X9d5WgUL-KGsYScKsKq_E8y2hpl9BAc3gcmnRpttYNxo3TdCAvCxteUg5TIn8g3v7pkaXlDAXLuWDLh4GCEyAkcyn9AVr5Fhntx-rlMNZZn3tMMjS0c9l1E9t5YlVOjGl4puTsLkB8P-A6vfpZ7k1DDaPMDNHN-di6trzSKebahoo8cHK3GGv96B4omfUlWlCiGavxsd3BuPX6vVNEYO24kAHDeLSGFiwenlAohcLARImUXODLaiGZMjjr_LKCQSAWCL8KcbPNr_OYyPz5-JS8dc4UrNj5cn73EBLDfAit-EshYel_8kj6sHrVwxUBTd2bfPpCEu4db-xFGqi0v1Fmo5u4XQN1L6ByYbU6cnMNR4dhaIc5bc0P1wnUanE8-kt03w4J0M94sRCH8Ccxs3ItX0hBUpFAKFnwSiHhlx-UoAB_cPDbKy5plY4zYuCJUBVg-ktsUrSs--8zwKukHhsRz7cYUuYnK70zJMlegAnpCRGWkPIzpkYIPOLnZQPftR8P4KmAznFGbxx_QtpyNwouX_9RosvIgAHiN3NYvIxE7pocdkew8L-xhHL4; hpbegetru=5ge5bfjqf57qahg3cu80k3a5i7; _ym_isad=2");
            InputStreamReader input = new InputStreamReader(connection.getInputStream(), "UTF-8");
            BufferedReader stream = new BufferedReader(input);

            while(true){
                String line = stream.readLine();
                if (line == null) break;
                if (line.contains("<script")&&line.contains("js")) {
                    jsHandler.eval(jsGetter(jsUrlBuilder(line)));
//                    System.out.println(jsGetter(jsUrlBuilder(line)));
                }
                //System.out.println(line);
            }
        } catch (IOException | ScriptException e) {
            e.printStackTrace();
        }


    }

    private static String jsUrlBuilder(String line){
        String tmp = line;
        if (line.contains("\"")) {
            tmp = line.replace("<script src=\"", "https://hp.beget.ru");
            tmp = tmp.replace("\"></script>", "");
        }
        if (line.contains("'")) {
            tmp = line.replace("<script src='", "https://hp.beget.ru");
            tmp = tmp.replace("'></script>", "");
        }
        return tmp;
    }

    private static String jsGetter(String jsUrl){
        String result = "";
             try {
            URL url = new URL(jsUrl);
            BufferedReader stream = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream(), "UTF-8"));
            String tmp = stream.readLine();
            while (tmp != null) {
                result+=tmp;
                result+="\r\n";
                tmp = stream.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
//        result+="}";
        //System.out.println(result);
        return result;
    }
}
