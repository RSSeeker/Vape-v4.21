package gg.vape.module.blatant.antibot;

import gg.vape.mapping.MappedClasses;
import gg.vape.unmap.TextComponentBase;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.EntityPlayer;
import gg.vape.wrapper.impl.GameProfile;
import gg.vape.wrapper.impl.ITextComponent;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.NetHandlerPlayClientImpl;
import gg.vape.wrapper.impl.PlayerInfo;
import gg.vape.wrapper.impl.ScorePlayerTeam;
import gg.vape.wrapper.impl.ScorePlayerTeamTextComponent;
import gg.vape.wrapper.impl.TextFormatting;
import java.util.List;

public class AntiBotEntityCache {
    private static final Integer NO_TEAM_COLOR = 0xFFFFFF;

    private String sanitizeName(String name) {
        if (name == null) {
            return null;
        }
        StringBuilder sanitizedName = new StringBuilder();
        for (int index = 0; index < name.length(); ++index) {
            char character = name.charAt(index);
            if (!(character >= 'a' && character <= 'z' || character >= 'A' && character <= 'Z' || character >= '0' && character <= '9') && character != '_') continue;
            sanitizedName.append(character);
        }
        return sanitizedName.toString();
    }

    public Integer getTeamColor(EntityPlayer player) {
        NetHandlerPlayClientImpl connection = Minecraft.N();
        if (connection.isNull()) {
            return null;
        }
        String playerName = player.getName();
        Integer teamColor = null;
        for (Object playerInfoObject : connection.getPlayerInfoMap()) {
            Object profileObject;
            PlayerInfo playerInfo = new PlayerInfo(playerInfoObject);
            if (playerInfo.isNull()) continue;
            ITextComponent displayName = playerInfo.R();
            if (displayName.isNotNull()) {
                Object displayText = displayName.getFormattedText();
                if (displayText == null || !((String)displayText).contains(playerName)) continue;
                Integer formattedColor = this.findColorFromInfo(playerInfo, playerName);
                if (formattedColor != null) {
                    return formattedColor;
                }
            }
            if (teamColor != null || playerInfo.v().isNull()
                    || !((GameProfile)(profileObject = playerInfo.v())).getName().equals(playerName)) continue;
            try {
                ScorePlayerTeam scoreTeam = playerInfo.X();
                TextFormatting formatting;
                Integer formattingColor;
                if (!scoreTeam.isNotNull() || (formatting = scoreTeam.getColor()) == null
                        || (formattingColor = formatting.getColor()) == null
                        || formattingColor.equals(NO_TEAM_COLOR)) continue;
                teamColor = formattingColor;
            }
            catch (Exception exception) {}
        }
        return teamColor;
    }

    public boolean hasSameTeamColor(EntityPlayer firstPlayer, EntityPlayer secondPlayer) {
        Integer firstColor = this.getTeamColor(firstPlayer);
        Integer secondColor = this.getTeamColor(secondPlayer);
        if (firstColor != null && secondColor != null) {
            return firstColor.equals(secondColor);
        }
        return false;
    }

    private String extractText(ITextComponent component) {
        Object componentValue;
        try {
            componentValue = component.F();
            if (((Wrapper)componentValue).isNotNull() && ((Wrapper)componentValue).isInstance(MappedClasses.qT)) {
                ScorePlayerTeamTextComponent teamComponent = new ScorePlayerTeamTextComponent(((Wrapper)componentValue).getObject());
                return teamComponent.Y();
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        try {
            componentValue = component.getFormattedText();
            if (componentValue != null) {
                return this.sanitizeName((String)componentValue);
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return null;
    }

    private Integer findColorInComponent(ITextComponent component, String playerName) {
        if (component.isNull()) {
            return null;
        }
        String componentText = this.extractText(component);
        Integer componentColor;
        if (componentText != null && this.namesMatch(componentText, playerName) && (componentColor = this.extractColorFromStyle(component)) != null) {
            return componentColor;
        }
        try {
            List<ITextComponent> siblings = component.G();
            for (ITextComponent sibling : siblings) {
                Integer siblingColor = this.findColorInComponent(sibling, playerName);
                if (siblingColor == null) continue;
                return siblingColor;
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return null;
    }

    private Integer findColorFromInfo(PlayerInfo playerInfo, String playerName) {
        ITextComponent displayName = playerInfo.R();
        if (displayName.isNull()) {
            return null;
        }
        return this.findColorInComponent(displayName, playerName);
    }

    private Integer extractColorFromStyle(ITextComponent component) {
        try {
            TextComponentBase style = component.J();
            if (style.isNull()) {
                return null;
            }
            String styleText = style.getObject().toString();
            if (styleText.contains("color=")) {
                int colorStart = styleText.indexOf("color=") + 6;
                int colorEnd = styleText.indexOf(",", colorStart);
                if (colorEnd == -1) {
                    colorEnd = styleText.indexOf("}", colorStart);
                }
                if (colorEnd > colorStart) {
                    String colorName = styleText.substring(colorStart, colorEnd);
                    return this.parseColor(colorName);
                }
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return null;
    }

    private boolean namesMatch(String componentName, String playerName) {
        if (componentName == null || playerName == null) {
            return false;
        }
        if (componentName.equals(playerName)) {
            return true;
        }
        String sanitizedName = this.sanitizeName(componentName);
        return sanitizedName.equals(playerName);
    }

    private Integer parseColor(String colorName) {
        if (colorName == null || colorName.isEmpty()) {
            return null;
        }
        if (colorName.startsWith("#")) {
            try {
                return Integer.parseInt(colorName.substring(1), 16);
            }
            catch (NumberFormatException numberFormatException) {
                return null;
            }
        }
        TextFormatting formatting = TextFormatting.fromName(colorName);
        if (formatting != null) {
            return formatting.getColor();
        }
        return null;
    }
}
