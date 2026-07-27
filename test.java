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
public static void main(String[] args) {
    boolean assertsOn = false;
    assert assertsOn = true;
    if (!assertsOn) {
        System.out.println("WARNING: assertions disabled"
        + " - re-run with: java -ea test\n");
    }
    System.out.println("=== NUMBER Test Suite ===\n");


        System.out.println("\n=== Summary ===");
        System.out.println("Passed: " + passed);
        System.out.println("Failed: " + failed);
        System.out.println("Total : " + (passed + failed));
        System.out.println(failed == 0 ? "ALL TESTS PASSED" : "SOME TESTS FAILED");

        if (failed > 0) {
            System.exit(1);
        }
    }
}
