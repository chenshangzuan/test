/*
 * Fabric4cloud.com Inc.
 * Copyright (c) 2015-2020 All Rights Reserved.
 */
package sdk_test.minioTest;

import io.minio.MinioClient;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;

public class MinioTest {

    public static void main(String[] args) {
        try {
            // Create a minioClient with the MinIO Server name, Port, Access key and Secret key.
            MinioClient minioClient = null;
            try {
                minioClient = new MinioClient("http://172.16.5.56:9000", "minioadmin", "minioadmin");
            } catch (InvalidEndpointException ex) {
                ex.printStackTrace();
            } catch (InvalidPortException ex) {
                ex.printStackTrace();
            }

            // Check if the bucket already exists.
            boolean isExist = minioClient.bucketExists("asiatrip");
            if(isExist) {
                System.out.println("Bucket already exists.");
            } else {
                // Make a new bucket called asiatrip to hold a zip file of photos.
                minioClient.makeBucket("asiatrip");
            }

            // Upload the zip file to the bucket with putObject
            //minioClient.putObject("asiatrip","asiaphotos.zip", "/Users/kled/git/test/src/main/resources/books.xml", null);
            System.out.println("/home/user/Photos/asiaphotos.zip is successfully uploaded as asiaphotos.zip to `asiatrip` bucket.");
        } catch( Exception e) {
            System.out.println("Error occurred: " + e);
        }
    }

}

