package org.alienideology.jcord.util;

import org.alienideology.jcord.JCord;
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

    public static String encodeIcon(BufferedImage image) throws IOException {
        ByteArrayOutputStream byteoutput = new ByteArrayOutputStream();
        ImageIO.write(image, "jpeg", byteoutput);
        byte[] imageInByte = byteoutput.toByteArray();
        return "data:image/jpeg;base64, " + StringUtils.newStringUtf8(Base64.getEncoder().encode(imageInByte));
    }

    public static String encodeToUrl(String toEncode) {
        try {
            return URLEncoder.encode(toEncode, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            JCord.LOGGER.fatal("UnsupportedEncodingException when encoding to url!");
        }
        return null;
    }

}
