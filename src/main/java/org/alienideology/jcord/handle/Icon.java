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
import org.json.JSONObject;

/**
 * Icon - An encoded(base64) image, can be an avatar or picture.
 *
 * @author AlienIdeology
 */
public final class Icon {

    private Object data;
    private byte[] bytes;

    Icon(Object data, byte[] bytes) {
        this.data = data;
        this.bytes = bytes;
    }

    /**
     * Get the data discord required when posting http requests.
     * Note that this is not guaranteed to be a string, But only {@link #DEFAULT_ICON} returns a {@link JSONObject#NULL} object.
     *
     * @return The data, may be a string or {@link JSONObject#NULL}.
     */
    public Object getData() {
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

    //--------------------------Statics Variables------------------------------

    /**
     * Discord supported {@code Jpg} image format.
     */
    public final static String FORMAT_JPG = "jpg";

    /**
     * Discord supported {@code Jpeg} image format.
     */
    public final static String FORMAT_JPEG = "jpeg";

    /**
     * Discord supported {@code Jpeg} image format.
     */
    public final static String FORMAT_PNG = "png";

    /**
     * Discord supported {@code Gif} image format.
     */
    public final static String FORMAT_GIF = "gif";

    /**
     * The default icon (null).
     */
    public final static Icon DEFAULT_ICON = new Icon(JSONObject.NULL, new byte[0]);

    //--------------------------Statics Getters------------------------------

    /**
     * Get the icon instance from the icon file path.
     *
     * @param fileFormat The image file format, jpg, jpeg, png, etc.
     * @param path The file path.
     * @return A new icon.
     * @throws IOException When decoding the icon.
     */
    public static Icon fromPath(String fileFormat, String path) throws IOException {
        return fromFile(fileFormat, new File(path));
    }

    /**
     * Get the icon instance from a file.
     *
     * @param fileFormat The image file format, jpg, jpeg, png, etc.
     * @param file The image file.
     * @return A new icon.
     * @throws IOException When decoding the icon.
     */
    public static Icon fromFile(String fileFormat, File file) throws IOException {
        return fromImage(fileFormat, ImageIO.read(file));
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
     *
     * @param fileFormat The image file format, jpg, jpeg, png, etc.
     * @param url The image url.
     * @return A new icon.
     * @throws IOException When decoding the icon.
     */
    public static Icon fromUrl(String fileFormat, String url) throws IOException {
        URLConnection urlConnection = new URL(url)
                .openConnection();
        urlConnection.setRequestProperty("User-Agent", JCord.USER_AGENT);
        InputStream stream = urlConnection.getInputStream();
        return fromStream(fileFormat, stream);
    }

    /**
     * Get the icon instance from an input stream.
     *
     * @param fileFormat The image file format, jpg, jpeg, png, etc.
     * @param stream The image stream.
     * @return A new icon.
     * @throws IOException When decoding the icon.
     */
    public static Icon fromStream(String fileFormat, InputStream stream) throws IOException {
        Icon icon = fromBytes(fileFormat, IOUtils.toByteArray(stream));
        stream.close();
        return icon;
    }

    /**
     * Get the icon instance from a byte array.
     *
     * @param fileFormat The image file format, jpg, jpeg, png, etc.
     * @param bytes The byte array.
     * @return A new icon.
     * @throws IOException When decoding the icon.
     */
    public static Icon fromBytes(String fileFormat, byte[] bytes) throws IOException {
        return new Icon("data:image/" + fileFormat + ";base64, " + DataUtils.byteToString(bytes), bytes);
    }

}
