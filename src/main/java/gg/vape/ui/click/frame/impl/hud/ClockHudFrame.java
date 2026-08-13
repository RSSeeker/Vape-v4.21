package gg.vape.ui.click.frame.impl.hud;

import gg.vape.Vape;
import gg.vape.module.render.hud.ClockHudModule;
import gg.vape.ui.click.frame.impl.hud.HudModuleConfigFrameBase;
import gg.vape.ui.font.SmoothFontRenderer;
import gg.vape.unmap.ModeSelection;
import gg.vape.utils.render.GuiRenderPrimitives;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

public class ClockHudFrame
extends HudModuleConfigFrameBase {
    private final ClockHudModule module = (ClockHudModule)this.getModule();

    private void renderAnalogClock() {
        float right = (float)(this.G$src$D$1b2f02a() + this.A());
        float bottom = (float)(this.n() + this.L());
        SmoothFontRenderer timeFont = Vape.INSTANCE.getFontManager().K(2.8, true);
        SmoothFontRenderer detailFont = Vape.INSTANCE.getFontManager().K(0.85, true);
        this.drawClockTicks();
        float centerX = (float)(this.G$src$D$1b2f02a() + this.A() / 2.0);
        float centerY = (float)(this.n() + this.L() / 2.0);
        float handDegrees = Integer.parseInt(this.getHourText()) * 30
                + Float.parseFloat(this.getMinuteText()) / 2.0f;
        float handX = (float)(Math.cos(handDegrees * Math.PI / 180.0
                - Math.PI / 2.0) * 26.0 + centerX);
        float handY = (float)(Math.sin(handDegrees * Math.PI / 180.0
                - Math.PI / 2.0) * 26.0 + centerY);
        GuiRenderPrimitives.u(centerX, centerY, handX, handY, 1.8f,
                this.applyDefaultEditorAlpha(ClockHudFrame.J.O));
        String hourText = this.getHourText();
        String minuteText = this.getMinuteText();
        String weekday = this.getWeekday();
        String date = this.formatDate(false);
        timeFont.d(hourText, this.G$src$D$1b2f02a() - timeFont.N(hourText) + 28.0,
                this.n() + 5.0, this.getEditorForegroundColor());
        timeFont.d(minuteText, right - timeFont.N(minuteText) - 5.0,
                bottom - timeFont.d(minuteText) - 8.0,
                this.getEditorForegroundColor());
        detailFont.d(weekday, this.G$src$D$1b2f02a() + 10.0,
                bottom - detailFont.d(weekday) - 16.0,
                this.getEditorForegroundColor());
        detailFont.d(date, this.G$src$D$1b2f02a() + 10.0,
                bottom - detailFont.d(date) - 8.0,
                this.getEditorForegroundColor());
        if (!this.module.use24HourTime.getEffectiveValue().booleanValue()) {
            String meridiem = this.getMeridiem();
            detailFont.d(meridiem, right - detailFont.N(meridiem) - 5.0,
                    this.n() + 5.0, this.getEditorForegroundColor());
        }
    }

    @Override
    public double A() {
        if (this.isDigitalMode()) {
            double width = 70.0;
            if (this.module.showDate.getEffectiveValue().booleanValue()) {
                width += 24.0;
            }
            if (!this.module.use24HourTime.getEffectiveValue().booleanValue()) {
                width += 12.0;
            }
            return width;
        }
        return 70.0;
    }

    private String getMinuteText() {
        int minute = LocalTime.now().getMinute();
        String minuteText = String.valueOf(minute);
        if (minute < 10) {
            minuteText = "0" + minuteText;
        }
        return minuteText;
    }

    public ClockHudFrame() {
        super(ClockHudModule.class);
    }

    @Override
    public void renderHudContent() {
        if (this.isDigitalMode()) {
            this.renderDigitalClock();
            return;
        }
        this.renderAnalogClock();
    }

    private boolean isDigitalMode() {
        if (this.module == null) {
            return false;
        }
        return ((ModeSelection)this.module.clockType.getValue()).getName().equalsIgnoreCase("digital");
    }

    private String getMeridiem() {
        int hour = LocalTime.now().getHour();
        if (hour >= 12) {
            return "pm";
        }
        return "am";
    }

    private String getWeekday() {
        return LocalDate.now().getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.ROOT).toLowerCase();
    }

    @Override
    public double L() {
        if (this.isDigitalMode()) {
            return 32.0;
        }
        return 65.0;
    }

    private String getHourText() {
        int hour = LocalTime.now().getHour();
        if (!this.module.use24HourTime.getEffectiveValue().booleanValue()) {
            if (hour > 12) {
                hour -= 12;
            } else if (hour == 0) {
                hour = 12;
            }
        }
        String hourText = String.valueOf(hour);
        if (hour < 10) {
            hourText = "0" + hourText;
        }
        return hourText;
    }

    private void renderDigitalClock() {
        SmoothFontRenderer timeFont = Vape.INSTANCE.getFontManager().K(3.0, true);
        SmoothFontRenderer detailFont = Vape.INSTANCE.getFontManager().K(1.0, true);
        String hourText = this.getHourText();
        String minuteText = this.getMinuteText();
        timeFont.d(hourText, this.G$src$D$1b2f02a() - timeFont.N(hourText) + 30.0,
                this.n() + 2.0, this.getEditorForegroundColor());
        GuiRenderPrimitives.V(this.G$src$D$1b2f02a() + 34.0, this.n() + 15.0, 2.0, 0.0, this.getEditorForegroundColor());
        timeFont.d(minuteText, this.G$src$D$1b2f02a() + 39.0,
                this.n() + 2.0, this.getEditorForegroundColor());
        double detailX = this.G$src$D$1b2f02a() + 39.0;
        if (!this.module.use24HourTime.getEffectiveValue().booleanValue()) {
            detailX += timeFont.N(minuteText);
            detailFont.d(this.getMeridiem(), detailX, this.n() + 18.0,
                    this.getEditorForegroundColor());
        }
        if (this.module.showDate.getEffectiveValue().booleanValue()) {
            String date = this.formatDate(true);
            detailX = this.G$src$D$1b2f02a() + this.A() - detailFont.N(date) - 6.0;
            double dateWidth = detailFont.N(date);
            String weekday = this.getWeekday().toLowerCase();
            double weekdayWidth = detailFont.N(weekday);
            double weekdayX = detailX + dateWidth - weekdayWidth;
            detailFont.d(date, detailX, this.n() + 7.0,
                    this.getEditorForegroundColor());
            detailFont.d(weekday, weekdayX, this.n() + 15.0,
                    this.getEditorForegroundColor());
        }
    }


    private void drawClockTicks() {
        float centerX = (float)(this.G$src$D$1b2f02a() + this.A() / 2.0);
        float centerY = (float)(this.n() + this.L() / 2.0);
        for (int tick = 0; tick < 24; ++tick) {
            if (tick == 8 || tick == 9 || tick == 10 || tick == 14 || tick == 15
                    || tick == 16 || tick == 20 || tick == 21 || tick == 22) continue;
            float tickX = (float)(Math.cos((double)(tick * 15) * Math.PI / 180.0
                    - Math.PI / 2.0) * 25.0 + centerX);
            float tickY = (float)(Math.sin((double)(tick * 15) * Math.PI / 180.0
                    - Math.PI / 2.0) * 25.0 + centerY);
            GuiRenderPrimitives.V(tickX - 1.0f, tickY, 0.5, 1.0,
                    this.getEditorForegroundColor());
        }
    }

    private String formatDate(boolean includeSpaces) {
        String zoneId = ZonedDateTime.now().getZone().getId();
        String datePattern = zoneId != null && zoneId.contains("America")
                ? "MM / dd" : "dd / MM";
        if (!includeSpaces) {
            datePattern = datePattern.replace(" ", "");
        }
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(datePattern));
    }

    @Override
    public String getName() {
        return "ClockFrame";
    }
}

