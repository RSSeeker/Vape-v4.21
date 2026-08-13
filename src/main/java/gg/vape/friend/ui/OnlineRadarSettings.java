package gg.vape.friend.ui;

import gg.vape.unmap.ModeOption;
import gg.vape.unmap.ModeSelection;
import gg.vape.value.BooleanValue;
import gg.vape.value.ColorValue;
import gg.vape.value.ModeValue;
import gg.vape.value.NumberValue;
import java.awt.Color;

public class OnlineRadarSettings {
    public final ModeOption customColorMode;
    public final ColorValue enemyColor;
    public final ModeValue colorMode;
    public final ModeValue radarMode;
    public final NumberValue dotSize;
    public final ModeOption twoDimensionalRadarMode = new ModeOption("2D Radar");
    public final BooleanValue clampRadar;
    public final ColorValue friendlyColor;
    public final ModeOption circleRadarStyle;
    public final NumberValue radarSize;
    public final BooleanValue showBackground;
    public final ModeOption relationshipColorMode;
    public final NumberValue radarScale;
    public final ModeValue radarStyle;
    public final ModeOption teamColorMode;
    public final ModeOption squareDotStyle;
    public final NumberValue maxDistance;
    public final ModeValue dotStyle;
    public final NumberValue maxShown;
    public final ColorValue customColor;
    public final ModeOption textRadarMode = new ModeOption("Text Radar");
    public final BooleanValue showCross;
    public final ModeOption squareRadarStyle;
    public final ModeOption circleDotStyle;

    public OnlineRadarSettings() {
        this.radarMode = ModeValue.create((Object)this, "Mode", this.twoDimensionalRadarMode, this.twoDimensionalRadarMode, this.textRadarMode);
        this.radarSize = NumberValue.createWithDescription(this, "Radar Size", "#.#", "", 25.0, 110.0, 500.0, "The size of the radar.");
        this.dotSize = NumberValue.create(this, "Dot Size", "#.#", "", 0.5, 3.0, 10.0, 0.1, "The size of the radar.");
        this.radarScale = NumberValue.create(this, "Radar Scale", "#.##", "", 0.1, 0.5, 5.0, 0.01, "The size of the radar.");
        this.maxDistance = NumberValue.create(this, "Max Distance", "#", "m", 0.0, 0.0, 100.0, 5.0, "Maximum distance to show.\nUse 0 to ignore distance requirement.");
        this.maxShown = NumberValue.create(this, "Max Shown", "#", "", 0.0, 25.0, 50.0, 1.0, "Maximum players to show at once.\nUse 0 to show all players.");
        this.teamColorMode = new ModeOption("Team");
        this.customColorMode = new ModeOption("Custom");
        this.relationshipColorMode = new ModeOption("Relationship");
        this.colorMode = ModeValue.create((Object)this, "ColorMode1", "Color Mode", "", (ModeSelection)this.teamColorMode, this.teamColorMode, this.customColorMode, this.relationshipColorMode);
        this.circleDotStyle = new ModeOption("Circles");
        this.squareDotStyle = new ModeOption("Squares");
        this.dotStyle = ModeValue.create((Object)this, "DotStyle1", "Dot Style", "", (ModeSelection)this.circleDotStyle, this.circleDotStyle, this.squareDotStyle);
        this.squareRadarStyle = new ModeOption("Square");
        this.circleRadarStyle = new ModeOption("Circle");
        this.radarStyle = ModeValue.create((Object)this, "Radar Style", this.squareRadarStyle, this.squareRadarStyle, this.circleRadarStyle);
        this.customColor = ColorValue.create(this, "Custom Color", Color.WHITE);
        this.friendlyColor = ColorValue.create(this, "Friendly Color", Color.GREEN);
        this.enemyColor = ColorValue.create(this, "Enemy Color", Color.RED);
        this.showCross = BooleanValue.create(this, "Show Cross", true, "Render a center cross on the radar.");
        this.showBackground = BooleanValue.create(this, "Show Background", true, "Render a background behind the radar.");
        this.clampRadar = BooleanValue.create(this, "Clamp Radar", true, "Clamps dots to the given size and shape.");
        this.radarMode.addModeDependentValues(this.twoDimensionalRadarMode, this.radarSize, this.radarScale, this.colorMode, this.radarStyle, this.dotStyle, this.dotSize, this.showCross, this.clampRadar);
        this.radarMode.addModeDependentValues(this.textRadarMode, this.maxDistance, this.maxShown);
        this.colorMode.addModeDependentValues(this.customColorMode, this.customColor);
        this.colorMode.addModeDependentValues(this.relationshipColorMode, this.friendlyColor, this.enemyColor);
    }
}
