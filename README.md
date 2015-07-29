# Twitter Streaming Api - Example

## Overview
This is a simple application that makes use of Apache Lucene and Twitter's Streaming API.

## Get Started

1. Download Distribution directory.
2. Connect to a Wifi/LAN network.
3. Update the file app.properties with the following security details (Please visit Twitter's dev portal for more details about how you can obtain these):
   1. App Token
   2. App Secret
   3. Consumer Key
   4. Consumer Secret	 
4. On Windows
   * Double-click twitter-search-example.cmd if you are on Windows.
5. On Linux
   * Open Console in distribution folder and execute "bash twitter-search-example.sh 
6. Check console Output.

## Details
This is a standalone tool that doesn't have any dependencies on 3d party services. The application starts in two threads. The first one reads Tweet messages from Twitter's public stream and sends them off to Lucene for indexing. The second thread queries Lucene every 5 seconds and gets answers to the following questions:

1. What is the total count of tweets matching the search term seen so far?
2. How many tweets containing the search term were there in the last 1, 5 and 15 minutes?
3. What are the ten most frequent terms (excluding the search term) that appear in tweets containing the
search term over the last 1, 5 and 15 minutes?
4. Within tweets matching the search term, who were the top ten tweeps (Twitter users) who tweeted the most in the last 1, 5 and 15 minutes?

## Tokenization
The terms tokenization is done by Lucene's Standard Analyzer. The Standard Analyzer uses a Standard tokenizer that splits words based on spaces and stop characters. Many tweets contain urls in the form of http://www.google.com. The Standard Analyzer will split that in two tokens -> http and www.google.com. It is easy to change this behaviour by creating a custom Lucene Analyzer.


## Improvements
1. Use sentiment analysis for printing out the sentiment of the tweets published in the last 1,5 and 15 minutes.
2. The project uses an In-memory Lucene index for storing tweet messages. If the available memory is limited then the in memory index can store a limited number of tweets too.
3. Use multiple threads when writing to/reading from the index.
4. Create RESTful APIs for querying the data.
5. Replace Lucene with a distributed storage layer e.g ElasticSearch

## Testing
The distribution version of the current tool has been tested on OS X Yosemite and Java 1.7 only. It should be able to run on any computer running java 1.5 and above.


## Libraries/APIs
1. <https://github.com/twitter/hbc>


## Licence
License Copyright 2015, Georgios Kalogiros. Licensed under the MIT license: <http://www.opensource.org/licenses/mit-license.php>







