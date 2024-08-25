package io.github.lejun0v0.betterserver.portal;

import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;

public class LineManager {
    private static final ArrayList<LineTest> lineTests = new ArrayList<>();

    private LineManager() {

    }

    public static boolean addLine(LineTest lineTest) {
        return lineTests.add(lineTest);
    }

    public static boolean removeLine(LineTest lineTest) {
        return lineTests.remove(lineTest);
    }

    public static LineTest getLineTest(String code) {
        ArrayList<LineTest> lineTests = LineManager.lineTests;
        for (LineTest lineTest : lineTests) {
            if (code.equals(lineTest.getCode())) {
                return lineTest;
            }
        }
        return null;
    }

    public static LineTest createLineTest(Location Location, int count, World world, String code) {
        return new LineTest(Location, count, world, code);
    }

    public static ArrayList<LineTest> getLines() {
        return lineTests;
    }
}
