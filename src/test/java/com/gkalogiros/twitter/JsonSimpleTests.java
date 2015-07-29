package com.gkalogiros.twitter;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;
import static org.junit.Assert.*;

public class JsonSimpleTests {

    String apiResponse = "{  \n" +
            "   \"created_at\":\"Sun Jul 26 14:28:55 +0000 2015\",\n" +
            "   \"text\":\"RT @claudenougat: Europe vs. Greece: the Euro's survival at stake, could endanger American recovery #ASMSG #IARTG http:\\/\\/t.co\\/rtKJ2hrbQ2\",\n" +
            "   \"user\":{  \n" +
            "      \"id\":1738103420,\n" +
            "      \"id_str\":\"1738103420\",\n" +
            "      \"name\":\"lisa kay\",\n" +
            "      \"screen_name\":\"LKaybayareagirl\",\n" +
            "      \"location\":\"\",\n" +
            "      \"description\":\"Lovin' life!\",\n" +
            "      \"followers_count\":992,\n" +
            "      \"friends_count\":509,\n" +
            "      \"listed_count\":225,\n" +
            "      \"favourites_count\":380,\n" +
            "      \"statuses_count\":72145,\n" +
            "      \"created_at\":\"Fri Sep 06 22:56:59 +0000 2013\",\n" +
            "      \"utc_offset\":-10800,\n" +
            "      \"time_zone\":\"Atlantic Time (Canada)\",\n" +
            "      \"geo_enabled\":false,\n" +
            "      \"lang\":\"en\",\n" +
            "   },\n" +
            "}";

    @Test
    public void thatSimpleJsonWorks() throws ParseException {
        JSONParser parser = new JSONParser();

        // Tweet Message
        JSONObject json = (JSONObject) parser.parse(apiResponse);
        String text = (String) json.get("text");

        // User ID
        JSONObject user = (JSONObject) json.get("user");
        long l = (Long) user.get("id");

        assertEquals(
                "RT @claudenougat: Europe vs. Greece: the Euro's survival at stake, " +
                        "could endanger American recovery #ASMSG #IARTG http://t.co/rtKJ2hrbQ2", text);

        assertEquals(l, 1738103420);
    }
}
