package org.alienideology.jcord.util;

import org.alienideology.jcord.JCord;
import org.alienideology.jcord.util.log.LogLevel;
import org.apache.commons.codec.binary.StringUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Base64;

/**
 * DataUtils - Utilities for I/O and data.
 *
 * @author AlienIdeology
 */
public class DataUtils {

    public static byte[] getBytesFromImage(String fileFormat, BufferedImage image) throws IOException {
        ByteArrayOutputStream byteoutput = new ByteArrayOutputStream();
        ImageIO.write(image, fileFormat, byteoutput);
        return byteoutput.toByteArray();
    }

    public static String encodeIcon(String fileFormat, BufferedImage image) throws IOException {
        return "data:image/" + fileFormat + ";base64, " + byteToString(getBytesFromImage(fileFormat, image));
    }

    public static String byteToString(byte[] bytes) {
        return StringUtils.newStringUtf8(Base64.getEncoder().encode(bytes));
    }

    public static String encodeToUrl(String toEncode) {
        try {
            return URLEncoder.encode(toEncode, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            JCord.LOG.log(LogLevel.FETAL, e);
            return toEncode;
        }
    }

}
