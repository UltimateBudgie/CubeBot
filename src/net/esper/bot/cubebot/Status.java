package net.esper.bot.cubebot;

import java.sql.Time;

/**
 *
 * @author Mark Smullen <marktexnical@gmail.com>
 */
public class Status {

    private static boolean picromaUp = false;
    private static boolean shopUp = false;
    private static boolean registrationUp = false;
    private static Time unixTime = null;

    public static boolean isPicromaUp() {
        return picromaUp;
    }

    public static boolean isShopUp() {
        return shopUp;
    }

    public static boolean isRegistrationUp() {
        return registrationUp;
    }

    public static Time getUnixTime() {
        return unixTime;
    }

    public static void setPicromaUp(boolean picromaUp) {
        Status.picromaUp = picromaUp;
    }

    public static void setShopUp(boolean shopUp) {
        Status.shopUp = shopUp;
    }

    public static void setRegistrationUp(boolean registrationUp) {
        Status.registrationUp = registrationUp;
    }

    public static boolean expired() {
        if ((System.currentTimeMillis() / 1000L) > Status.getUnixTime().getTime() + 70) {
            return true;
        }
        return false;
    }

    protected static void setUnixTime(Long timeStamp) {
        Status.unixTime.setTime(timeStamp);
    }
}
