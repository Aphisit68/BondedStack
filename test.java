
import java.util.Arrays;
import java.util.List;

/**
 * Test runner
 */
public class test {

    private static int passed = 0;
    private static int failed = 0;

    /** helper กลาง — พิมพ์ PASS/FAIL และนับผลให้เอง */
    private static void check(String name, boolean condition) {
        if (condition) {
            passed++;
            System.out.println("[PASS] " + name);
        } else {
            failed++;
            System.out.println("[FAIL] " + name);
        }
    }

    public static void main(String[] args) {
        boolean assertsOn = false;
        assert assertsOn = true;
        if (!assertsOn) {
            System.out.println("WARNING: assertions disabled"
                    + " - re-run with: java -ea test\n");
        }

        System.out.println("=== BoundedStack Test Suite ===\n");

        testCreators();
        testAdd();
        testRemove();
        testObservers();
        testProducer();
        testExposure();

        System.out.println("\n=== Summary ===");
        System.out.println("Passed: " + passed);
        System.out.println("Failed: " + failed);
        System.out.println("Total : " + (passed + failed));
        System.out.println(failed == 0 ? "ALL TESTS PASSED" : "SOME TESTS FAILED");

        if (failed > 0) {
            System.exit(1);
        }
    }

    // --- Partition: constructor เปล่า / constructor(initial) ที่ถูก-ผิดเงื่อนไข ---
    private static void testCreators() {
        System.out.println("-- Creators --");

        BoundedStack empty = new BoundedStack();
        check("new() -> empty", empty.getAll().isEmpty());

        BoundedStack reserved = new BoundedStack(5);
        check("new(5) -> still empty (initial แค่จอง capacity ไม่ใส่คะแนน)",
                reserved.getAll().isEmpty());

        // boundary: initial = 0 คือขอบล่างที่ถูกต้อง
        BoundedStack fromZero = new BoundedStack(0);
        check("new(0) -> empty", fromZero.getAll().isEmpty());

        // boundary: initial = max_number คือขอบบนที่ถูกต้อง (ต้องไม่ throw)
        boolean threwAtUpperBound = false;
        try {
            new BoundedStack(BoundedStack.max_number);
        } catch (IllegalArgumentException e) {
            threwAtUpperBound = true;
        }
        check("new(max_number) -> does not throw", !threwAtUpperBound);

        // input ที่ผิดเงื่อนไขต้องโยน exception ไม่ใช่ปล่อยผ่าน
        boolean threwNegative = false;
        try {
            new BoundedStack(-1);
        } catch (IllegalArgumentException e) {
            threwNegative = true;
        }
        check("new(-1) -> throws IllegalArgumentException", threwNegative);

        boolean threwOverMax = false;
        try {
            new BoundedStack(BoundedStack.max_number + 1);
        } catch (IllegalArgumentException e) {
            threwOverMax = true;
        }
        check("new(max_number + 1) -> throws IllegalArgumentException", threwOverMax);
    }

    // --- Mutator: add ต้องรักษาลำดับและกันคะแนนที่ผิดเงื่อนไข ---
    private static void testAdd() {
        System.out.println("\n-- Add --");

        BoundedStack s = new BoundedStack();
        check("add(20) -> returns true", s.add("20"));
        check("add(20) -> size 1", s.getAll().size() == 1);
        check("add(20) -> found by contains", s.contains("20"));

        s.add("9");
        s.add("15");
        check("add preserves insertion order",
                s.getAll().equals(Arrays.asList("20", "9", "15")));

        // คะแนนซ้ำได้ — list ไม่ใช่ set
        check("add duplicate score -> still returns true", s.add("9"));
        check("duplicate scores both counted", s.getAll().size() == 4);

        // boundary: 0 กับ 20 คือขอบล่าง-บนของคะแนนที่ถูกต้อง
        BoundedStack boundary = new BoundedStack();
        check("add(0) -> lower bound accepted", boundary.add("0"));
        check("add(20) -> upper bound accepted", boundary.add("20"));

        // input ที่ผิดเงื่อนไขต้องโยน exception
        boolean threwNull = false;
        try {
            s.add(null);
        } catch (IllegalArgumentException e) {
            threwNull = true;
        }
        check("add(null) -> throws IllegalArgumentException", threwNull);

        boolean threwEmpty = false;
        try {
            s.add("");
        } catch (IllegalArgumentException e) {
            threwEmpty = true;
        }
        check("add(empty string) -> throws IllegalArgumentException", threwEmpty);

        boolean threwNonDigit = false;
        try {
            s.add("abc");
        } catch (IllegalArgumentException e) {
            threwNonDigit = true;
        }
        check("add(\"abc\") -> throws IllegalArgumentException", threwNonDigit);

        boolean threwNegative = false;
        try {
            s.add("-5");
        } catch (IllegalArgumentException e) {
            threwNegative = true;
        }
        check("add(\"-5\") -> throws IllegalArgumentException", threwNegative);

        boolean threwOver20 = false;
        try {
            s.add("21");
        } catch (IllegalArgumentException e) {
            threwOver20 = true;
        }
        check("add(\"21\") -> throws IllegalArgumentException", threwOver20);

        check("failed adds leave playlist unchanged", s.getAll().size() == 4);

        // boundary: เติมจนเต็มพอดีแล้วเติมเพิ่ม
        BoundedStack full = new BoundedStack();
        for (int i = 0; i < BoundedStack.max_number; i++) {
            full.add("10");
        }
        check("can fill up to max_number", full.getAll().size() == BoundedStack.max_number);
        check("add when full -> returns false", !full.add("10"));
        check("full stack stays at max_number", full.getAll().size() == BoundedStack.max_number);
    }

