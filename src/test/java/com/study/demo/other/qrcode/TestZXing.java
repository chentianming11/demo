package com.study.demo.other.qrcode;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by chen on 2018/5/13.
 */
public class TestZXing {


    /**
     * 测试创建二维码图片
     */
    @Test
    public void createQRCode() throws WriterException, IOException {
        String contents = "http://www.baidu.com";
        int width = 500;
        int height = 500;
        String format = "png";

        Map hints = new HashMap();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        hints.put(EncodeHintType.MARGIN, 2);

        BitMatrix bitMatrix = new MultiFormatWriter().encode(contents, BarcodeFormat.QR_CODE, width, height, hints);
        MatrixToImageWriter.writeToPath(bitMatrix, format, new File("D:/qrcode.png").toPath());
    }

    @Test
    public void readQRCode() throws IOException, NotFoundException {
        Map hints = new HashMap();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");

        File file = new File("D:/qrcode.png");
        BinaryBitmap image = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(ImageIO.read(file))));
        Result result = new MultiFormatReader().decode(image, hints);
        System.out.println("文本：" + result.getText());
        System.out.println("格式：" + result.getBarcodeFormat().name());
        System.out.println("结果：" + result);

    }
}
