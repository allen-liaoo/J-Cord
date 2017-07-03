package org.alienideology.jcord.util.log;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.alienideology.jcord.util.log.LogMode.*;

/**
 * JCordLogger - Official Logger for JCord.
 *
 * @author AlienIdeology
 */
public class JCordLogger {

    private String className;
    private LogMode mode;

    private PrintStream printStream;

    private boolean showDate;

    private boolean showTime;

    //-------------------------Constructor-------------------------

    public JCordLogger(Object clazz) {
        this(ON, clazz.getClass().getSimpleName());
    }

    public JCordLogger(LogMode mode, Object clazz) {
        this(mode, clazz.getClass().getSimpleName());
    }

    public JCordLogger(String className) {
        this(ON, className);
    }

    public JCordLogger(LogMode mode, String className) {
        this.className = className == null ? "UNKNOWN" : className;
        this.mode = mode == null ? SOME : mode;
        printStream = System.err;
        this.showDate = true;
        this.showTime = true;
    }

    //-------------------------Log Methods-------------------------

    public void log(LogLevel level, Object message) {
        log(level, message, null);
    }

    public void log(LogLevel level, Throwable throwable) {
        log(level, "", throwable);
    }

    public void log(LogLevel level, Object message, Throwable throwable) {
        /* Ignore Levels */
        if (mode == SOME && !level.isSomeMode())
            return;
        if (mode == ON && !level.isOnMode())
            return;
        if (mode == OFF)
            return;

        final StringBuffer sb = new StringBuffer();

        // Append date
        Date date = new Date();
        if (showDate) {
            sb.append("[").append(date.getMonth()).append("/").append(date.getDate());
        }

        // Append time
        if (showTime) {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            sb.append(showDate ? "-" : "[").append(sdf.format(date)).append("] ");
        }

        // Append level
        sb.append(level.message).append(" ");

        // Append Class Name
        sb.append(className).append(" - ");

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

    public String getClassName() {
        return className;
    }

    public JCordLogger setClassName(String className) {
        this.className = className;
        return this;
    }

    public JCordLogger setMode(LogMode mode) {
        this.mode = mode;
        return this;
    }

    public LogMode getMode() {
        return mode;
    }

    public JCordLogger setPrintStream(PrintStream printStream) {
        this.printStream = printStream;
        return this;
    }

    public PrintStream getPrintStream() {
        return printStream;
    }

    public JCordLogger setShowDate(boolean showDate) {
        this.showDate = showDate;
        return this;
    }

    public boolean isShowDate() {
        return showDate;
    }

    public boolean isShowTime() {
        return showTime;
    }

    public void setShowTime(boolean showTime) {
        this.showTime = showTime;
    }

}