    // --- Mutator: remove ทั้งกรณีพบและไม่พบตำแหน่ง ---
    private static void testRemove() {
        System.out.println("\n-- Remove --");

        BoundedStack s = new BoundedStack();
        s.add("20");
        s.add("9");
        s.add("15");

        check("remove(1) -> returns true", s.remove(1));
        check("remove -> size decreases", s.getAll().size() == 2);
        check("remove keeps the others in order",
                s.getAll().equals(Arrays.asList("20", "15")));

        // boundary: index นอกขอบเขตทั้งสองฝั่งไม่ใช่ error — คืน false เฉย ๆ
        check("remove(index เกินขอบบน) -> returns false", !s.remove(99));
        check("remove(index ติดลบ) -> returns false", !s.remove(-1));
        check("failed remove leaves size unchanged", s.getAll().size() == 2);

        // boundary: ลบจนหมด
        s.remove(0);
        s.remove(0);
        check("remove all -> empty", s.getAll().isEmpty());
        check("remove on empty stack -> returns false", !s.remove(0));
    }

    // --- Observer ต้องไม่มี side effect ---
    private static void testObservers() {
        System.out.println("\n-- Observers --");

        BoundedStack s = new BoundedStack();
        s.add("20");
        s.add("9");

        check("getAll().size() reports 2", s.getAll().size() == 2);
        check("get(0) returns first score", s.get(0).equals("20"));
        check("get(1) returns second score", s.get(1).equals("9"));
        check("contains finds an existing score", s.contains("20"));
        check("contains rejects a missing score", !s.contains("99"));
        check("getAll returns the full list in order",
                s.getAll().equals(Arrays.asList("20", "9")));

        boolean threwOutOfBounds = false;
        try {
            s.get(99);
        } catch (IndexOutOfBoundsException e) {
            threwOutOfBounds = true;
        }
        check("get(index นอกขอบเขต) -> throws IndexOutOfBoundsException", threwOutOfBounds);

        int before = s.getAll().size();
        s.getAll();
        s.contains("20");
        s.get(0);
        check("observers have no side effects", s.getAll().size() == before);
    }

    // --- Producer ต้องคืน object ใหม่ ไม่แก้ตัวเดิม ---
    private static void testProducer() {
        System.out.println("\n-- Producer (sortedDescending) --");

        BoundedStack original = new BoundedStack();
        original.add("9");
        original.add("20");
        original.add("15");

        BoundedStack sorted = original.sortedDescending();
        check("sortedDescending has the same size", sorted.getAll().size() == original.getAll().size());
        check("sortedDescending is ordered from มากไปน้อย",
                sorted.getAll().equals(Arrays.asList("20", "15", "9")));

        check("sortedDescending does not mutate the original",
                original.getAll().equals(Arrays.asList("9", "20", "15")));

        // mutate ตัวใหม่ต้องไม่กระทบตัวเดิม
        sorted.add("0");
        check("mutating the result does not affect the original",
                original.getAll().size() == 3);

        // boundary: sort stack ว่างต้องไม่พัง
        BoundedStack emptySorted = new BoundedStack().sortedDescending();
        check("sorting an empty stack is safe", emptySorted.getAll().isEmpty());
    }

    // --- ทดสอบว่าไม่เกิด representation exposure ---
    private static void testExposure() {
        System.out.println("\n-- Representation Exposure --");

        // ขาออก: แก้ list ที่ได้จาก getAll() ต้องไม่กระทบ rep
        BoundedStack s = new BoundedStack();
        s.add("20");

        List<String> got = s.getAll();
        got.clear();
        check("clearing result of getAll() does not affect stack",
                s.getAll().size() == 1);

        got = s.getAll();
        got.add("99");
        check("adding to result of getAll() does not affect stack",
                s.getAll().size() == 1 && !s.contains("99"));

        // สองครั้งต้องเป็นคนละ object
        check("getAll() returns a fresh list each call", s.getAll() != s.getAll());
    }
}