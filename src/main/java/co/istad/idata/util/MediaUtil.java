package co.istad.idata.util;

public class MediaUtil {

    public static String getExtension(String mediaName){

        int lastDotIndex = mediaName.lastIndexOf(".");

        return mediaName.substring(lastDotIndex + 1);

    }


}
