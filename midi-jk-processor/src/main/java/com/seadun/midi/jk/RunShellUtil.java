package com.seadun.midi.jk;

import lombok.extern.log4j.Log4j2;

import java.io.InputStreamReader;
import java.io.LineNumberReader;

/**
 * 调用shell
 */
@Log4j2
public class RunShellUtil {
    /**
     * 执行shell命令 String[] cmd = { "sh", "-c", "lsmod |grep linuxVmux" }或者
     * String[] cmd = { "sh", "-c", "./load_driver.sh" } 
     */
    public static String runScript(String[] cmd) {
        StringBuffer buf = new StringBuffer();
        String rt = "-1";
        try {
            Process pos = Runtime.getRuntime().exec(cmd);
            pos.waitFor();
            InputStreamReader ir = new InputStreamReader(pos.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            String ln = "";
            while ((ln = input.readLine()) != null) {
                buf.append(ln + "\n");
            }
            rt = buf.toString();
            input.close();
            ir.close();
        } catch (java.io.IOException e) {
            rt = e.toString();
        } catch (Exception e) {
            rt = e.toString();
        }
        return rt;
    }

    /**
     * 执行简单命令 String cmd="ls"
     */
    public static String runScript(String cmd) throws MidiException {
        StringBuffer buf = new StringBuffer();
        String rt = "-1";
        try {
            Process pos = Runtime.getRuntime().exec(cmd);
            pos.waitFor();
            InputStreamReader ir = new InputStreamReader(pos.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            String ln = "";
            while ((ln = input.readLine()) != null) {
                buf.append(ln + "\n");
            }
            rt = buf.toString();
            input.close();
            ir.close();
        } catch (Exception e) {
            rt = e.toString();
            log.error("runScript异常------------->"+e);
            throw new MidiException(400, rt);
        }
        return rt;
    }
}