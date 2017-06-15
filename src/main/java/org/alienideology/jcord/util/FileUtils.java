package org.alienideology.jcord.util;

import org.apache.commons.codec.binary.StringUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

/**
 * @author AlienIdeology
 */
public class FileUtils {

    public static String encodeIcon(BufferedImage image) throws IOException {
        ByteArrayOutputStream byteoutput = new ByteArrayOutputStream();
        ImageIO.write(image, "jpeg", byteoutput);
        byte[] imageInByte = byteoutput.toByteArray();
        return "data:image/jpeg;base64, " + StringUtils.newStringUtf8(Base64.getEncoder().encode(imageInByte));
    }

}
