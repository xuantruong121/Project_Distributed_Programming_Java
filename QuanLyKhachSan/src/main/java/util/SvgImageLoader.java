package util;

import org.apache.batik.bridge.BridgeContext;
import org.apache.batik.bridge.DocumentLoader;
import org.apache.batik.bridge.GVTBuilder;
import org.apache.batik.bridge.UserAgentAdapter;
import org.apache.batik.gvt.GraphicsNode;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.w3c.dom.svg.SVGDocument;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;

public class SvgImageLoader {
    public static BufferedImage loadSvg(File svgFile, float targetWidth, float targetHeight) {
        try {
            // Bước 1: Lấy kích thước gốc của SVG
            float[] originalDimensions = getOriginalSvgDimensions(svgFile);
            float originalWidth = originalDimensions[0];
            float originalHeight = originalDimensions[1];

            // Bước 2: Tính toán tỷ lệ scale giữ nguyên aspect ratio
            float widthRatio = targetWidth / originalWidth;
            float heightRatio = targetHeight / originalHeight;
            float scale = Math.min(widthRatio, heightRatio);

            // Bước 3: Tính kích thước mới
            float scaledWidth = originalWidth * scale;
            float scaledHeight = originalHeight * scale;

            // Bước 4: Thực hiện transcoding với kích thước mới
            PNGTranscoder transcoder = new PNGTranscoder();
            transcoder.addTranscodingHint(PNGTranscoder.KEY_WIDTH, scaledWidth);
            transcoder.addTranscodingHint(PNGTranscoder.KEY_HEIGHT, scaledHeight);

            // Các hint chất lượng
            transcoder.addTranscodingHint(PNGTranscoder.KEY_FORCE_TRANSPARENT_WHITE, false);

            TranscoderInput input = new TranscoderInput(svgFile.toURI().toString());
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            transcoder.transcode(input, new TranscoderOutput(outputStream));

            byte[] imageData = outputStream.toByteArray();
            return javax.imageio.ImageIO.read(new ByteArrayInputStream(imageData));

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static float[] getOriginalSvgDimensions(File svgFile) throws Exception {
        UserAgentAdapter userAgent = new UserAgentAdapter();
        DocumentLoader loader = new DocumentLoader(userAgent);
        BridgeContext ctx = new BridgeContext(userAgent, loader);
        ctx.setDynamicState(BridgeContext.DYNAMIC);

        String uri = svgFile.toURI().toString();
        SVGDocument doc = (SVGDocument) loader.loadDocument(uri);

        GVTBuilder builder = new GVTBuilder();
        GraphicsNode root = builder.build(ctx, doc);

        float width = (float) root.getPrimitiveBounds().getWidth();
        float height = (float) root.getPrimitiveBounds().getHeight();

        return new float[]{width, height};
    }
}