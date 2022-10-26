package com.sjkz1.emissive_skin_renderer.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;

public class ThaiLanguageGen extends FabricLanguageProvider {
    protected ThaiLanguageGen(FabricDataGenerator dataGenerator) {
        super(dataGenerator, "th_th");
    }

    @Override
    public void generateTranslations(TranslationBuilder translationBuilder) {
        translationBuilder.add("text.autoconfig.emissive-skin-renderer.title", "Emissive Skin Renderer");
        translationBuilder.add("text.autoconfig.emissive-skin-renderer.option.main.glowingSkin", "สกินเรืองแสง");
        translationBuilder.add("text.autoconfig.emissive-skin-renderer.option.main.glowingHorseArmor", "เกราะม้าเรืองแสง");
        translationBuilder.add("text.autoconfig.emissive-skin-renderer.option.main.palletsRate", "อัตราการหายไปของสี");
        translationBuilder.add("text.autoconfig.emissive-skin-renderer.option.main.renderPlayerNameInThirdPerson", "แสดงชื่อในมุมมองบุคคลที่สาม");
        translationBuilder.add("create_skin_less_than", "สร้างสกิน (น)");
        translationBuilder.add("create_skin_greater_than", "สร้างสกิน (ม)");
        translationBuilder.add("pallets_desc_l1", "น คือ `น้อยกว่า`");
        translationBuilder.add("pallets_desc_l2", "ม คือ `มากกว่า`");
        translationBuilder.add("pallets_desc_l3", "ในเวอร์ชั่นที่ผ่านมา มีบางสกินไม่สามารถเรืองแสงได้");
        translationBuilder.add("pallets_desc_l4", "`มากกว่า`เมื่อคุณเลือก/กดปุ่มนี้ จะทำการทำการลบสีที่มีเดลต้าของสีมากกว่า `ตัวตั้งของเดลต้าของสี");
        translationBuilder.add("pallets_desc_l5", "`น้อยกว่า`เมื่อคุณเลือก/กดปุ่มนี้ จะทำการทำการลบสีที่มีเดลต้าของสีน้อยกว่า `ตัวตั้งของเดลต้าของสี`");
        translationBuilder.add("pallets_desc_l6", "(สกินส่วนใหญ่จะใช้รในรูปแบบน้อยกว่า)");
        translationBuilder.add("not_final_gui", "GUI ยังไม่เสร็จ!!");
        translationBuilder.add("base_delta", "ตัวตั้งของเดลต้าของสี");
    }
}
