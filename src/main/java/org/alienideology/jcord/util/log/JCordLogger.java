package org.alienideology.jcord.util.log;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.alienideology.jcord.util.log.LogLevel.*;
import static org.alienideology.jcord.util.log.LogMode.*;

/**
 * JCordLogger - Official Logger for JCord.
 *
 * @see LogMode
 * @see LogLevel
 *
 * @author AlienIdeology
 */
public class JCordLogger implements Serializable {

    private String name;
    private LogMode mode;

    private PrintStream printStream;

    private boolean showDate;
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("yy.MM.dd");

    private boolean showTime;
    private SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss");

    //-------------------------Constructor-------------------------

    /**
     * Set the logger by an object's simple class name, with {@link LogMode#ON} mode.
     *
     * @param clazz the object.
     */
    public JCordLogger(Object clazz) {
        this(ON, clazz.getClass().getSimpleName());
    }

    /**
     * Set the logger by an object's simple name and a specified mode.
     *
     * @param mode The log mode.
     * @param clazz The object.
     */
    public JCordLogger(LogMode mode, Object clazz) {
        this(mode, clazz.getClass().getSimpleName());
    }

    /**
     * Set the logger by a string name, with {@link LogMode#ON} mode.
     *
     * @param name The name.
     */
    public JCordLogger(String name) {
        this(ON, name);
    }

    /**
     * Set the logger by a string name and a specified mode.
     *
     * @param mode The log mode.
     * @param name The name.
     */
    public JCordLogger(LogMode mode, String name) {
        this.name = name == null ? "UNKNOWN" : name;
        this.mode = mode == null ? SOME : mode;
        printStream = System.err;
        this.showDate = true;
        this.showTime = true;
    }

    //-------------------------Log Methods-------------------------

    /**
     * Log a message to the console.
     *
     * @param level The level this log is.
     * @param message The message.
     */
    public void log(LogLevel level, Object message) {
        log(level, message, null);
    }

    /**
     * Log a throwable's stack trace to the console, without providing a message.
     *
     * @param level The level this log is.
     * @param throwable The throwable, used to print stack trace.
     */
    public void log(LogLevel level, Throwable throwable) {
        log(level, "", throwable);
    }

    /**
     * Log a message to the console.
     *
     * @param level The level this log is.
     * @param message The message.
     * @param throwable The throwable, used to print stack trace.
     */
    public void log(LogLevel level, Object message, Throwable throwable) {
        /* Ignore Levels */
        if (mode == ON && !level.isOnMode())
            return;
        if (mode == SOME && !level.isSomeMode())
            return;
        if (mode == OFF)
            return;

        final StringBuffer sb = new StringBuffer();

        // Append date
        Date date = new Date();
        if (showDate) {
            sb.append("[").append(dateFormatter.format(date));
        }

        // Append time
        if (showTime) {
            sb.append(showDate ? "-" : "[").append(timeFormatter.format(date)).append("] ");
        }

        // Append level
        sb.append(level.message).append(" ");

        // Append Class Name
        sb.append(name).append(" - ");

        // Append message
        sb.append(message);

        // Append error message
        if (throwable != null) {
            sb.append("\n").append(throwable.toString());
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            throwable.printStackTrace(pw);
            pw.close();
            sb.append("\n").append(sw.toString());
        }

        // Writes to console
        printStream.println(sb.toString());
    }

    //-------------------------Getters & Setters-------------------------

    /**
     * Set the name of this logger.
     * Default name for an object is the class name, get by {@link Class#getSimpleName()}.
     *
     * @param name The name.
     * @return JCordLogger for chaining.
     */
    public JCordLogger setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Get the name of this logger.
     *
     * @return The name.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the logger mode.
     * Default mode is {@link LogMode#ON}.
     *
     * @param mode The mode.
     * @return JCordLogger for chaining.
     */
    public JCordLogger setMode(LogMode mode) {
        this.mode = mode;
        return this;
    }

    /**
     * Get the mode of this logger.
     *
     * @return The mode.
     */
    public LogMode getMode() {
        return mode;
    }

    /**
     * Check if a certain {@link LogLevel} is enabled in this logger.
     * See {@link LogMode} for what level is enabled in a certain mode.
     *
     * @param level The level to check with.
     * @return True if the level is enabled.
     */
    public boolean isLevelEnabled(LogLevel level) {
        switch (mode) {
            case ALL:
                return true;
            case ON:
                if (level == DEBUG || level == TRACE)
                    return false;
                break;
            case SOME:
                if (level == INFO || level == DEBUG || level == TRACE)
                    return false;
                break;
            case OFF:
                return false;
        }
        return true;
    }

    /**
     * Set the stream this logger will print to.
     * Default stream is {@link System#err}.
     *
     * @param printStream The stream to print to.
     * @return JCordLogger for chaining.
     */
    public JCordLogger setPrintStream(PrintStream printStream) {
        this.printStream = printStream;
        return this;
    }

    /**
     * Get the stream this logger is printing to.
     *
     * @return The stream.
     */
    public PrintStream getPrintStream() {
        return printStream;
    }

    /**
     * Set if the logger should show the logs' date.
     *
     * @param showDate True to show the date of the log.
     * @return JCordLogger for chaining.
     */
    public JCordLogger setShowDate(boolean showDate) {
        this.showDate = showDate;
        return this;
    }

    /**
     * Did the logger show date or not.
     *
     * @return
     */
    public boolean isShowDate() {
        return showDate;
    }

    /**
     * Get the date formatter.
     *
     * @return the date formatter.
     */
    public SimpleDateFormat getDateFormatter() {
        return dateFormatter;
    }

    /**
     * Set the date formatter.
     * Default date format is {@code yy.MM.dd}. For example, {@code 17.07.02}.
     *
     * @param dateFormatter The formatter.
     * @return JCordLogger for chaining.
     */
    public JCordLogger setDateFormatter(SimpleDateFormat dateFormatter) {
        this.dateFormatter = dateFormatter;
        return this;
    }

    /**
     * Did the logger show time or not.
     *
     * @return
     */
    public boolean isShowTime() {
        return showTime;
    }

    /**
     * Set if the logger should show time or not.
     *
     * @param showTime True to show the time.
     * @return JCordLogger for chaining.
     */
    public JCordLogger setShowTime(boolean showTime) {
        this.showTime = showTime;
        return this;
    }

    /**
     * Get the time formatter of this logger.
     *
     * @return The time formatter.
     */
    public SimpleDateFormat getTimeFormatter() {
        return timeFormatter;
    }

    /**
     * Set the time formatter of this logger.
     * Default time format is {@code HH:mm:ss}. For example, {@code 08.18.35}.
     *
     * @param timeFormatter The time formatter.
     * @return JCordLogger for chaining.
     */
    public JCordLogger setTimeFormatter(SimpleDateFormat timeFormatter) {
        this.timeFormatter = timeFormatter;
        return this;
    }

    /**
     * Clone the logger settings by passing a new object.
     *
     * @param clazz The new object.
     * @return A cloned logger.
     */
    public JCordLogger clone(Object clazz) {
        return clone(clazz.getClass().getSimpleName());
    }

    /**
     * Clone the logger settings by passing a new name.
     *
     * @param name The new name.
     * @return A cloned logger.
     */
    public JCordLogger clone(String name) {
        name = name == null ? this.name : name;
        return new JCordLogger(mode, name)
                .setPrintStream(printStream)
                .setShowDate(showDate)
                .setDateFormatter(dateFormatter)
                .setShowTime(showTime)
                .setTimeFormatter(timeFormatter);
    }

}
