package org.alienideology.jcord.util;

import org.alienideology.jcord.JCord;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.io.IOUtils;

/**
 * Icon - An avatar or picture.
 *
 * @author AlienIdeology
 */
public class Icon {

    private String data;
    private byte[] bytes;

    public Icon(String data, byte[] bytes) {
        this.data = data;
        this.bytes = bytes;
    }

    /**
     * Get the data discord required when posting http requests.
     *
     * @return The string data.
     */
    public String getData() {
        return data;
    }

    /**
     * Get the raw data of this image.
     *
     * @return The raw bytes array.
     */
    public byte[] getBytes() {
        return bytes;
    }

    /**
     * Get the icon instance from the icon file path.
     *
     * @param path The file path.
     * @return A new icon.
     * @throws IOException When decoding the icon.
     */
    public static Icon fromPath(String path) throws IOException {
        return fromImage(ImageIO.read(new File(path)));
    }

    /**
     * Get the icon instance from an url.
     *
     * @param url The image url.
     * @return A new icon.
     * @throws IOException When decoding the icon.
     */
    public static Icon fromUrl(String url) throws IOException {
        URLConnection urlConnection = new URL(url)
                .openConnection();
        urlConnection.setRequestProperty("User-Agent", JCord.USER_AGENT);
        InputStream stream = urlConnection.getInputStream();
        return fromStream(stream);
    }

    /**
     * Get the icon instance from an input stream.
     *
     * @param stream The image stream.
     * @return A new icon.
     * @throws IOException When decoding the icon.
     */
    public static Icon fromStream(InputStream stream) throws IOException {
        Icon icon = fromBytes(IOUtils.toByteArray(stream));
        stream.close();
        return icon;
    }

    /**
     * Get the icon instance from a buffered image.
     *
     * @param image The image.
     * @return A new icon.
     * @throws IOException When decoding the icon.
     */
    public static Icon fromImage(BufferedImage image) throws IOException {
        return new Icon(DataUtils.encodeIcon(image), DataUtils.getBytesFromImage(image));
    }

    /**
     * Get the icon instance from a byte array.
     *
     * @param bytes The byte array.
     * @return A new icon.
     * @throws IOException When decoding the icon.
     */
    public static Icon fromBytes(byte[] bytes) throws IOException {
        return new Icon("data:image/jpeg;base64, " + DataUtils.byteToString(bytes), bytes);
    }

}
