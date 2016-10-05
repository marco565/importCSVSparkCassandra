# Import CVS files with spark
This short example will show to you how easily is to import CSV files from your AWS S3 buckets 
using spark into cassandra.

## Setting up your applicaiton
Clone this repository `git clone http://gitlab.ippon.fr/mthebault/simplecsvexportspark.git`
Open the file 'src/main/ressources/project.conf' and change your settings.

**You need to change the following values:**

- Cassandra
    - host
    - port
    - keyspace
    - table
- AWS
    - accessKey
    - secretKey
    - bucket
    - fileName

## Build a jar
To build the Jar of your this application you just need to run the command `sbt clean assembly`

## Deploy the Jar on a spark cluster
To deploy a jar on a spark cluster you have to make sure you have the port 7077 accessible from the outside.
You have to push this Jar to a S3 public bucket `aws s3 cp ./target/scala-2.10/ImportCSV.jar s3://YOUR_BUCKET/ImportCVS.jar`

***Once you have done that, you just need to run the spark-submit command as following:***
```
$SPARK_HOME/bin/spark-submit \
	--verbose \
	--master spark://IP_SPARK_MASTER:PORT \
	--deploy-mode cluster \
	--driver-class-path /spark/spark-1.6.1-bin-hadoop2.6/lib/spark-assembly-1.6.1-hadoop2.6.0.jar \
	--class Application \
	https://s3-eu-west-1.amazonaws.com/YOUR_BUCKET/ImportCSV.jar
```

***Note: ***
 Here I am using a public s3 bucket for the jars. If you want to use your private buckets you can use the following link:
 `http://AWS_S3_ACCESS_KEY:AWS_S3_SECRET_KEY@YOUR_BUCKET/ImportCSV.jar` 
 please be aware of the http link have to be formatted [you can use this website to format the link](http://www.freeformatter.com/url-encoder.html)
## Next
If you want to contribute to this project feel free to do it, if you see some mistake please leave me an issue.