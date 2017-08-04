package org.alienideology.jcord.util.log;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Predicate;

import static org.alienideology.jcord.util.log.LogLevel.*;
import static org.alienideology.jcord.util.log.LogMode.*;

/**
 * Logger - Official Logger for JCord.
 *
 * This logger supports printing 2 streams, one as default, one as the error output.
 * It can also logs to files with individual filters attach to the file, making logging customisable.
 *
 * @see LogMode For different modes of this logger, used to turn on/off and filter logger output.
 * @see LogLevel For different levels of this logger.
 *
 * @author AlienIdeology
 */
public class Logger implements Serializable {

    private String name;
    private LogMode mode;

    private List<LogLevel> enabledLevels = new ArrayList<>();
    private List<LogLevel> ignoreLevels = new ArrayList<>();

    private PrintStream printStream;
    private PrintStream errorStream;

    private List<FileLog> fileLogs;

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
    public Logger(Object clazz) {
        this(ON, clazz.getClass().getSimpleName());
    }

    /**
     * Set the logger by an object's simple name and a specified mode.
     *
     * @param mode The log mode.
     * @param clazz The object.
     */
    public Logger(LogMode mode, Object clazz) {
        this(mode, clazz.getClass().getSimpleName());
    }

    /**
     * Set the logger by a string name, with {@link LogMode#ON} mode.
     *
     * @param name The name.
     */
    public Logger(String name) {
        this(ON, name);
    }

    /**
     * Set the logger by a string name and a specified mode.
     *
     * @param mode The log mode.
     * @param name The name.
     */
    public Logger(LogMode mode, String name) {
        this.name = name == null ? "UNKNOWN" : name;
        this.mode = mode == null ? ON : mode;
        this.printStream = System.out;
        this.errorStream = System.err;
        this.fileLogs =  new ArrayList<>();
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
        if (enabledLevels.contains(level)) {
            // Continue
        } else if (mode == ON && !level.isOnMode()) {
            return;
        } else if (mode == SOME && !level.isSomeMode()) {
            return;
        } else if (mode == OFF) {
            return;
        } else if (ignoreLevels.contains(level)) {
            return;
        }

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

        // Writes to default print streams
        if (level.isError() || throwable != null) {
            errorStream.println(sb.toString());
        } else {
            printStream.println(sb.toString());
        }

        // Writes to files
        Writer writer;
        for (FileLog file : fileLogs) {
            if (file.filter.test(level)) {
                try {
                    writer = new BufferedWriter(new FileWriter(file.file));
                    writer.write(sb.toString());
                    writer.close();
                } catch (IOException e) {
                    errorStream.println("Error while writing to file: " + file);
                    e.printStackTrace();
                }
            }
        }
    }

    //-------------------------Getters & Setters-------------------------

