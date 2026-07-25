import java.util.*;
/**
 * BoundedStack - ADT แทนคะแนนนักเรียนที่อาจารย์จัดลำดับไว้
 * form พลอยกาญจน์ และ ...
 * คำนามธรรม (A) : ลำดับของคะแนนนักเรียน เช่น [18, 9, 15]
 * 
 * ตัวอย่างการใช้งาน
 * BoundedStack s = new BoundedStack();
 * s.add("20");
 * System.out.println(s.size());  
 */

public class BoundedStack {
    public static final int max_number = 50;

    //===== representation =====
    // TODO 1: เขียน Abstraction Function 
    // Abstraction Function:
    //   AF(number) = ลำดับของคะแนนนักเรียนแต่ละคน
    

    // TODO 2: เขียน Representation Invariant ตรงนี้ (7 ข้อ)
    // Representation Invariant:
    // นักเรียนไม่เป็น null
    // ไม่มีคะแนนเป็น null
    // ไม่มีคะแนนที่ว่าง
    // ไม่มีคะแนนติดลบ
    // คะแนนมีได้แค่ตัวเลข
    // คะแนนไม่เกิน 20 คะแนน
    // มีได้ไม่เกิน max_number (50) คน

    // TODO 3: เขียน Safety from rep exposure ตรงนี้
    // Safety from rep exposure:
    //  มีการ copy object ทั้งตอนสร้างและส่งคะแนนนักเรียน

    /**
     * TODO 4: เขียน checkRep()
     * แปลง RI ทุกข้อเป็น assert หนึ่งบรรทัด พร้อมข้อความอธิบาย
     */

    
}

 // ===== Creator =====

    /**
     * สร้างคะแนนนักเรียน
     */
    
   
/**
 * TODO 5: Creator ตัวที่สอง
 * 
 * สร้างจำนวนของคะแนนนักเรียน
 * @param initial จำนวนนักเรียน ต้องไม่เกิน max_number
 * @throws IllegalArgumentException ถ้า initial ผิดเงื่อนไข
 */



 // ===== Mutators =====

/**
 * TODO 6: เพิ่มคะแนนต่อท้ายนักเรียน
 *
 * @param score คะแนน ต้องไม่เป็น null และไม่มีคะแนนว่าง
 * @return true ถ้าเพิ่มสำเร็จ, false ถ้ามีจำนวนนักเรียนเต็มแล้ว
 * @throws IllegalArgumentException ถ้า score เป็น null หรือสตริงว่าง
 */


/**
 * TODO 7: ลบคะแนนออกจากลำดับนักเรียน
 *
 * @param song ลำดับคะแนนที่ต้องการลบ
 * @return true ถ้าลบสำเร็จ, false ถ้าไม่พบลำดับคะแนนนี้
 */

// ===== Observers =====

/**
 * TODO 8: คืนคะแนนในลำดับนักเรียน
 */


/**
 * TODO 9: ตรวจว่ามีคะแนนนี้อยู่หรือไม่
 */


/**
 * TODO 10: คืนคะแนนทั้งหมดตามลำดับ
 */


// ===== Producer =====

/**
 * TODO 11: คืนลำดับคะแนนใหม่ที่มีคะแนนเดียวกันแต่เรียงเลขจากมากไปน้อย
 *
 * @return คะแนนลำดับใหม่ที่เรียงเลขแล้ว
 */

