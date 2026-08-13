package gg.vape.module.render.hud;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import gg.vape.event.impl.EventScoreboardScores;
import gg.vape.module.render.hud.HudModule;
import gg.vape.module.render.hud.HudModuleGroup;
import gg.vape.module.render.hud.ScoreboardVisibleScorePredicate;
import gg.vape.ui.click.frame.impl.hud.ScoreboardHudFrame;
import gg.vape.utils.TimerUtil;
import gg.vape.utils.Vec3d;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.value.BooleanValue;
import gg.vape.value.StringMapValue;
import gg.vape.wrapper.impl.FontRenderer;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.GlStateManager;
import gg.vape.wrapper.impl.MatrixStack;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.Score;
import gg.vape.wrapper.impl.ScoreObjective;
import gg.vape.wrapper.impl.ScorePlayerTeam;
import gg.vape.wrapper.impl.Scoreboard;
import gg.vape.wrapper.impl.TextComponent;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import org.lwjgl.opengl.GL11;

public class ScoreboardHudModule
extends HudModule {
    private final TimerUtil objectiveTimer = new TimerUtil();
    public final BooleanValue showScoreNumbers = BooleanValue.create(this, "Show score numbers", false);
    public final StringMapValue textReplacements = (StringMapValue)StringMapValue.create(this, "Replace scoreboard text", "Find text", "Replace with").setBase64Encoded(true);
    private ScoreObjective objective;

    @Override
    public void onEnable() {
        EventScoreboardScores.setLocked(true);
    }

    public void updateObjective(ScoreObjective objective) {
        this.objective = objective;
        this.objectiveTimer.reset();
    }

    @Override
    public void onDisable() {
        EventScoreboardScores.setLocked(false);
    }

    public ScoreboardHudModule() {
        super("Scoreboard", HudModuleGroup.HUD, "scoreboard", ScoreboardHudFrame.class);
        this.addValue(this.showScoreNumbers, this.textReplacements);
        this.setSuffix("Allows you to edit the Minecraft scoreboard");
    }


    private String replaceScoreText(String searchText, String formattedText, String replacement) {
        String originalText = formattedText;
        char[] formattedCharacters = formattedText.toCharArray();
        StringBuilder visibleTextBuilder = new StringBuilder();
        for (int index = 0; index < formattedCharacters.length; ++index) {
            char character = formattedCharacters[index];
            if (character == '\u00a7') {
                ++index;
                continue;
            }
            if (character > 1000) {
                continue;
            }
            visibleTextBuilder.append(character);
        }
        String visibleText = visibleTextBuilder.toString().toLowerCase();
        String lowercaseFormattedText = formattedText.toLowerCase();
        String lowercaseSearchText = searchText.toLowerCase();
        if (visibleText.contains(lowercaseSearchText)) {
            char[] searchCharacters = lowercaseSearchText.toCharArray();
            char[] textCharacters = lowercaseFormattedText.toCharArray();
            int matchedCharacters = 0;
            int matchStart = -1;
            int matchEnd = -1;
            for (int index = 0; index < textCharacters.length; ++index) {
                if (matchedCharacters > searchCharacters.length - 1) {
                    continue;
                }
                char character = textCharacters[index];
                if (character == searchCharacters[matchedCharacters]) {
                    if (matchStart == -1) {
                        matchStart = index;
                    }
                    if (++matchedCharacters == searchCharacters.length) {
                        matchEnd = index;
                    }
                    continue;
                }
                if (matchStart != -1 && character == '\u00a7') {
                    ++index;
                }
            }
            if (matchStart != -1 && matchEnd > matchStart) {
                StringBuilder replacedText = new StringBuilder();
                for (int index = 0; index < textCharacters.length; ++index) {
                    if (index < matchStart || index > matchEnd) {
                        replacedText.append(textCharacters[index]);
                    }
                    if (index == matchStart) {
                        replacedText.append(replacement);
                    }
                }
                return replacedText.toString();
            }
        }
        return originalText;
    }

    public Vec3d renderScoreboard(double x, double y, boolean drawBackground) {
        boolean gamePaused = false;
        if (Minecraft.i() != null) {
            gamePaused = Minecraft.V();
        }
        if (this.objective == null) {
            return new Vec3d(0.0, 0.0, 0.0);
        }
        if (gamePaused || this.objectiveTimer.hasTimeElapsed(10000L)) {
            this.objective = null;
            return new Vec3d(0.0, 0.0, 0.0);
        }
        boolean blendWasEnabled = GL11.glIsEnabled(3042);
        if (blendWasEnabled) {
            GlStateManager.disableBlend();
        }
        boolean includeScoreNumbers = this.showScoreNumbers.getEffectiveValue();
        FontRenderer fontRenderer = Minecraft.getFontRenderer();
        Scoreboard scoreboard = this.objective.getScoreboard();
        EventScoreboardScores.setLocked(false);
        Collection<Score> scores = scoreboard.getPlayerScores(this.objective);
        EventScoreboardScores.setLocked(true);
        ArrayList<Score> visibleScores = Lists.newArrayList(
                Iterables.filter(scores, new ScoreboardVisibleScorePredicate()));
        scores = visibleScores.size() > 15
                ? Lists.newArrayList(Iterables.skip(visibleScores, scores.size() - 15))
                : visibleScores;
        int scoreboardWidth = fontRenderer.getStringWidth(this.objective.getDisplayNameText());
        for (Score score : scores) {
            ScorePlayerTeam team = scoreboard.getPlayersTeam(score.getOwner());
            String scoreLine = ScorePlayerTeam.formatPlayerName(team, score.getOwner()) + ":";
            if (includeScoreNumbers) {
                scoreLine += " \u00a7c" + score.getScore();
            }
            scoreboardWidth = Math.max(scoreboardWidth, fontRenderer.getStringWidth(scoreLine));
        }
        int contentHeight = scores.size() * fontRenderer.getFontHeight();
        int bottom = (int)(y + contentHeight) + 8;
        int left = (int)x + 1;
        int rowIndex = 0;
        double renderedHeight = 0.0;
        Map<String, String> replacements = this.textReplacements.getValue();
        for (Score score : scores) {
            ++rowIndex;
            ScorePlayerTeam team = scoreboard.getPlayersTeam(score.getOwner());
            String playerName = ScorePlayerTeam.formatPlayerName(team, score.getOwner());
            for (Map.Entry<String, String> replacement : replacements.entrySet()) {
                playerName = this.replaceScoreText(
                        replacement.getKey(), playerName, replacement.getValue());
            }
            String scoreText = "\u00a7c" + score.getScore();
            int rowY = bottom - rowIndex * fontRenderer.getFontHeight();
            if (drawBackground) {
                float backgroundX = left - 2;
                float backgroundY = rowY;
                float backgroundWidth = (float)(left + scoreboardWidth) - backgroundX;
                float backgroundHeight = fontRenderer.getFontHeight();
                GuiRenderPrimitives.y(backgroundX, backgroundY, backgroundWidth,
                        backgroundHeight, new Color(0x50000000, true));
            }
            renderedHeight += fontRenderer.getFontHeight();
            if (ForgeVersion.MC_1_16_5.d()) {
                fontRenderer.J(MatrixStack.A(), new TextComponent(team, score.getOwner()), left, rowY, -1);
            } else {
                fontRenderer.drawString(playerName, left, rowY, 0x20FFFFFF);
            }
            if (includeScoreNumbers) {
                fontRenderer.drawString(scoreText,
                        left + scoreboardWidth - fontRenderer.getStringWidth(scoreText), rowY, 3648127);
            }
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            if (rowIndex == scores.size()) {
                String title = this.objective.getDisplayNameText();
                for (Map.Entry<String, String> replacement : replacements.entrySet()) {
                    title = this.replaceScoreText(
                            replacement.getKey(), title, replacement.getValue());
                }
                if (drawBackground) {
                    GuiRenderPrimitives.C(left - 2, rowY - fontRenderer.getFontHeight() - 1,
                            scoreboardWidth + 2.0, fontRenderer.getFontHeight(),
                            new Color(0x60000000, true));
                    GuiRenderPrimitives.C(left - 2, rowY - 1, scoreboardWidth + 2.0, 1.0,
                            new Color(0x50000000, true));
                }
                if (ForgeVersion.MC_1_16_5.d()) {
                    fontRenderer.J(MatrixStack.A(), this.objective.getDisplayNameComponent(),
                            left + scoreboardWidth / 2 - fontRenderer.getStringWidth(title) / 2,
                            rowY - fontRenderer.getFontHeight(), -1);
                } else {
                    fontRenderer.drawString(title,
                            left + scoreboardWidth / 2 - fontRenderer.getStringWidth(title) / 2,
                            rowY - fontRenderer.getFontHeight(), 0x20FFFFFF);
                }
            }
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        }
        if (blendWasEnabled) {
            GlStateManager.enableBlend();
        }
        return new Vec3d(scoreboardWidth, renderedHeight + 5.0, 0.0);
    }
}
