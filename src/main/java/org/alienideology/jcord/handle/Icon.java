package org.alienideology.jcord.handle;

import org.alienideology.jcord.JCord;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.alienideology.jcord.util.DataUtils;
import org.apache.commons.io.IOUtils;

/**
 * Icon - An encoded(base64) image, can be an avatar or picture.
 *
 * @author AlienIdeology
 */
public final class Icon {

    private String data;
    private byte[] bytes;

    Icon(String data, byte[] bytes) {
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

    //------------------------------Statics------------------------------

    /**
     * Get the default icon (null).
     *
     * @return The default icon.
     */
    public static Icon defaultIcon() {
        return new Icon(null, null);
    }

    /**
     * Get the icon instance from the icon file path.
     * The default {@code file format} for this method is {@code jpeg}.
     *
     * @param path The file path.
     * @return A new icon.
     * @throws IOException When decoding the icon.
     */
    public static Icon fromPath(String path) throws IOException {
        return fromPath("jpeg", path);
    }

    /**
     * Get the icon instance from the icon file path.
     *
     * @param fileFormat The image file format, jpg, jpeg, png, etc.
     * @param path The file path.
     * @return A new icon.
     * @throws IOException When decoding the icon.
     */
    public static Icon fromPath(String fileFormat, String path) throws IOException {
        return fromImage(ImageIO.read(new File(path)));
    }

    /**
     * Get the icon instance from a buffered image.
     * The default {@code file format} for this method is {@code jpeg}.
     *
     * @param image The image.
     * @return A new icon.
     * @throws IOException When decoding the icon.
     */
    public static Icon fromImage(BufferedImage image) throws IOException {
        return fromImage("jpeg", image);
    }

    /**
     * Get the icon instance from a buffered image.
     *
     * @param fileFormat The image file format, jpg, jpeg, png, etc.
     * @param image The image.
     * @return A new icon.
     * @throws IOException When decoding the icon.
     */
    public static Icon fromImage(String fileFormat, BufferedImage image) throws IOException {
        return new Icon(DataUtils.encodeIcon(fileFormat, image), DataUtils.getBytesFromImage(fileFormat, image));
    }

    /**
     * Get the icon instance from an url.
     * The default {@code file format} for this method is {@code jpeg}.
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
     * The default {@code file format} for this method is {@code jpeg}.
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
     * Get the icon instance from a byte array.
     * The default {@code file format} for this method is {@code jpeg}.
     *
     * @param bytes The byte array.
     * @return A new icon.
     * @throws IOException When decoding the icon.
     */
    public static Icon fromBytes(byte[] bytes) throws IOException {
        return new Icon("data:image/jpeg;base64, " + DataUtils.byteToString(bytes), bytes);
    }

}
