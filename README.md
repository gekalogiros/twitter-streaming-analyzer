# Twitter Streaming Api - Example

## Overview
This is a simple application that makes use of Apache Lucene and Twitter's Streaming API.

## Get Started

1. Download project from GitHub.
2. Connect to a wifi/LAN network.
3. Open code in Intellij or Eclipse
4. Update the file app.properties with the following security details (Please visit Twitter's dev portal for more details about how you can obtain these):
   1. App Token
   2. App Secret
   3. Consumer Key
   4. Consumer Secret	 
5. Run main() method in com.gkalogiros.main.Main
6. Check console Output.

## Details
This is a standalone tool that doesn't have any dependencies on 3d party services. The application starts in two threads. The first one reads Tweet messages from Twitter's public stream and sends them off to Lucene for indexing. The second thread queries Lucene every 5 seconds and gets answers to the following questions:

1. What is the total count of tweets matching the search term seen so far?
2. How many tweets containing the search term were there in the last 1, 5 and 15 minutes?
3. What are the ten most frequent terms (excluding the search term) that appear in tweets containing the
search term over the last 1, 5 and 15 minutes?
4. Within tweets matching the search term, who were the top ten tweeps (Twitter users) who tweeted the most in the last 1, 5 and 15 minutes?

## Improvements
1. Use sentiment analysis for printing out the sentiment of the tweets published in the last 1,5 and 15 minutes.
2. The project uses an In-memory Lucene index so the available memory limits the amount of tweet messages that can be stored in the computer running the project.
3. Use multiple threads when writing to/reading from the index.
4. Create RESTful APIs for querying the data.
5. Replace Lucene with a distributed storage layer e.g ElasticSearch

## Projects Used
1. <https://github.com/twitter/hbc>


## Licence
License Copyright 2015, Georgios Kalogiros. Licensed under the MIT license: <http://www.opensource.org/licenses/mit-license.php>