    /**
     * Set the name of this logger.
     * Default name for an object is the class name, get by {@link Class#getSimpleName()}.
     *
     * @param name The name.
     * @return Logger for chaining.
     */
    public Logger setName(String name) {
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
     * @return Logger for chaining.
     */
    public Logger setMode(LogMode mode) {
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
        if (enabledLevels.contains(level)) return true;
        else if (ignoreLevels.contains(level)) return false;
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
     * Get all levels that are specified to enable logging.
     * This does not contains levels that are enabled by the {@link LogMode}.
     * All enabled levels must be specified by invoking {@link #setEnabledLevels(LogLevel...)}.
     *
     * @return Get enabled levels.
     */
    public List<LogLevel> getEnabledLevels() {
        return enabledLevels;
    }

    /**
     * Set specific log levels to enable logging, independent from the {@link #setMode(LogMode)}.
     *
     * @param enabledLevels The levels to enable logging.
     * @return Logger for chaining.
     */
    public Logger setEnabledLevels(LogLevel... enabledLevels) {
        this.enabledLevels.clear();
        this.enabledLevels.addAll(Arrays.asList(enabledLevels));
        return this;
    }

    /**
     * Get all levels that are specified to disable logging.
     * If the {@link LogLevel} is also enabled, then it will not be ignored.
     *
     * This does not contains levels that are ignored by the {@link LogMode}.
     * All ignored levels must be specified by invoking {@link #setIgnoreLevels(LogLevel...)}.
     *
     * @return Get ignored levels.
     */
    public List<LogLevel> getIgnoreLevels() {
        return ignoreLevels;
    }

    /**
     * Set specific log levels to ignore logging, independent from the {@link #setMode(LogMode)}.
     *
     * @param ignoreLevels The levels to disable, or ignore logging.
     * @return Logger for chaining.
     */
    public Logger setIgnoreLevels(LogLevel... ignoreLevels) {
        this.ignoreLevels.clear();
        this.ignoreLevels.addAll(Arrays.asList(ignoreLevels));
        return this;
    }

    /**
     * Disable the logger's print stream.
     *
     * @return Logger for chaining.
     */
    public Logger disablePrintStream() {
        this.printStream = null;
        return this;
    }

    /**
     * Set the stream this logger will print to.
     * Default stream is {@link System#out}.
     *
     * @param printStream The stream to print to.
     * @return Logger for chaining.
     */
    public Logger setPrintStream(PrintStream printStream) {
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
     * Disable the logger's error stream.
     *
     * @return Logger for chaining.
     */
    public Logger disableErrorStream() {
        this.errorStream = null;
        return this;
    }

    /**
     * Set the error stream this logger will print errors to.
     *
     * @param errorStream The stream.
     * @return Logger for chaining.
     */
    public Logger setErrorStream(PrintStream errorStream) {
        this.errorStream = errorStream;
        return this;
    }

    /**
     * Get the error stream this logger will print errors to.
     *
     * @return The stream.
     */
    public PrintStream getErrorStream() {
        return errorStream;
    }

    /**
     * Set if the logger should show the logs' date.
     *
     * @param showDate True to show the date of the log.
     * @return Logger for chaining.
     */
    public Logger setShowDate(boolean showDate) {
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
     * @return Logger for chaining.
     */
    public Logger setDateFormatter(SimpleDateFormat dateFormatter) {
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
     * @return Logger for chaining.
     */
    public Logger setShowTime(boolean showTime) {
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
     * @return Logger for chaining.
     */
    public Logger setTimeFormatter(SimpleDateFormat timeFormatter) {
        this.timeFormatter = timeFormatter;
        return this;
    }

    /**
     * Get the file logs added to the logger, waiting to be write to.
     *
     * @return A list of file logs.
     */
    public List<FileLog> getFileLogs() {
        return fileLogs;
    }

    /**
     * Add a file to the logger output.
     * You can filter the logs by using the following predicate,
     * or pass {@code null} to the predicate to get all logs (that are granted by the {@link LogMode}).
     *
     * @exception IllegalArgumentException
     *          <ul>
     *              <li>If the file does not exist or is read-only.</li>
     *              <li>If the file is already added.</li>
     *          </ul>
     * @param filter The log filter.
     * @param file The file to output to.
     * @return Logger for chaining.
     */
    public Logger addFileLog(Predicate<LogLevel> filter, File file) {
        if (!file.exists() || !file.canWrite()) {
            throw new IllegalArgumentException("Cannot write to the file!");
        }

        if (fileLogs.stream().anyMatch(fl -> fl.file.equals(file))) {
            throw new IllegalArgumentException("Cannot register an existed file log!");
        }

        fileLogs.add(new FileLog(filter == null ? level -> true : filter, file));
        return this;
    }

    /**
     * Remove a file from the logger.
     * If the file was not added, then this method will do nothing and return.
     *
     * @param file The file to remove.
     * @return Logger for chaining.
     */
    public Logger removeFileLog(File file) {
        Optional<FileLog> first = fileLogs.stream().filter(fl -> fl.file.equals(file)).findFirst();
        first.ifPresent(fileLog -> fileLogs.remove(fileLog));
        return this;
    }

    /**
     * Clone the logger settings by passing a new object.
     *
     * @param clazz The new object.
     * @return A cloned logger.
     */
    public Logger clone(Object clazz) {
        return clone(clazz.getClass().getSimpleName());
    }

    /**
     * Clone the logger settings by passing a new name.
     *
     * @param name The new name.
     * @return A cloned logger.
     */
    public Logger clone(String name) {
        name = name == null ? this.name : name;
        return new Logger(mode, name)
                .setEnabledLevels(enabledLevels.toArray(new LogLevel[enabledLevels.size()]))
                .setIgnoreLevels(ignoreLevels.toArray(new LogLevel[ignoreLevels.size()]))
                .setPrintStream(printStream)
                .setShowDate(showDate)
                .setDateFormatter(dateFormatter)
                .setShowTime(showTime)
                .setTimeFormatter(timeFormatter);
    }

    /**
     * A file in the logger with a filter attach to it.
     */
    public static class FileLog {

        Predicate<LogLevel> filter;
        File file;

        FileLog(Predicate<LogLevel> filter, File file) {
            this.filter = filter;
            this.file = file;
        }

        public Predicate<LogLevel> getFilter() {
            return filter;
        }

        public File getFile() {
            return file;
        }

    }

}
