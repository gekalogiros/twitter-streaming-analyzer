package com.gkalogiros.apis;

import com.gkalogiros.models.Tweet;
import com.google.common.base.Strings;
import org.apache.http.Header;
import org.apache.http.HttpVersion;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.apache.http.message.BasicHeader;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import static com.google.common.base.Strings.isNullOrEmpty;

public class SentimentAnalyzerImpl implements SentimentAnalyzer {

    /*
     * ====================================================================================================
     * CONSTANTS
     * ====================================================================================================
     */
    private static final String API = "http://access.alchemyapi.com/calls/text/TextGetTextSentiment";
    private static final String RESPONSE_PARAMETER = "outputMode";
    private static final String RESPONSE_VALUE = "json";
    private static final String APIKEY_PARAMETER = "apikey";
    private static final String TEXT_PARAMETER = "text";
    private static final String DOC_SENTIMENT = "docSentiment";
    private static final String SCORE = "score";

    /*
     * ====================================================================================================
     * INSTANCE VARIABLES
     * ====================================================================================================
     */
    private JSONParser parser;
    private String apiKey;
    private String baseApiUrl;

    /*
     * ====================================================================================================
     * CONSTRUCTORS
     * ====================================================================================================
     */
    public SentimentAnalyzerImpl(String apiKey)
    {
        this.apiKey = apiKey;
        this.parser = new JSONParser();
        this.baseApiUrl = buildBaseUrl();
    }

    /*
     * ====================================================================================================
     * PUBLIC
     * ====================================================================================================
     */
    @Override
    public Tweet addSentiment(Tweet tweet)
    {
        if (isNullOrEmpty(tweet.getContent())) return tweet;
        try
        {
            final String reqUrl = buildRequestUrlForText(tweet.getEncodedContent());
            final String sentiment = requestSentiment(reqUrl);
            final double score = extractSentimentScore(sentiment);
            tweet.setScore(score);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        } catch (ParseException e)
        {
            e.printStackTrace();
        }
        return tweet;
    }

    /*
     * ====================================================================================================
     * PRIVATE
     * ====================================================================================================
     */
    private String buildBaseUrl()
    {
        StringBuffer buff = new StringBuffer(API);
        buff.append("?").append(APIKEY_PARAMETER).append("=").append(apiKey);
        buff.append("&").append(RESPONSE_PARAMETER).append("=").append(RESPONSE_VALUE);
        return buff.toString();
    }

    private String buildRequestUrlForText(String text) throws UnsupportedEncodingException {
        StringBuffer buff = new StringBuffer(baseApiUrl);
        buff.append("&").append(TEXT_PARAMETER).append("=").append(text);
        return buff.toString();
    }

    private double extractSentimentScore(String response) throws ParseException {
        JSONObject json = (JSONObject) parser.parse(response);
        JSONObject sentiment = (JSONObject) json.get(DOC_SENTIMENT);
        if (null == sentiment) return 0;
        String score = (String) sentiment.get(SCORE);
        return (Strings.isNullOrEmpty(score)) ? 0 : Double.valueOf(score);
    }

    private String requestSentiment(String reqUrl) throws IOException {
        Header header =
                new BasicHeader("Content-Type",
                        ContentType.APPLICATION_FORM_URLENCODED.getMimeType());

        return  Request.Post(reqUrl)
                .addHeader(header)
                .useExpectContinue()
                .version(HttpVersion.HTTP_1_1)
                .execute()
                .returnContent()
                .asString();
    }

}
