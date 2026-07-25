import java.util.*;

public class test {
    private static int passed = 0;
    private static int failed = 0;

    /** helper กลาง — พิมพ์ PASS/FAIL และนับผลให้เอง */
    private static void check(int score, boolean condition) {
        if (condition) {
            passed++;
            System.out.println("[PASS] " + score);
        } else {
            failed++;
            System.out.println("[FAIL] " + score);
        }
}
}
