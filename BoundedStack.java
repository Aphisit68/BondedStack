import java.util.ArrayList;
import java.util.List;
/**
 * BoundedStack - ADT แทนคะแนนนักเรียนที่อาจารย์จัดลำดับไว้
 * form พลอยกาญจน์ เหลืองอมรศักดิ์ และ อภิสิทธิ์ สิงห์เปีย
 * คำนามธรรม (A) : ลำดับของคะแนนนักเรียน เช่น [18, 9, 15]
 */

public class BoundedStack {
     private final List<String> number;
     public static final int max_number = 50;

    //===== representation =====
    // Abstraction Function(AF):
    //   AF(number) = ลำดับของคะแนนนักเรียนแต่ละคน

    // Representation Invariant(RI):
    // นักเรียนไม่เป็น null
    // ไม่มีคะแนนเป็น null
    // ไม่มีคะแนนที่ว่าง
    // ไม่มีคะแนนติดลบ
    // คะแนนมีได้แค่ตัวเลข
    // คะแนนไม่เกิน 20 คะแนน
    // มีได้ไม่เกิน max_number (50) คน

     private  void  checkRep(){
          assert  number != null : "number ต้องไม่เป็น null";
          assert number.size() <= max_number : "จำนวนคะแนนต้องไม่เกิน max_number";
          for (String s : number) {
               assert s != null : "คะแนนต้องไม่เป็น null";
               assert !s.isEmpty() : "คะแนนต้องไม่เป็นสตริงว่าง";
               assert s.matches("\\d+") : "คะแนนต้องประกอบด้วยตัวเลขเท่านั้น: " + s;
               int val = Integer.parseInt(s);
               assert val >= 0 : "คะแนนต้องไม่ติดลบ: " + s;
               assert val <= 20 : "คะแนนต้องไม่เกิน 20: " + s;
          }
}

 // ===== Creator =====

/**
     * สร้างคะแนนนักเรียน
     */
public BoundedStack(){
     this.number = new ArrayList<>();
     checkRep();
    } 

/**
 * สร้างจำนวนของคะแนนนักเรียน
 * @param initial จำนวนนักเรียน ต้องไม่เกิน max_number
 * @throws IllegalArgumentException ถ้า initial ผิดเงื่อนไข
 */
public BoundedStack(int initial) {
     if (initial < 0 || initial > max_number) {
     throw new IllegalArgumentException("initial ต้องอยู่ระหว่าง 0 ถึง " + max_number + " แต่ได้ " + initial);
     }
     this.number = new ArrayList<>(initial);
     checkRep();
}

 // ===== Mutators =====

/**
 * เพิ่มคะแนนต่อท้ายนักเรียน
 * @param score คะแนน ต้องไม่เป็น null, ไม่มีคะแนนว่าง, คะแนนต้องเป็นตัวเลข, คะแนนไม่เกิน 20 คะแนน
 * @return true ถ้าเพิ่มสำเร็จ, false ถ้ามีจำนวนนักเรียนเต็มแล้ว
 * @throws IllegalArgumentException ถ้า score เป็น null หรือสตริงว่าง
 */
public boolean add(String score) {
     if (score == null || score.isEmpty()) {throw new IllegalArgumentException("score ต้องไม่เป็น null หรือสตริงว่าง");
     }
     if (!score.matches("\\d+")) {throw new IllegalArgumentException("score ต้องประกอบด้วยตัวเลขเท่านั้น: " + score);
     }
     if (Integer.parseInt(score) > 20) {throw new IllegalArgumentException("score ต้องไม่เกิน 20: " + score);
     }
     if (number.size() >= max_number) {return false;
     }
     number.add(score);
     checkRep();
     return true;
}

/**
 * ลบคะแนนออกจากลำดับนักเรียน
 * @param index ตำแหน่งคะแนนที่ต้องการลบ
 * @return true ถ้าลบสำเร็จ, false ถ้าไม่พบตำแหน่งคะแนนนี้
 */
public boolean remove(int index) {
     if (index < 0 || index >= number.size()) {
          return false;
     }
     number.remove(index);
     checkRep();
     return true;
}

// ===== Observers =====

/**
 * คืนคะแนนในลำดับนักเรียน
 * @param index ตำแหน่งที่ต้องการ (0-indexed)
 * @return คะแนน ที่ ตำแหน่งนั้น
 * @throws IndexOutOfBoundsException ถ้า index ไม่ถูกต้อง
 */
public String get(int index) {
     return number.get(index);
}

/**
 * ตรวจว่ามีคะแนนนี้อยู่ในลำดับนักเรียนหรือไม่
 * @param score คะแนนที่ต้องการตรวจสอบ
 * @return true ถ้ามีคะแนนนี้อยู่อย่างน้อยหนึ่งตำแหน่ง
 */
public boolean contains(String score) {
     return number.contains(score);
}


/**
 * คืนคะแนนทั้งหมดตามลำดับ
 */
public List<String> getAll() {
     return new ArrayList<>(number);
 }

// ===== Producer =====

/**
 * คืนลำดับคะแนนใหม่ที่มีคะแนนเดียวกันแต่เรียงเลขจากมากไปน้อย
 * @return คะแนนลำดับใหม่ที่เรียงเลขแล้ว
 */
public BoundedStack sortedDescending() {
     List<String> copy = new ArrayList<>(number);
     copy.sort((a, b) -> Integer.parseInt(b) - Integer.parseInt(a));
     BoundedStack result = new BoundedStack(copy.size());
     for (String s : copy) {
         result.add(s);
     }
     return result;
 }
}
