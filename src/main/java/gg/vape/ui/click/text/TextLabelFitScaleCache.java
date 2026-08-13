package gg.vape.ui.click.text;

import gg.vape.Vape;
import gg.vape.ui.click.text.TextLabelFitSpec;
import gg.vape.ui.font.SmoothFontRenderer;
import java.math.BigDecimal;
import java.util.LinkedHashMap;

public class TextLabelFitScaleCache {
    LinkedHashMap<String, Double> scaleBySpecKey = new LinkedHashMap();
    public static TextLabelFitScaleCache INSTANCE = new TextLabelFitScaleCache();


    public double getFittedScale(TextLabelFitSpec fitSpec) {
        if (this.scaleBySpecKey.containsKey(fitSpec.toString())) {
            return this.scaleBySpecKey.get(fitSpec.toString());
        }
        double fittedScale = fitSpec.getMaxScale();
        while (fittedScale >= fitSpec.getMinScale() && fittedScale <= fitSpec.getMaxScale()) {
            SmoothFontRenderer fontRenderer = fitSpec.isBold() ? Vape.INSTANCE.getFontManager().W(fittedScale, false) : Vape.INSTANCE.getFontManager().E(fittedScale, false);
            if (!(fontRenderer.N(fitSpec.getText()) > fitSpec.getMaxWidth())) break;
            fittedScale = new BigDecimal(fittedScale).subtract(BigDecimal.valueOf(fitSpec.getScaleIncrement())).setScale(1, 4).doubleValue();
        }
        this.scaleBySpecKey.put(fitSpec.toString(), fittedScale);
        return fittedScale;
    }
}

