package yilin_logistics;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.memory.platform.global.TwoDimensionCode;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:application.xml" })
public class TestYingLian {

	@Test
	public void QRCode() {
		String imgPath = "E:/Michael_QRCode.png";
		String encoderContent = "http://www.qibogu.com/yilin_logistics/download/Logistics.apk";
		TwoDimensionCode handler = new TwoDimensionCode();
		handler.encoderQRCode(encoderContent, imgPath, "png");
		System.out.println("============encoder success");
		String decoderContent = handler.decoderQRCode(imgPath);
		System.out.println("解析结果如下:");
		System.out.println(decoderContent);
		System.out.println("============decoder success!!!");
	}
}
