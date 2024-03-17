package io.github.lejun0v0.betterserver.others;

import io.github.lejun0v0.betterserver.BetterServer;

public class WelcomeMessage {
    private static final String DEFAULT_MESSAGE = "欢迎你的到来！";
    private static String welcomeMessage;

    static {
        String str = BetterServer.getInstance().getConfig().getString("welcome-message");
        if (str != null) {
            setWelcomeMessage(str);
        } else {
            setWelcomeMessage(DEFAULT_MESSAGE);
        }
    }

    public static String getWelcomeMessage() {
        return welcomeMessage;
    }

    public static void setWelcomeMessage(String welcomeMessage) {
        WelcomeMessage.welcomeMessage = welcomeMessage;
    }

    public static void reloadWelcomeMessage() {
        String str = BetterServer.getInstance().getConfig().getString("welcome-message");
        if (str == null) {
            return;
        }
        setWelcomeMessage(str);
    }
}
