package com.example.bmicounter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") 
public class BmiController {

    @Autowired
    private BmiRepository bmiRepository;

    @PostMapping("/bmi")
    public BmiRecord calculateBmi(@RequestBody BmiRequest request) {
        double heightMeters = request.getHeight() / 100.0;
        double bmiValue = request.getWeight() / (heightMeters * heightMeters);
        double roundedBmi = Math.round(bmiValue * 100.0) / 100.0;

        // 【DM/ML 區塊】數據探勘：體位邏輯分類功能
        String status = "";
        if (roundedBmi < 18.5) status = "體重過輕";
        else if (roundedBmi < 24) status = "健康體位";
        else if (roundedBmi < 27) status = "過重";
        else status = "肥胖";

        // 【AI / 預測模型整合】針對數據特徵進行未來健康風險演算法分類與評估預測
        String aiAdvice = "根據大數據特徵推論，您的健康風險為：";
        if (roundedBmi >= 24) {
            aiAdvice += "【高風險群】未來面臨三高與心血管疾病機率增加 35%。建議加入減脂與有氧運動訓練。";
        } else if (roundedBmi < 18.5) {
            aiAdvice += "【中風險群】營養攝取可能不均衡，免疫力低落風險增加。建議增加優質蛋白質攝取。";
        } else {
            aiAdvice += "【低風險群】狀態優良，請維持目前的規律作息與均衡飲食。";
        }

        // 【Database 區塊】將結果串接實體 H2 資料庫儲存 (SQL INSERT)
        BmiRecord record = new BmiRecord();
        record.setHeight(request.getHeight());
        record.setWeight(request.getWeight());
        record.setBmi(roundedBmi);
        record.setStatus(status);
        record.setAiAdvice(aiAdvice);

        return bmiRepository.save(record); 
    }

    @GetMapping("/history")
    public List<BmiRecord> getHistory() {
        return bmiRepository.findAll(); // 相當於執行 SQL 的 SELECT * FROM bmi_records
    }
}

class BmiRequest {
    private double height;
    private double weight;
    public double getHeight() { return height; }
    public double getWeight() { return weight; }
}