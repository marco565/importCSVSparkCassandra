# Import CVS files into cassandra form an S3 bucket using spark

## Setting up your applicaiton
Clone this repository `git clone http://gitlab.ippon.fr/mthebault/simplecsvexportspark.git`
Open the file 'src/main/ressources/project.conf' and change your settings.
Don't forget to change the IP/Port of the cassandra Host

## Build a jar
To build the Jar of your this application you just need to run the command `sbt clean assembly`

## Deploy the Jar on a spark cluster
To deploy a jar on a spark cluster you have to make sure you have the port 7077 accessible from the outside.
You have to push this Jar to a S3 public bucket 'aws s3 cp ./target/scala-2.10/ImportCSV.jar s3://YOUR_BUCKET/ImportCVS.jar'

Once you have done that, you just need to run the spark-submit command as following:
```
$SPARK_HOME/bin/spark-submit \
	--verbose \
	--master spark://IP_SPARK_MASTER:PORT \
	--deploy-mode cluster \
	--driver-class-path /spark/spark-1.6.1-bin-hadoop2.6/lib/spark-assembly-1.6.1-hadoop2.6.0.jar \
	--class Application \
	https://s3-eu-west-1.amazonaws.com/YOUR_BUCKET/ImportCSV.jar
```